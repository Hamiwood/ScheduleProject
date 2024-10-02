package com.sparta.scheduleproject.controller;

import com.sparta.scheduleproject.dto.ScheduleRequestDto;
import com.sparta.scheduleproject.dto.ScheduleResponseDto;
import com.sparta.scheduleproject.service.ScheduleService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Create
    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.createSchedule(requestDto);
    }

    // 다건조회 >> 수정일, username 둘다 적용
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getScheduleAll() {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.getScheduleAll();
    }

    //다건 조회 >> 수정일로 정렬
    @GetMapping("/schedules/modifiedTime")
    public List<ScheduleResponseDto> getScheduleAllOrderByDate() {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.getScheduleOrderByDate();
    }

    //다건 조회 >> username으로 정렬
    @GetMapping("/schedules/username")
    public List<ScheduleResponseDto> getScheduleAllOrderByUsername() {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.getScheduleOrderByUsername();
    }

    //단건 조회
    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id){
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.getSchedule(id);
    }

    //update
    @PutMapping("/schedules/{id}/{password}")
    public Long updateSchedule(@PathVariable Long id, @PathVariable String password, @RequestBody ScheduleRequestDto requestDto) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.updateSchedule(id, password, requestDto);
    }

    //delete
    @DeleteMapping("/schedules/{id}/{password}")
    public Long deleteSchedule(@PathVariable Long id, @PathVariable String password) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.deleteSchedule(id,password);
    }
}
