package com.sparta.scheduleproject.service;

import com.sparta.scheduleproject.dto.ScheduleRequestDto;
import com.sparta.scheduleproject.dto.ScheduleResponseDto;
import com.sparta.scheduleproject.entity.Paging;
import com.sparta.scheduleproject.entity.Schedule;
import com.sparta.scheduleproject.repository.ScheduleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ScheduleService {

    private final JdbcTemplate jdbcTemplate;
    public ScheduleService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        //RequestDto > Entity
        Schedule schedule = new Schedule(requestDto);

        //DB 저장
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        //Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(savedSchedule);

        return scheduleResponseDto;
    }

    public List<ScheduleResponseDto> getScheduleAll(int page , int pageSize) {
        Paging paging = new Paging(page, pageSize, 0);
        //DB 조회
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        return scheduleRepository.findAll(paging);
    }

    public List<ScheduleResponseDto> getScheduleOrderByDate(int page , int pageSize) {
        Paging paging = new Paging(page, pageSize, 0);
        //DB조회
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        return scheduleRepository.findAllOrderByDate(paging);
    }

    public List<ScheduleResponseDto> getScheduleOrderByUsername(int page , int pageSize) {
        Paging paging = new Paging(page, pageSize, 0);
        //DB조회
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        return scheduleRepository.findAllOrderByUsername(paging);
    }

    public ScheduleResponseDto getSchedule(Long id) {
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        // 해당 일정이 DB에 존재하는지 확인
        Schedule schedule = scheduleRepository.findById(id);
        if(schedule != null) {
            return scheduleRepository.findOne(id);
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
    }

    public Long updateSchedule(Long id, String password, ScheduleRequestDto requestDto) {
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);

        // 해당 일정이 DB에 존재하는지 확인
        Schedule schedule = scheduleRepository.findById(id);
        if(schedule != null) {
            //패스워드가 일치하는 지 확인
            String pw = getSchedule(id).getPassword();
            if(password.equals(pw)) {
                scheduleRepository.update(id, requestDto);
            }else{
                System.out.println("비밀번호가 일치하지 않습니다");
            }
            // schedule 내용 수정
            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
    }

    public Long deleteSchedule(Long id, String password) {
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);

        //해당 일정이 DB에 존재하는 지 확인
        Schedule schedule = scheduleRepository.findById(id);
        if(schedule != null) {
            // 패스워드가 일치하는지 확인
            String pw = getSchedule(id).getPassword();
            if(password.equals(pw)) {
                scheduleRepository.delete(id);
            }else{
                System.out.println("비밀번호가 일치하지 않습니다");
            }
            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
    }
}
