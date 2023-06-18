package io.dane.meetroom.domain.criteria;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "예약 검색 조건 객체")
public class BookCriteria {

    @Schema(description = "예약의 고유 ID",
            example = "3",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long bookId;

    @Schema(description = "예약 시작 일시",
            example = "2023-04-05 13:30",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String start;

    @Schema(description = "예약 종료 일시",
            example = "2023-04-05 16:30",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String end;

    @Schema(description = "예약자의 Account",
            example = "dane.park",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String booker;

    @Schema(description = "회의실의 고유 ID",
            example = "3",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long roomId;

    @Schema(description = "예약한 회의실의 이름",
            example = "M4",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String roomName;
}
