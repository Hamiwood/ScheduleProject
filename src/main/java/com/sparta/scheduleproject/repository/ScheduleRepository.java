package com.sparta.scheduleproject.repository;

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

public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;
    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Schedule save(Schedule schedule) {

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

        return schedule;
    }

    public List<ScheduleResponseDto> findAll() {
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

    public List<ScheduleResponseDto> findAllOrderByDate() {
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

    public List<ScheduleResponseDto> findAllOrderByUsername() {
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

    public ScheduleResponseDto findOne(Long id) {

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
    }

    public Long update(Long id, ScheduleRequestDto requestDto) {
        String sql = "UPDATE schedule SET username = ?, contents = ?, modifiedTime = default WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getUsername(), requestDto.getContents(), id);
        return id;
    }

    public Long delete(Long id){
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return id;
    }

    public Schedule findById(Long id) {
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

