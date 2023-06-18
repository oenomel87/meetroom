package io.dane.meetroom.controller;

import io.dane.meetroom.domain.dto.BookDTO;
import io.dane.meetroom.domain.dto.RoomDTO;
import io.dane.meetroom.domain.repository.RoomRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/room")
@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomRepository roomRepository;

    @Operation(summary = "회의실 목록 조회", description = "모든 회의실을 조회 한 후 회의실의 배열을 반환 합니다.")
    @Transactional
    @PostMapping("")
    public ResponseEntity<List<RoomDTO>> fetchRooms() {
        var rawList = this.roomRepository.findAll();
        var rooms = CollectionUtils.isEmpty(rawList)
                ? new ArrayList<RoomDTO>()
                    : rawList.stream().map(RoomDTO::convert).toList();
        return ResponseEntity.ok(rooms);
    }

    @Operation(summary = "특정 회의실 정보 조회", description = "회의실의 ID 값을 사용해 특정 회의실의 정보와 예약 내역을 확인한다.")
    @Transactional
    @PostMapping("/{roomId}")
    public ResponseEntity<RoomDTO> fetchRoom(
            @Parameter(name = "회의실 ID") @PathVariable Long roomId
    ) {
        var room = this.roomRepository.findById(roomId);
        return room.map(e -> ResponseEntity.ok(RoomDTO.convert(e)))
                .orElseGet(() -> ResponseEntity.ok().build());
    }
}
