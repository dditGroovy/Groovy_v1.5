package kr.co.groovy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class SanctionVO {
    private String elctrnSanctnEtprCode;
    private String elctrnSanctnFormatCode;
    private String elctrnSanctnSj;
    private String elctrnSanctnDc;
    private String elctrnSanctnDrftEmplId;
    private byte[] elctrnSanctnDrftEmplSign;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date elctrnSanctnRecomDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date elctrnSanctnFinalDate;
    private String commonCodeSanctProgrs;
    private String elctrnSanctnAfterPrcs;

    private String emplNm;
    private String commonCodeDept; // 부서
    private String commonCodeClsf; // 직급
    private String uploadFileStreNm; // 서명 파일

    private String emplId;


    // 결재라인 및 첨부파일
    List<SanctionLineVO> lineList;
    List<ReferenceVO> refrnList;
    UploadFileVO file;
}