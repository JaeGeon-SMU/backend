package com.guide.run.user.controller;

import com.guide.run.admin.dto.EventTypeCountDto;
import com.guide.run.event.entity.dto.response.get.Count;
import com.guide.run.event.entity.dto.response.get.MyPageEvent;
import com.guide.run.global.jwt.JwtProvider;
import com.guide.run.partner.entity.dto.MyPagePartner;
import com.guide.run.user.dto.GlobalUserInfoDto;
import com.guide.run.user.dto.request.Add1365Dto;
import com.guide.run.user.dto.response.MyPageEventList;
import com.guide.run.user.dto.response.MyPagePartnerList;
import com.guide.run.user.dto.response.ProfileResponse;
import com.guide.run.user.service.MypageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class MypageController {

    private final JwtProvider jwtProvider;
    private final MypageService mypageService;

    @GetMapping("/personal")
    public ResponseEntity<GlobalUserInfoDto> getGlobalUserInfo(HttpServletRequest httpServletRequest){
        String privateId = jwtProvider.extractUserId(httpServletRequest);
        GlobalUserInfoDto response = mypageService.getGlobalUserInfo(privateId);

        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/event-history/count/{userId}")
    public ResponseEntity<Count> getMyEventCount(@PathVariable String userId,
                                                 @RequestParam(defaultValue = "RECRUIT_ALL") String kind,
                                                 @RequestParam(defaultValue = "0") int year){
        Count response = Count.builder()
                .count(mypageService.getMyPageEventsCount(userId, kind, year))
                .build();
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/event-history/{userId}")
    public ResponseEntity<MyPageEventList> getMyEventList(@PathVariable String userId,
                                                          @RequestParam(defaultValue = "0") int start,
                                                          @RequestParam(defaultValue = "10") int limit,
                                                          @RequestParam(defaultValue = "RECRUIT_ALL") String kind,
                                                          @RequestParam(defaultValue = "0") int year){
        MyPageEventList response = MyPageEventList.builder()
                .items(mypageService.getMyPageEvents(userId,start,limit, kind, year))
                .build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<ProfileResponse> getUserProfile(@PathVariable String userId,
                                                          HttpServletRequest request){
        String privateId = jwtProvider.extractUserId(request);
        ProfileResponse response = mypageService.getUserProfile(userId,privateId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/partner-list/{userId}")
    public ResponseEntity<MyPagePartnerList> getMyPartnerList(@PathVariable String userId,
                                                              @RequestParam(defaultValue = "0") int start,
                                                              @RequestParam(defaultValue = "10") int limit,
                                                              @RequestParam(defaultValue = "RECENT") String sort){
        MyPagePartnerList response = MyPagePartnerList.builder()
                .items(mypageService.getMyPagePartners(userId, start, limit, sort))
                .build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/partner-list/count/{userId}")
    public ResponseEntity<Count> getMyPartnerCount(@PathVariable String userId){
        Count response = Count.builder()
                .count(mypageService.getMyPartnersCount(userId))
                .build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("event-type/count/{userId}")
    public ResponseEntity<EventTypeCountDto> getEventTypeCount(@PathVariable String userId){
        return ResponseEntity.ok(mypageService.getMyPageEventTypeCount(userId));
    }

    @PostMapping("/personal/1365id")
    public ResponseEntity<String> add1365(HttpServletRequest httpServletRequest, @RequestBody Add1365Dto add1365Dto){
        String privateId = jwtProvider.extractUserId(httpServletRequest);
        mypageService.add1365(add1365Dto.getId1365(), privateId);
        return ResponseEntity.ok().body("");
    }
}
