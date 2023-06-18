package io.dane.meetroom.domain.dto;

import io.dane.meetroom.domain.entity.Attendee;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "회의 참석자 상세 정보")
public class AttendeeDTO {

    @Schema(description = "참석자 ID. 시스템 내에서 지정합니다.",
            example = "3",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long id;

    @Schema(description = "참석자의 계정",
            example = "kim.123",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String account;

    @Schema(description = "참석할 예약의 ID",
            example = "3",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long bookId;

    @Schema(description = "예약 상세",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private BookDTO book;

    public static AttendeeDTO convert(Attendee e) {
        return AttendeeDTO.builder()
                .id(e.getId())
                .account(e.getAccount())
                .bookId(e.getBook().getId())
                .build();
    }
}
