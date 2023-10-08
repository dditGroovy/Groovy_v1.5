package kr.co.groovy.sanction;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.groovy.enums.ClassOfPosition;
import kr.co.groovy.enums.Department;
import kr.co.groovy.enums.SanctionFormat;
import kr.co.groovy.enums.SanctionProgress;
import kr.co.groovy.utils.ParamMap;
import kr.co.groovy.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Service
public class SanctionService {
    final SanctionMapper mapper;
    final WebApplicationContext context;
    final String uploadPath;


    public SanctionService(SanctionMapper mapper, WebApplicationContext context, String uploadPath) {
        this.mapper = mapper;
        this.context = context;
        this.uploadPath = uploadPath;
    }


    public void approve(String elctrnSanctnemplId, String elctrnSanctnEtprCode) throws SQLException {
        mapper.approve(elctrnSanctnemplId, elctrnSanctnEtprCode);
    }

    public void finalApprove(String elctrnSanctnemplId, String elctrnSanctnEtprCode) throws SQLException {
        mapper.finalApprove(elctrnSanctnemplId, elctrnSanctnEtprCode);
    }

    public void reject(Map<String, Object> map) throws SQLException {
        mapper.reject(map);
    }

    public void collect(String elctrnSanctnEtprCode) throws SQLException {
        mapper.collect(elctrnSanctnEtprCode);
    }

    @Transactional
    public void startApprove(@RequestBody Map<String, Object> request) {
        try {
            String className = (String) request.get("className");
            String methodName = (String) request.get("methodName");

            Map<String, Object> parameters = (Map<String, Object>) request.get("parameters");

            Class<?> serviceType = Class.forName(className);
            Object serviceInstance = context.getBean(serviceType);
            Method method = serviceType.getDeclaredMethod(methodName, Map.class);
            method.invoke(serviceInstance, parameters);
        } catch (Exception e) {
            log.error("결재 상신 오류 : {}", e.getMessage());
        }
    }

    public SanctionFormatVO loadFormat(String format) throws SQLException {
        return mapper.loadFormat(format);
    }

    public String getSeq(String formatSanctnKnd) throws SQLException {
        return mapper.getSeq(formatSanctnKnd);
    }


    public int getStatus(String elctrnSanctnDrftEmplId, String commonCodeSanctProgrs) throws SQLException {
        return mapper.getStatus(elctrnSanctnDrftEmplId, commonCodeSanctProgrs);
    }

    public List<SanctionLineVO> loadAwaiting(String emplId) throws SQLException {
        List<SanctionLineVO> list = mapper.loadAwaiting(emplId);
        for (SanctionLineVO vo : list) {
            vo.setCommonCodeSanctProgrs(SanctionProgress.valueOf(vo.getCommonCodeSanctProgrs()).label());
        }
        return list;
    }

    public List<SanctionVO> loadRequest(String emplId) throws SQLException {
        List<SanctionVO> list = mapper.loadRequest(emplId);
        for (SanctionVO vo : list) {
            vo.setElctrnSanctnFormatCode(SanctionFormat.valueOf(vo.getElctrnSanctnFormatCode()).label());
            vo.setCommonCodeSanctProgrs(SanctionProgress.valueOf(vo.getCommonCodeSanctProgrs()).label());
        }

        return list;
    }

    @Transactional
    public void inputSanction(ParamMap requestData) throws IOException, SQLException {
        SanctionVO vo = new SanctionVO();
        String etprCode = requestData.getString("etprCode");
        String formatCode = requestData.getString("formatCode");
        String writer = requestData.getString("writer");
        String title = requestData.getString("title");
        String content = requestData.getString("content");
        String afterProcess = requestData.getString("afterProcess");

        vo.setElctrnSanctnEtprCode(etprCode);
        vo.setElctrnSanctnFormatCode(formatCode);
        vo.setElctrnSanctnSj(title);
        vo.setElctrnSanctnDc(content);
        vo.setElctrnSanctnDrftEmplId(writer);
        if (afterProcess != null) {
            vo.setElctrnSanctnAfterPrcs(afterProcess);
        }

        String fileName = mapper.getSign(writer);
        File file = new File(String.format("%s/sign/%s", uploadPath, fileName));
        log.info(file + "");
        byte[] signImg = FileUtils.readFileToByteArray(file);
        String encodedString = Base64.getEncoder().encodeToString(signImg);
        vo.setElctrnSanctnDrftEmplSign(encodedString.getBytes());
        mapper.inputSanction(vo);

        List<String> approverList = requestData.get("approver", List.class);
        if (approverList != null) {
            for (int i = 0; i < approverList.size(); i++) {
                SanctionLineVO lineVO = createSanctionLine(etprCode, approverList.get(i), i, approverList);
                mapper.inputLine(lineVO);
            }
        }

        ReferenceVO referenceVO = new ReferenceVO();
        referenceVO.setElctrnSanctnEtprCode(etprCode);

        List<String> referrerList = requestData.get("referrer", List.class);
        if (referrerList != null) {
            for (String referrer : referrerList) {
                referenceVO.setSanctnRefrnEmplId(referrer);
                mapper.inputRefrn(referenceVO);
            }
        }
    }

    private SanctionLineVO createSanctionLine(String etprCode, String approver, int index, List<String> approverList) throws IOException {
        SanctionLineVO lineVO = new SanctionLineVO();
        lineVO.setElctrnSanctnEtprCode(etprCode);
        lineVO.setElctrnSanctnemplId(approver);
        lineVO.setSanctnLineOrdr(String.valueOf(index + 1));
        lineVO.setCommonCodeSanctProgrs(index == 0 ? "SANCTN013" : "SANCTN014");
        lineVO.setElctrnSanctnFinalAt(index == approverList.size() - 1 ? "Y" : "N");

        try {
            String fileName = mapper.getSign(approver);
            File file = new File(String.format("%s/sign/%s", uploadPath, fileName));

            if (file.exists()) {
                byte[] signImg = FileUtils.readFileToByteArray(file);
                String encodedString = Base64.getEncoder().encodeToString(signImg);
                lineVO.setSanctnLineSign(encodedString.getBytes());
            }
        } catch (FileNotFoundException e) {
            log.error("서명 파일 없음 : {}", approver);
        } catch (IOException e) {
            log.error("파일 읽기 실패 : {}", e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lineVO;
    }

    public SanctionVO loadSanction(String elctrnSanctnEtprCode) throws SQLException {
        SanctionVO sanctionVO = mapper.loadSanction(elctrnSanctnEtprCode);
        if (sanctionVO != null) {
            byte[] imageData = sanctionVO.getElctrnSanctnDrftEmplSign();
            if (imageData != null) {
                String base64ImageData = new String(imageData);
                sanctionVO.setDrftSignImg(base64ImageData);
            }
            sanctionVO.setElctrnSanctnFormatCode(SanctionFormat.valueOf(sanctionVO.getElctrnSanctnFormatCode()).label());
            sanctionVO.setCommonCodeSanctProgrs(SanctionProgress.valueOf(sanctionVO.getCommonCodeSanctProgrs()).label());
            sanctionVO.setCommonCodeDept(Department.valueOf(sanctionVO.getCommonCodeDept()).label());
            sanctionVO.setCommonCodeClsf(ClassOfPosition.valueOf(sanctionVO.getCommonCodeClsf()).label());
        }

        // 결재선 불러오기
        List<SanctionLineVO> lineList = mapper.loadLine(elctrnSanctnEtprCode);
        if (lineList != null) {
            for (SanctionLineVO lineVO : lineList) {
                byte[] imageData = lineVO.getSanctnLineSign();
                if (imageData != null) {
                    String base64ImageData = new String(imageData);
                    lineVO.setSignImg(base64ImageData);
                }
                lineVO.setCommonCodeSanctProgrs(SanctionProgress.valueOf(lineVO.getCommonCodeSanctProgrs()).label());
                lineVO.setCommonCodeDept(Department.valueOf(lineVO.getCommonCodeDept()).label());
                lineVO.setCommonCodeClsf(ClassOfPosition.valueOf(lineVO.getCommonCodeClsf()).label());
            }
            sanctionVO.setLineList(lineList);
        }

        // 침조선 불러오기
        List<ReferenceVO> refrnList = mapper.loadRefrn(elctrnSanctnEtprCode);
        if (refrnList != null) {
            for (ReferenceVO refrnVo : refrnList) {
                refrnVo.setCommonCodeDept(Department.valueOf(refrnVo.getCommonCodeDept()).label());
                refrnVo.setCommonCodeClsf(ClassOfPosition.valueOf(refrnVo.getCommonCodeClsf()).label());
            }
            sanctionVO.setRefrnList(refrnList);
        }

        UploadFileVO file = mapper.loadSanctionFile(elctrnSanctnEtprCode);
        if (file != null) {
            sanctionVO.setFile(file);
        }

        return sanctionVO;
    }


    public List<EmployeeVO> loadAllLine(String emplId, String keyword) throws SQLException {
        List<String> departmentCodes = Arrays.asList("DEPT010", "DEPT011", "DEPT012", "DEPT013", "DEPT014", "DEPT015");
        List<EmployeeVO> allEmployees = new ArrayList<>();
        for (String deptCode : departmentCodes) {
            List<EmployeeVO> deptEmployees = mapper.loadAllLine(deptCode, emplId, keyword);
            for (EmployeeVO vo : deptEmployees) {
                vo.setCommonCodeDept(Department.valueOf(vo.getCommonCodeDept()).label());
                vo.setCommonCodeClsf(ClassOfPosition.valueOf(vo.getCommonCodeClsf()).label());
            }
            allEmployees.addAll(deptEmployees);
        }
        return allEmployees;
    }

    public List<SanctionVO> loadReference(String emplId) throws SQLException {
        List<SanctionVO> list = mapper.loadReference(emplId);
        for (SanctionVO vo : list) {
            vo.setCommonCodeSanctProgrs(SanctionProgress.valueOf(vo.getCommonCodeSanctProgrs()).label());
        }
        return list;
    }

    /* 결재선 즐겨찾기 */
    public void inputBookmark(SanctionBookmarkVO vo) throws SQLException {
        mapper.inputBookmark(vo);
    }

    public List<Map<String, String>> loadBookmark(String emplId) throws SQLException {
        List<SanctionBookmarkVO> list = mapper.loadBookmark(emplId);
        List<Map<String, String>> resultList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (SanctionBookmarkVO vo : list) {
            String jsonLineBookmark = vo.getElctrnSanctnLineBookmark();

            try {
                Map<String, String> lineBookmarkMap = objectMapper.readValue(jsonLineBookmark, new TypeReference<Map<String, String>>() {
                });
                lineBookmarkMap.put("no", vo.getSanctionLineBookmarkSn());
                lineBookmarkMap.put("name", vo.getElctrnSanctnBookmarkName());
                resultList.add(lineBookmarkMap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return resultList;
    }

    public void deleteBookmark(String sanctionLineBookmarkSn) throws SQLException {
        mapper.deleteBookmark(sanctionLineBookmarkSn);
    }

    List<SanctionVO> loadSanctionList(String dept) throws SQLException {
        if (dept.equals("DEPT010")) {
            dept = "인사";
        } else {
            dept = "회계";
        }
        List<SanctionVO> list = mapper.loadSanctionList(dept);
        for (SanctionVO vo : list) {
            vo.setCommonCodeDept(Department.valueOf(vo.getCommonCodeDept()).label());
        }
        return list;
    }
}

