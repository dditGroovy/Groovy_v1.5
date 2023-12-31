package kr.co.groovy.community;

import kr.co.groovy.common.CommonService;
import kr.co.groovy.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("/teamCommunity")
@Slf4j
@Controller
public class CommunityController {
    final CommunityService service;
    final
    CommonService commonService;

    final
    String uploadPath;

    public CommunityController(CommunityService service, CommonService commonService, String uploadPath) {
        this.service = service;
        this.commonService = commonService;
        this.uploadPath = uploadPath;
    }

    @GetMapping("")
    public ModelAndView teamCommunity(Principal principal, ModelAndView mav) {
        String emplId = principal.getName();
        List<SntncVO> sntncList = service.loadPost(emplId);
        mav.addObject("sntncList", sntncList);
        mav.setViewName("community/teamCommunity");
        return mav;
    }

    @PostMapping("/inputPost")
    public String postWrite(String sntncCn, MultipartFile postFile,Principal principal) throws IOException {
        String emplId = principal.getName();
        try {
            SntncVO vo = new SntncVO();
            vo.setSntncWrtingEmplId(emplId);
            vo.setSntncCn(sntncCn);
            service.inputPost(vo, postFile);
            return "redirect:/teamCommunity";
        } catch (IOException e) {
            log.error("파일 업로드 중 예외 발생: " + e.getMessage());
            return "redirect:/teamCommunity";
        }
    }
    @ResponseBody
    @PutMapping("/modifyPost")
    public String modifyPost(@RequestBody Map<String, Object> map,Principal principal){
        String emplId = principal.getName();
        try {
            map.put("sntncWrtingEmplId", emplId);
            int result = service.modifyPost(map);
            return Integer.toString(result);
        } catch (Exception e) {
            log.error("포스트 수정 중 예외 발생: " + e.getMessage());
            return "error";
        }
    }
    /*  포스트 삭제 */
    @ResponseBody
    @DeleteMapping("/deletePost")
    public String deletePost(@RequestBody Map<String, Object> map,Principal principal){
        String emplId = principal.getName();
        try {
            map.put("sntncWrtingEmplId", emplId);
            service.deletePost(map);
            return "success";
        } catch (Exception e) {
            log.error("포스트 삭제 중 예외 발생: " + e.getMessage());
            return "error";
        }

    }
    /* 좋아요 구현 */
    @ResponseBody
    @PostMapping("/inputRecommend")
    public int inputRecommend(RecommendVO vo,Principal principal){
        String emplId = principal.getName();
        try {
            vo.setRecomendEmplId(emplId);
            service.inputRecommend(vo);
            String sntncEtprCode = vo.getSntncEtprCode();
            int recommendCnt = service.loadRecommend(sntncEtprCode);
            return recommendCnt;
        }catch (Exception e) {
            log.error("좋아요 인풋 중 예외 발생: " + e.getMessage());
            return 0;
        }

    }
    @ResponseBody
    @PostMapping("/deleteRecommend")
    public int deleteRecommend(RecommendVO vo,Principal principal){
        String emplId = principal.getName();
        try {
            vo.setRecomendEmplId(emplId);
            service.deleteRecommend(vo);
            String sntncEtprCode = vo.getSntncEtprCode();
            int recommendCnt = service.loadRecommend(sntncEtprCode);
            return recommendCnt;
        }catch (Exception e) {
            log.error("좋아요 삭제 중 예외 발생: " + e.getMessage());
            return 0;
        }
        
    }

    @ResponseBody
    @PostMapping("/inputAnswer")
    public int inputAnswer(@RequestBody Map<String, Object> map,Principal principal){
        String emplId = principal.getName();
        try {
            map.put("answerWrtingEmplId",emplId);
            String sntncEtprCode = (String) map.get("sntncEtprCode");
            service.inputAnswer(map);
            int answerCnt = service.loadAnswerCnt(sntncEtprCode);
            return answerCnt;
        }catch (Exception e) {
            log.error("댓글 등록 중 예외 발생: " + e.getMessage());
            return 0;
        }
        
    }
    @ResponseBody
    @PostMapping("/loadAnswer")
    public List<AnswerVO> loadAnswer(String sntncEtprCode){

            List<AnswerVO> answerList = service.loadAnswer(sntncEtprCode);
            return answerList;

    }

    @ResponseBody
    @PostMapping("/inputTeamNoti")
    public String inputTeamNoti(@RequestBody Map<String, Object> map,Principal principal){
        String emplId = principal.getName();
        try {
            map.put("sntncWrtingEmplId",emplId);
            service.inputTeamNoti(map);
            return "success";
        }catch (Exception e) {
            log.error("공지 등록 중 예외 발생: " + e.getMessage());
            return "error";
        }


    }

    @ResponseBody
    @PostMapping("/loadTeamNoti")
    public List<SntncVO> loadTeamNoti(Principal principal){
        String emplId = principal.getName();
        return service.loadTeamNoti(emplId);
    }
    @ResponseBody
    @PutMapping("/modifyTeamNoti")
    public String loadTeamNoti(@RequestBody Map<String, Object> map,Principal principal){
        String emplId = principal.getName();
        try {
            map.put("sntncWrtingEmplId",emplId);
            service.modifyTeamNoti(map);
            return "success";
        }catch (Exception e) {
            log.error("공지 수정 중 예외 발생: " + e.getMessage());
            return "error";
        }
    }

    @ResponseBody
    @GetMapping("/loadAllRegistVote")
    public List<VoteRegisterVO> loadAllRegistVote(Principal principal){
        String emplId = principal.getName();
        List<VoteRegisterVO> voteList = service.loadAllRegistVote(emplId);
        return voteList;
    }
    @ResponseBody
    @PostMapping("/inputVote")
    public String inputVote(@RequestBody Map<String, Object> map,Principal principal){
        String emplId = principal.getName();
        try {
            map.put("votePartcptnEmpId",emplId);
            service.inputVote(map);
            return "success";
        }catch (Exception e) {
            log.error("투표 요청 중 예외 발생: " + e.getMessage());
            return "error";
        }

    }
    @ResponseBody
    @DeleteMapping("/deleteVote")
    public String deleteVote(@RequestBody Map<String, Object> map,Principal principal){
        String emplId = principal.getName();
        try {
            map.put("votePartcptnEmpId",emplId);
            service.deleteVote(map);
            return "success";
        }catch (Exception e) {
            log.error("투표 삭제 중 예외 발생: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/inputVoteRegist")
    @ResponseBody
    public String inputVoteRegist(VoteRegisterVO vo,Principal principal){
        String emplId = principal.getName();
        try {
            vo.setVoteRegistEmpId(emplId);
            service.inputVoteRegist(vo);
            return "success";
        }catch (Exception e) {
            log.error("투표 등록 중 예외 발생: " + e.getMessage());
            return "error";
        }

    }

    @ResponseBody
    @PutMapping("/updateVoteRegistAt")
    public String updateVoteRegistAt(@RequestBody String voteRegistNo){
        try {
            service.updateVoteRegistAt(voteRegistNo);
            return "success";
        }catch (Exception e) {
            log.error("투표 상태 수정 중 예외 발생: " + e.getMessage());
            return "error";
        }
    }
}
