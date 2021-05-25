package com.example.idus.presentation.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@ApiModel("로그아웃 응답")
public class LogoutResponse {
    @ApiModelProperty(example = "test@naver.com")
    private LocalDateTime logoutTime;
    @ApiModelProperty(example = "true", dataType = "boolean")
    private Boolean success;

    @Builder
    public LogoutResponse(LocalDateTime logoutTime, Boolean success) {
        this.logoutTime = logoutTime;
        this.success = success;
    }
}
