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

    void inputSanction(SanctionVO vo) throws SQLException;

    void inputLine(SanctionLineVO vo) throws SQLException;

    void inputRefrn(ReferenceVO vo) throws SQLException;

    /* 결재 문서 불러오기 */

    List<SanctionLineVO> loadAwaiting(@Param("emplId") String emplId) throws SQLException;

    List<SanctionLineVO> loadLine(String elctrnSanctnEtprCode) throws SQLException;

    List<ReferenceVO> loadRefrn(String elctrnSanctnEtprCode) throws SQLException;

    SanctionVO loadSanction(String elctrnSanctnEtprCode) throws SQLException;

    UploadFileVO loadSanctionFile(String elctrnSanctnEtprCode) throws SQLException;

    String getSign(String emplId) throws SQLException;

    /* */

    void approve(@Param("elctrnSanctnemplId") String elctrnSanctnemplId, @Param("elctrnSanctnEtprCode") String elctrnSanctnEtprCode) throws SQLException;

    void finalApprove(@Param("elctrnSanctnemplId") String elctrnSanctnemplId, @Param("elctrnSanctnEtprCode") String elctrnSanctnEtprCode) throws SQLException;

    void reject(Map<String, Object> map) throws SQLException;
    void collect(String elctrnSanctnEtprCode) throws SQLException;

    List<EmployeeVO> loadAllLine(@Param("depCode") String depCode, @Param("emplId") String emplId, @Param("keyword") String keyword) throws SQLException;

    List<SanctionVO> loadReference(String emplId) throws SQLException;

    void inputBookmark(SanctionBookmarkVO vo) throws SQLException;

    List<SanctionBookmarkVO> loadBookmark(String emplId) throws SQLException;

    void deleteBookmark(String sanctionLineBookmarkSn) throws SQLException;

    List<SanctionVO> loadSanctionList(String dept) throws SQLException;
}
