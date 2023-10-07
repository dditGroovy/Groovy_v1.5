package kr.co.groovy.schedule;

import java.util.List;

import kr.co.groovy.vo.ScheduleVO;

public interface ScheduleMapper {

    public List<ScheduleVO> getSchedule();
    
    public ScheduleVO getOneSchedule(int schdulSn);
    
    public int inputSchedule(ScheduleVO scheduleVO);
    
    public int modifySchedule(ScheduleVO scheduleVO);
    
    public int deleteSchedule(int schdulSn);
    
    //휴가 중인 사원 캘린더에 등록
    public int inputVacationEMPL();
	
}
