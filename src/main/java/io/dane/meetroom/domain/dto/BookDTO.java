package io.dane.meetroom.domain.dto;

import io.dane.meetroom.domain.entity.Book;
import io.dane.meetroom.tool.DateTool;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "예약 상세 정보")
public class BookDTO {

    @Schema(description = "예약 ID. 새로 예약을 등록할 때에는 시스템에서 지정합니다. 단 세부 내용을 변경할 때에는 변경할 예약의 ID 값이 추가되어야 합니다.",
            example = "1",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long id;

    @Schema(description = "예약 시작 일시",
            example = "2023-04-05 13:30",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String start;

    @Schema(description = "예약 종료 일시",
            example = "2023-04-05 15:00",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String end;

    @Schema(description = "예약자의 Account",
            example = "dane.park",
            defaultValue = "ChatGPT",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String booker = "ChatGPT";

    @Schema(description = "예약한 회의실의 ID",
            example = "3",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Long roomId;

    @Schema(description = "예약한 회의실의 상세 정보",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private RoomDTO room;

    @Schema(description = "회의 참석자 목록",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<AttendeeDTO> attendees;

    public static BookDTO convert(Book e) {
        var attendees = CollectionUtils.isEmpty(e.getAttendees())
                ? new ArrayList<AttendeeDTO>()
                    : e.getAttendees().stream().map(AttendeeDTO::convert).toList();
        return BookDTO.builder()
                .id(e.getId())
                .start(DateTool.convertDateToString(e.getStart()))
                .end(DateTool.convertDateToString(e.getEnd()))
                .booker(e.getBooker())
                .roomId(e.getRoom().getId())
                .attendees(attendees)
                .build();
    }
}
