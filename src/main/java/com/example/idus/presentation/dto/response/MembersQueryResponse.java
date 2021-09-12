package com.example.idus.presentation.dto.response;

import com.example.idus.presentation.dto.OrderItemQuery;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MembersQueryResponse {
    List<OrderItemQuery> items;
    long pageCount;
    long totalContent;
    long totalPage;
    boolean hasNext;
    boolean isLast;


    @Builder
    public MembersQueryResponse(List<OrderItemQuery> items, long pageCount, long totalContent, long totalPage, boolean hasNext, boolean isLast) {
        this.items = items;
        this.pageCount = pageCount;
        this.totalContent = totalContent;
        this.totalPage = totalPage;
        this.hasNext = hasNext;
        this.isLast = isLast;
    }
}
