package com.example.idus.presentation.dto.response;

import com.example.idus.infrastructure.enums.Gender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@ApiModel("내 정보 응답")
public class UserMeResponse {
    @ApiModelProperty(example = "가힣azAZ")
    private String name;
    @ApiModelProperty(example = "lettercase")
    private String nickname;
    @ApiModelProperty(example = "010-1111-1111")
    private Long phoneNumber;
    @ApiModelProperty(example = "test@naver.com")
    private String email;
    @ApiModelProperty(example = "MEN")
    private Gender gender;
    
    @Builder
    public UserMeResponse(String name, String nickname, Long phoneNumber, String email, Gender gender) {
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
    }
}
