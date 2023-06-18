package io.dane.meetroom.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GPTPluginController {

    @GetMapping(value = "/.well-known/ai-plugin.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> pluginManifest() {
        /**
         * description for model
         * 회의실 예약 시스템은 현재 이용 가능한 회의실을 조회하고 필요한 회의실을 예약하는 시스템입니다.
         * 이 시스템이 현재 지원하는 기능의 목록 입니다.
         * <지원기능>
         * 1. 전체 회의실 조회
         * 2. 전체 예약 현황 조회
         * 3. 각 예약에 대해 예약한 회의실과 예약 시작일, 종료일 및 참석자 정보 제공.
         * 4. 모든 회의실은 24시간 예약이 가능합니다.
         * 5. 모든 예약은 한 명 이상의 참석자가 선택되어야 합니다.
         * 6. 하나의 회의실은 기간이 겹치치 않는 예약만 받을 수 있습니다.
         * 7. 회의를 예약할 때에는 회의실 ID 와 참석자를 함께 전달해야 합니다.
         */
        var manifest = """
                {
                    "schema_version": "v1",
                    "name_for_human": "회의실 예약 시스템",
                    "name_for_model": "A_meeting_room_booking_system",
                    "description_for_human": "회의실 사용 현황을 확인하고 예약하는 서비스 입니다.",
                    "description_for_model": "The room reservation system is a system that allows you to view currently available rooms and reserve the required rooms. This is a list of features that this system currently supports:<Features> 1. View all rooms 2. View all reservation statuses 3. Provide reserved rooms and reservation start date, end date, and attendee information for each reservation. 4. All rooms are available for booking 24 hours a day. 5. All reservations must have at least one attendee selected. 6. A room can only receive reservations with non-overlapping time periods. 7. When booking a meeting, the room ID and attendees must be communicated together.",
                    "auth": {
                        "type": "none"
                    },
                    "api": {
                        "type": "openapi",
                        "url": "http://localhost:8080/v3/api-docs"
                    },
                    "logo_url": "https://lh3.googleusercontent.com/a/AAcHTtcATGkQGbJt1goR8IcpVEtEP7qh05pwSqOppOSZuQ=s96-c",
                    "contact_email": "ysparknanum@gmail.com",
                    "legal_info_url": "http://www.example.com/legal"
                }
                """;
        return ResponseEntity.ok(manifest);
    }
}
