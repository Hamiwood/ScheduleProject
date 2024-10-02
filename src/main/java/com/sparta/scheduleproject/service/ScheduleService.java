package com.sparta.scheduleproject.service;

import com.sparta.scheduleproject.dto.ScheduleRequestDto;
import com.sparta.scheduleproject.dto.ScheduleResponseDto;
import com.sparta.scheduleproject.entity.Schedule;
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
        //기본 키를 반환받기 위한 객체
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO schedule (username, password, contents, createdTime, modifiedTime) VALUES (?, ?, ? ,default, default)";

        jdbcTemplate.update( con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, schedule.getUsername());
                    preparedStatement.setString(2, schedule.getPassword());
                    preparedStatement.setString(3, schedule.getContents());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);

        //Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;
    }

    public List<ScheduleResponseDto> getScheduleAll() {
        // DB 조회
        String sql = "SELECT * FROM schedule ORDER BY ModifiedTime DESC, username ASC";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 데이터들을 ScheduleResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String contents = rs.getString("contents");
                String createdTime = rs.getString("createdTime");
                String modifiedTime = rs.getString("modifiedTime");
                return new ScheduleResponseDto(id, username, password, contents, createdTime, modifiedTime);
            }
        });
    }

    public List<ScheduleResponseDto> getScheduleOrderByDate() {
        // DB 조회
        String sql = "SELECT * FROM schedule ORDER BY ModifiedTime DESC";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 데이터들을 ScheduleResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String contents = rs.getString("contents");
                String createdTime = rs.getString("createdTime");
                String modifiedTime = rs.getString("modifiedTime");
                return new ScheduleResponseDto(id, username, password, contents, createdTime, modifiedTime);
            }
        });
    }

    public List<ScheduleResponseDto> getScheduleOrderByUsername() {
        // DB 조회
        String sql = "SELECT * FROM schedule ORDER BY username ASC";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 데이터들을 ScheduleResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String contents = rs.getString("contents");
                String createdTime = rs.getString("createdTime");
                String modifiedTime = rs.getString("modifiedTime");
                return new ScheduleResponseDto(id, username, password, contents, createdTime, modifiedTime);
            }
        });
    }

    public ScheduleResponseDto getSchedule(Long id) {
        // 해당 일정이 DB에 존재하는지 확인
        Schedule schedule = findById(id);
        if(schedule != null) {

            // schedule 내용 수정
            String sql = "SELECT * FROM schedule where id = ?";
            return jdbcTemplate.queryForObject(sql, new RowMapper<ScheduleResponseDto>() {
                @Override
                public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                    // SQL 의 결과로 받아온 데이터들을 ScheduleResponseDto 타입으로 변환해줄 메서드
                    Long id = rs.getLong("id");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String contents = rs.getString("contents");
                    String createdTime = rs.getString("createdTime");
                    String modifiedTime = rs.getString("modifiedTime");
                    return new ScheduleResponseDto(id, username, password, contents, createdTime, modifiedTime);
                }
            }, id);
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
    }

    public Long updateSchedule(Long id, String password, ScheduleRequestDto requestDto) {
        // 해당 일정이 DB에 존재하는지 확인
        Schedule schedule = findById(id);
        if(schedule != null) {
            //패스워드가 일치하는 지 확인
            String pw = getSchedule(id).getPassword();
            if(password.equals(pw)) {
                String sql = "UPDATE schedule SET username = ?, contents = ?, modifiedTime = default WHERE id = ?";
                jdbcTemplate.update(sql, requestDto.getUsername(), requestDto.getContents(), id);
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
        //해당 일정이 DB에 존재하는 지 확인
        Schedule schedule = findById(id);
        if(schedule != null) {
            // 패스워드가 일치하는지 확인
            String pw = getSchedule(id).getPassword();
            if(password.equals(pw)) {
                String sql = "DELETE FROM schedule WHERE id = ?";
                jdbcTemplate.update(sql, id);
            }else{
                System.out.println("비밀번호가 일치하지 않습니다");
            }
            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
    }

    private Schedule findById(Long id) {
        // DB 조회
        String sql = "SELECT * FROM schedule WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setUsername(resultSet.getString("username"));
                schedule.setContents(resultSet.getString("contents"));
                schedule.setContents(resultSet.getString("password"));
                return schedule;
            } else {
                return null;
            }
        }, id);
    }
}
