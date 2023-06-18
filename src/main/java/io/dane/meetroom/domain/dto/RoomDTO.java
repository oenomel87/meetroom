package io.dane.meetroom.domain.dto;

import io.dane.meetroom.domain.entity.Room;
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
@Schema(description = "회의실 상세 정보")
public class RoomDTO {

    @Schema(description = "회의실 ID",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "회의실 이름",
            example = "D3",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "회의실 예약 내역",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<BookDTO> books;

    public static RoomDTO convert(Room e) {
        var books = CollectionUtils.isEmpty(e.getBooks())
                ? new ArrayList<BookDTO>()
                    : e.getBooks().stream().map(BookDTO::convert).toList();
        return RoomDTO.builder()
                .id(e.getId())
                .name(e.getName())
                .books(books)
                .build();
    }
}
