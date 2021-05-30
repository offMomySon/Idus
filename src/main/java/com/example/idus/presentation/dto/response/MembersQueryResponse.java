package com.example.idus.presentation.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel("멤버정보 요청 응답")
public class MembersQueryResponse {
    List<MemberInfo> items;
    long pageCount;
    long totalContent;
    long totalPage;
    boolean hasNext;
    boolean isLast;


    @Builder
    public MembersQueryResponse(List<MemberInfo> items, long pageCount, long totalContent, long totalPage, boolean hasNext, boolean isLast) {
        this.items = items;
        this.pageCount = pageCount;
        this.totalContent = totalContent;
        this.totalPage = totalPage;
        this.hasNext = hasNext;
        this.isLast = isLast;
    }
}
