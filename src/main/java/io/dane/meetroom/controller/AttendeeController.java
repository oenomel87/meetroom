package io.dane.meetroom.controller;

import io.dane.meetroom.domain.dto.AttendeeDTO;
import io.dane.meetroom.domain.repository.AttendeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("/attendee")
@RestController
@RequiredArgsConstructor
public class AttendeeController {

    private final AttendeeRepository attendeeRepository;

    @Operation(summary = "예약 참석자 목록 조회", description = "특정 예약의 ID로 예약의 참석자 목록을 조회 합니다.")
    @Transactional
    @PostMapping("")
    public ResponseEntity<List<AttendeeDTO>> fetchAttendees(
            @Parameter(name = "bookId", description = "참석이 예정된 예약의 고유 ID", required = true) @RequestParam(value = "bookId") Long bookId
    ) {
        var entities = this.attendeeRepository.findAllByBook_Id(bookId);
        var attendees = CollectionUtils.isEmpty(entities)
                ? new ArrayList<AttendeeDTO>()
                : entities.stream().map(AttendeeDTO::convert).toList();
        return ResponseEntity.ok(attendees);
    }
}
