package com.sparta.scheduleproject.controller;

import com.sparta.scheduleproject.dto.ScheduleRequestDto;
import com.sparta.scheduleproject.dto.ScheduleResponseDto;
import com.sparta.scheduleproject.service.ScheduleService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Create
    @PostMapping("")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.createSchedule(requestDto);
    }

    // 다건조회 >> 수정일, username 둘다 적용
    @GetMapping("/{page}/{pageSize}")

    public List<ScheduleResponseDto> getScheduleAll(@PathVariable int page, @PathVariable int pageSize) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.getScheduleAll(page, pageSize);
    }

    //다건 조회 >> 수정일로 정렬
    @GetMapping("/modifiedTime/{page}/{pageSize}")
    public List<ScheduleResponseDto> getScheduleAllOrderByDate(@PathVariable int page, @PathVariable int pageSize) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.getScheduleOrderByDate(page, pageSize);
    }

    //다건 조회 >> username으로 정렬
    @GetMapping("/username/{page}/{pageSize}")
    public List<ScheduleResponseDto> getScheduleAllOrderByUsername(@PathVariable int page, @PathVariable int pageSize) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.getScheduleOrderByUsername(page, pageSize);
    }

    //단건 조회
    @GetMapping("/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id){
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.getSchedule(id);
    }

    //update
    @PutMapping("/{id}/{password}")
    public Long updateSchedule(@PathVariable Long id, @PathVariable String password, @RequestBody ScheduleRequestDto requestDto) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.updateSchedule(id, password, requestDto);
    }

    //delete
    @DeleteMapping("/{id}/{password}")
    public Long deleteSchedule(@PathVariable Long id, @PathVariable String password) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.deleteSchedule(id,password);
    }
}
