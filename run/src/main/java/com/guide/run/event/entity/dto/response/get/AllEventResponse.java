package com.guide.run.event.entity.dto.response.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AllEventResponse {
    private List<AllEvent> items;
}
