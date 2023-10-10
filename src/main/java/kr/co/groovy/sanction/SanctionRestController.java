package kr.co.groovy.sanction;

import kr.co.groovy.utils.ParamMap;
import kr.co.groovy.vo.EmployeeVO;
import kr.co.groovy.vo.SanctionBookmarkVO;
import kr.co.groovy.vo.SanctionLineVO;
import kr.co.groovy.vo.SanctionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/sanction/api")
public class SanctionRestController {
    final
    SanctionService service;

    public SanctionRestController(SanctionService service) {
        this.service = service;
    }

    /**
     * 전자 결재 승인 처리
     */

    @PutMapping("/approval/{etprCode}")
    public void approve(Principal principal, @PathVariable String etprCode) throws SQLException {
        service.approve(principal.getName(), etprCode);
    }

    @PutMapping("/final/approval/{etprCode}")
    public void finalApprove(Principal principal,  @PathVariable String etprCode) throws SQLException {
        service.finalApprove(principal.getName(), etprCode);
    }

    @PutMapping("/reject")
    public void reject(@RequestBody Map<String, Object> map) throws SQLException {
        service.reject(map);
    }

    @PutMapping("/collect/{etprCode}")
    public void collect(@PathVariable String etprCode) throws SQLException {
        service.collect(etprCode);
    }


    /**
     * 전자 결재 시작
     */

    /* 전자결재 후처리 실행 */
    @PostMapping("/reflection")
    public void startApprove(@RequestBody Map<String, Object> request) {
        service.startApprove(request);
    }

    /* 결재선 포함 결재 문서 내용 insert */
    @PostMapping("/sanction")
    public void inputSanction(@RequestBody ParamMap requestData) throws IOException, SQLException {
        service.inputSanction(requestData);
    }

    /**
     * 결재함 결재 문서 리스트
     */

    @GetMapping("/status")
    public String getStatus(Principal principal, String progrs) throws SQLException {
        return String.valueOf(service.getStatus(principal.getName(), progrs));
    }

    @GetMapping("/request")
    public List<SanctionVO> loadRequest(Principal principal) throws SQLException {
        return service.loadRequest(principal.getName());
    }

    @GetMapping("/awaiting")
    public List<SanctionLineVO> loadAwaiting(Principal principal) throws SQLException {
        return service.loadAwaiting(principal.getName());
    }

    @GetMapping("/reference")
    public List<SanctionVO> loadReference(Principal principal) throws SQLException {
        return service.loadReference(principal.getName());
    }


    /**
     * 결재선 지정 및 즐겨찾기
     */

    @GetMapping("/line")
    public List<EmployeeVO> loadAllLine(Principal principal, @RequestParam(required = false, defaultValue = "") String keyword) throws SQLException {
        return service.loadAllLine(principal.getName(), keyword);
    }

    @PostMapping("/bookmark")
    public void inputBookmark(@RequestBody SanctionBookmarkVO vo) throws SQLException {
        service.inputBookmark(vo);
    }

    @GetMapping("/bookmark")
    public List<Map<String, String>> loadBookmark(Principal principal) throws SQLException {
        return service.loadBookmark(principal.getName());
    }

    @DeleteMapping("/bookmark/{sn}")
    public void deleteBookmark(@PathVariable String sn) throws SQLException {
        service.deleteBookmark(sn);
    }

}
