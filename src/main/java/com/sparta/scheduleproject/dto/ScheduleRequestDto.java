package com.sparta.scheduleproject.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String username;
    private String password;
    private String contents;
    private String email;
}
