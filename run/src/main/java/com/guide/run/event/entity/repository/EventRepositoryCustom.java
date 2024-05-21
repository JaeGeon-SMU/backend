package com.guide.run.event.entity.repository;

import com.guide.run.admin.dto.EventDto;
import com.guide.run.event.entity.dto.response.get.MyEvent;
import com.guide.run.event.entity.dto.response.get.MyPageEvent;
import com.guide.run.event.entity.type.EventRecruitStatus;


import java.util.List;

public interface EventRepositoryCustom {
    List<MyPageEvent> findMyEventAfterYear(String privateId, int start, int limit, String kind, int year);

    long countMyEventAfterYear(String privateId, String kind, int year);

    List<EventDto> sortAdminEvent(int start, int limit);
    long sortAdminEventCount();
    List<MyEvent> findMyEventByYear(String privateId, int year, EventRecruitStatus eventRecruitStatus);

}
