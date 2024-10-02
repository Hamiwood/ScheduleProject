package com.sparta.scheduleproject.dto;

import com.sparta.scheduleproject.entity.Schedule;
import lombok.Getter;


@Getter
public class ScheduleResponseDto {
    private Long id;
    private String username;
    private String password;
    private String contents;
    private String createdTime;
    private String modifiedTime;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.username = schedule.getUsername();
        this.password = schedule.getPassword();
        this.contents = schedule.getContents();
        this.createdTime = schedule.getCreatedTime();
        this.modifiedTime = schedule.getModifiedTime();
    }

    public ScheduleResponseDto(Long id, String username, String password, String contents, String createdTime, String modifiedTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.contents = contents;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }
}
