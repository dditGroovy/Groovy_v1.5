package kr.co.groovy.commute;

import kr.co.groovy.vo.CommuteVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/commute")
public class CommuteController {
    final CommuteService commuteService;

    public CommuteController(CommuteService commuteService) {
        this.commuteService = commuteService;
    }

    @GetMapping("/main")
    public String CommuteMain(Principal principal, Model model) {
        List<CommuteVO> commuteVOList = commuteService.getCommuteByCurrentMonth(principal.getName());
        model.addAttribute("commuteVOList", commuteVOList);
        return "employee/commute";
    }

    @ResponseBody
    @GetMapping("/getCommute/{dclzEmplId}")
    public CommuteVO getCommute(@PathVariable String dclzEmplId) {

        CommuteVO commuteVO = commuteService.getCommute(dclzEmplId);
        if (commuteVO==null) {
            commuteVO = new CommuteVO();
        }
        return commuteVO;
    }

    @ResponseBody
    @GetMapping("/getMaxWeeklyWorkTime/{dclzEmplId}")
    public String getMaxWeeklyWorkTime(@PathVariable String dclzEmplId) {
        return Integer.toString(commuteService.getMaxWeeklyWorkTime(dclzEmplId));
    }

    @ResponseBody
    @PostMapping("/insertAttend")
    public String insertAttend(CommuteVO commuteVO) {
        return Integer.toString(commuteService.insertAttend(commuteVO));
    }

    @ResponseBody
    @PutMapping("/updateCommute/{dclzEmplId}")
    public String updateCommute(@PathVariable String dclzEmplId) {
        return Integer.toString(commuteService.updateCommute(dclzEmplId));
    }

    @ResponseBody
    @GetMapping("/getWeeklyAttendTime/{dclzEmplId}")
    public List<String> getWeeklyAttendTime(@PathVariable String dclzEmplId) {
        return commuteService.getWeeklyAttendTime(dclzEmplId);
    }

    @ResponseBody
    @GetMapping("/getWeeklyLeaveTime/{dclzEmplId}")
    public List<String> getWeeklyLeaveTime(@PathVariable String dclzEmplId) {
        return commuteService.getWeeklyLeaveTime(dclzEmplId);
    }

    @ResponseBody
    @GetMapping("/getAllYear/{dclzEmplId}")
    public List<String> getAllYear(@PathVariable String dclzEmplId) {
        return commuteService.getAllYear(dclzEmplId);
    }

    @ResponseBody
    @GetMapping("/getAllMonth")
    public List<String> getAllMonth(String year, String dclzEmplId) {
        if (year == null) {
            year = "0";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("year", year);
        map.put("dclzEmplId", dclzEmplId);

        return commuteService.getAllMonth(map);
    }

    @ResponseBody
    @GetMapping("/getCommuteByYearMonth")
    public List<CommuteVO>getCommuteByYearMonth(String year, String month, String dclzEmplId) {
        return commuteService.getCommuteByYearMonth(year, month, dclzEmplId);
    }

    @ResponseBody
    @GetMapping("/getAttend/{dclzEmplId}")
    public CommuteVO getAttend(@PathVariable String dclzEmplId) {
        return commuteService.getAttend(dclzEmplId);
    }

}
