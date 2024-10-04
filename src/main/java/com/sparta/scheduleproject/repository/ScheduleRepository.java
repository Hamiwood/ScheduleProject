package com.sparta.scheduleproject.repository;

import com.sparta.scheduleproject.dto.ScheduleRequestDto;
import com.sparta.scheduleproject.dto.ScheduleResponseDto;
import com.sparta.scheduleproject.entity.Paging;
import com.sparta.scheduleproject.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Schedule save(Schedule schedule) {

        String userSql = "INSERT INTO user(username, email, createdTime, modifiedTime) VALUES (?, ?, default, default)";

        //DB 저장
        //기본 키를 반환받기 위한 객체
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update( con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(userSql,
                            new String[]{"userid"});
                    preparedStatement.setString(1, schedule.getUsername());
                    preparedStatement.setString(2, schedule.getEmail());
                    return preparedStatement;
                },
                keyHolder);

        Long userId = keyHolder.getKey().longValue();

        String scheduleSql = "INSERT INTO schedule (username, password, contents, createdTime, modifiedTime, userid) VALUES (?, ?, ? ,default, default, ?)";

        jdbcTemplate.update( con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(scheduleSql,
                            Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, schedule.getUsername());
                    preparedStatement.setString(2, schedule.getPassword());
                    preparedStatement.setString(3, schedule.getContents());
                    preparedStatement.setLong(4, userId);
                    return preparedStatement;
                },
                keyHolder);


        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);

        return schedule;
    }

    public List<ScheduleResponseDto> findAll(Paging paging) {

        // 데이터의 총 개수 조회
        String countSql = "SELECT COUNT(*) FROM schedule";
        int totalRecords = jdbcTemplate.queryForObject(countSql, Integer.class);

        // 총 데이터 개수를 기반으로 Paging 객체 생성
        paging = new Paging(paging.getPage(), paging.getPageSize(), totalRecords);

        // 범위를 넘어선 페이지인 경우 빈 배열 반환
        if (paging.getPage() > paging.getTotalPages()) {
            return List.of();
        }

        // DB 조회
        String sql = "SELECT * FROM schedule ORDER BY ModifiedTime DESC, username ASC LIMIT ? OFFSET ?";

        return jdbcTemplate.query(sql, new Object[]{paging.getPageSize(), paging.getOffset()}, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 데이터들을 ScheduleResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String contents = rs.getString("contents");
                String createdTime = rs.getString("createdTime");
                String modifiedTime = rs.getString("modifiedTime");
                Long userid = rs.getLong("userid");
                return new ScheduleResponseDto(id, username, password, contents, createdTime, modifiedTime, userid);
            }
        });
    }

    public List<ScheduleResponseDto> findAllOrderByDate(Paging paging) {

        // 데이터의 총 개수 조회
        String countSql = "SELECT COUNT(*) FROM schedule";
        int totalRecords = jdbcTemplate.queryForObject(countSql, Integer.class);

        // 총 데이터 개수를 기반으로 Paging 객체 생성
        paging = new Paging(paging.getPage(), paging.getPageSize(), totalRecords);

        // 범위를 넘어선 페이지인 경우 빈 배열 반환
        if (paging.getPage() > paging.getTotalPages()) {
            return List.of();
        }

        // DB 조회
        String sql = "SELECT * FROM schedule ORDER BY ModifiedTime DESC LIMIT ? OFFSET ?";

        return jdbcTemplate.query(sql, new Object[]{paging.getPageSize(), paging.getOffset()}, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 데이터들을 ScheduleResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String contents = rs.getString("contents");
                String createdTime = rs.getString("createdTime");
                String modifiedTime = rs.getString("modifiedTime");
                Long userid = rs.getLong("userid");
                return new ScheduleResponseDto(id, username, password, contents, createdTime, modifiedTime, userid);
            }
        });
    }

    public List<ScheduleResponseDto> findAllOrderByUsername(Paging paging) {

        // 데이터의 총 개수 조회
        String countSql = "SELECT COUNT(*) FROM schedule";
        int totalRecords = jdbcTemplate.queryForObject(countSql, Integer.class);

        // 총 데이터 개수를 기반으로 Paging 객체 생성
        paging = new Paging(paging.getPage(), paging.getPageSize(), totalRecords);

        // 범위를 넘어선 페이지인 경우 빈 배열 반환
        if (paging.getPage() > paging.getTotalPages()) {
            return List.of();
        }

        // DB 조회
        String sql = "SELECT * FROM schedule ORDER BY username ASC LIMIT ? OFFSET ?";

        return jdbcTemplate.query(sql, new Object[]{paging.getPageSize(), paging.getOffset()}, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 데이터들을 ScheduleResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String contents = rs.getString("contents");
                String createdTime = rs.getString("createdTime");
                String modifiedTime = rs.getString("modifiedTime");
                Long userid = rs.getLong("userid");
                return new ScheduleResponseDto(id, username, password, contents, createdTime, modifiedTime, userid);
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
                Long userid = rs.getLong("userid");
                return new ScheduleResponseDto(id, username, password, contents, createdTime, modifiedTime, userid);
            }
        }, id);
    }

    @Transactional
    public Long update(Long id, ScheduleRequestDto requestDto) {

        //해당 id 값에 맞는 userid를 가져옴
        String userSql = "SELECT userid FROM schedule WHERE id = ?";
        Long userId = jdbcTemplate.queryForObject(userSql, new Object[]{id}, Long.class);

        String sqlSchedule = "UPDATE schedule SET username = ?, contents = ?, modifiedTime = default WHERE id = ?";
        jdbcTemplate.update(sqlSchedule, requestDto.getUsername(), requestDto.getContents(), id);

        String sqlUser = "UPDATE user SET modifiedTime = default WHERE userid = ?";
        jdbcTemplate.update(sqlUser, userId);
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

