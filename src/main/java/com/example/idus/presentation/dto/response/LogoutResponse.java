package com.example.idus.presentation.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
public class LogoutResponse {
    private LocalDateTime logoutTime;
    private Boolean success;

    @Builder
    public LogoutResponse(LocalDateTime logoutTime, Boolean success) {
        this.logoutTime = logoutTime;
        this.success = success;
    }
}
