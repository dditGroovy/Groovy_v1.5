package kr.co.groovy.sanction;

import kr.co.groovy.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface SanctionMapper {

    SanctionFormatVO loadFormat(String format) throws SQLException;

    String getSeq(String formatSanctnKnd) throws SQLException;

    int getStatus(@Param("elctrnSanctnDrftEmplId") String elctrnSanctnDrftEmplId, @Param("commonCodeSanctProgrs") String commonCodeSanctProgrs) throws SQLException;

    List<SanctionVO> loadRequest(@Param("elctrnSanctnDrftEmplId") String emplId) throws SQLException;

    void inputSanction(SanctionVO vo);

    void inputLine(SanctionLineVO vo);

    void inputRefrn(ReferenceVO vo);

    /* 결재 문서 불러오기 */

    List<SanctionLineVO> loadAwaiting(@Param("emplId") String emplId);

    List<SanctionLineVO> loadLine(String elctrnSanctnEtprCode);

    List<ReferenceVO> loadRefrn(String elctrnSanctnEtprCode);

    SanctionVO loadSanction(String elctrnSanctnEtprCode);

    UploadFileVO loadSanctionFile(String elctrnSanctnEtprCode);

    String getSign(String emplId);

    /* */

    void approve(@Param("elctrnSanctnemplId") String elctrnSanctnemplId, @Param("elctrnSanctnEtprCode") String elctrnSanctnEtprCode);

    void finalApprove(@Param("elctrnSanctnemplId") String elctrnSanctnemplId, @Param("elctrnSanctnEtprCode") String elctrnSanctnEtprCode);

    void reject(Map<String, Object> map);

    void collect(String elctrnSanctnEtprCode);

    List<EmployeeVO> loadAllLine(@Param("depCode") String depCode, @Param("emplId") String emplId, @Param("keyword") String keyword);

    List<SanctionVO> loadReference(String emplId);

    void inputBookmark(SanctionBookmarkVO vo);

    List<SanctionBookmarkVO> loadBookmark(String emplId);

    void deleteBookmark(String sanctionLineBookmarkSn);

    List<SanctionVO> loadSanctionList(String dept);
}
