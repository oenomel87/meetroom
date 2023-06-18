package io.dane.meetroom.controller;

import io.dane.meetroom.domain.dto.BookDTO;
import io.dane.meetroom.domain.entity.Attendee;
import io.dane.meetroom.domain.entity.Book;
import io.dane.meetroom.domain.repository.AttendeeRepository;
import io.dane.meetroom.domain.repository.BookRepository;
import io.dane.meetroom.domain.repository.RoomRepository;
import io.dane.meetroom.tool.DateTool;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("/book")
@RestController
@RequiredArgsConstructor
public class BookController {

    private final RoomRepository roomRepository;

    private final BookRepository bookRepository;

    private final AttendeeRepository attendeeRepository;

    @Operation(summary = "예약 목록 조회", description = "모든 예약을 조회 한 후 예약의 배열을 반환 합니다.")
    @Transactional
    @PostMapping("")
    public ResponseEntity<List<BookDTO>> fetchBooks(
            @Parameter(name = "room", description = "회의실의 고유 ID, 해당 회의실의 예약을 조회할 수 있는 조건", example = "20", allowEmptyValue = true)
            @RequestParam(value = "room", required = false)
            Long room,
            @Parameter(name = "start", description = "해당 값 이후에 시작하는 예약을 조회하기 위한 조건", example = "2023-04-05 13:30", allowEmptyValue = true)
            @RequestParam(value = "start", required = false)
            String start,
            @Parameter(name = "end", description = "해당 값 이전에 완료하는 예약을 조회하기 위한 조건", example = "2023-04-05 16:30", allowEmptyValue = true)
            @RequestParam(value = "end", required = false)
            String end
    ) {
        var startDate = DateTool.convertStringToDate(start);
        var endDate = DateTool.convertStringToDate(end);
        var entities = this.bookRepository.findAll();
        var books = CollectionUtils.isEmpty(entities)
                ? new ArrayList<BookDTO>()
                    : entities.stream()
                        .filter(b -> this.checkPeriod(b, startDate, endDate))
                        .filter(b -> room == null || b.getRoom().getId().equals(room))
                        .map(BookDTO::convert).toList();
        return ResponseEntity.ok(books);
    }

    private boolean checkPeriod(Book book, LocalDateTime start, LocalDateTime end) {
        var startPeriod = start == null || !book.getEnd().isBefore(start);
        var endPeriod = end == null || !book.getStart().isAfter(end);
        return startPeriod && endPeriod;
    }

    @Operation(summary = "특정 예약 정보 조회", description = "예약의 ID 값을 사용해 특정 예약의 상세 정보를 조회 한다.")
    @Transactional
    @PostMapping("/{bookId}")
    public ResponseEntity<BookDTO> fetchBook(
            @Parameter(name = "id", description = "상세 조회를 할 예약의 고유 ID", example = "12") @PathVariable Long bookId
    ) {
        var book = this.bookRepository.findById(bookId);
        return book.map(e -> ResponseEntity.ok(BookDTO.convert(e)))
                .orElseGet(() -> ResponseEntity.ok().build());
    }

    @Operation(
            summary = "회의실 예약하기",
            description = "예약 정보를 전달 받아 회의실을 예약 합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "저장된 예약 정보를 다시 전달한다.", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "400", description = "전달 받은 roomId 로 회의실을 찾을 수 없음")
            }
    )
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/save")
    public ResponseEntity<BookDTO> book(@RequestBody BookDTO book) {
        var room = this.roomRepository.findById(book.getRoomId());

        if(room.isEmpty()) {
            log.error("찾을 수 없는 회의실 입니다. room id - {}", book.getRoomId());
            return ResponseEntity.badRequest().build();
        }

        var entity = Book.builder()
                .id(book.getId())
                .start(DateTool.convertStringToDate(book.getStart()))
                .end(DateTool.convertStringToDate(book.getEnd()))
                .booker(book.getBooker())
                .room(room.get())
                .build();
        var savedBook = this.bookRepository.save(entity);
        var originAttendees = savedBook.getAttendees();
        var attendees = book.getAttendees().stream()
                .map(a -> Attendee.builder()
                        .account(a.getAccount())
                        .book(savedBook)
                        .build())
                .filter(a -> CollectionUtils.isEmpty(originAttendees) || originAttendees.stream().noneMatch(o -> o.getAccount().equals(a.getAccount())))
                .toList();
        var savedAttendees = this.attendeeRepository.saveAll(attendees);
        savedBook.setAttendees(savedAttendees);

        return ResponseEntity.ok(BookDTO.convert(savedBook));
    }

    @Operation(summary = "특정 예약 취소 및 삭제", description = "예약의 ID 값을 사용해 특정 예약을 취소 후 삭제 합니다.")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/cancel")
    public ResponseEntity<String> cancel(
            @Parameter(name = "id", description = "취소 및 삭제할 예약의 고유 ID", required = true) @RequestParam(value = "id") Long bookId
    ) {
        this.attendeeRepository.deleteAllByBook_Id(bookId);
        this.bookRepository.deleteById(bookId);
        return ResponseEntity.ok().build();
    }
}
