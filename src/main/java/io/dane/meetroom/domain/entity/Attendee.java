package io.dane.meetroom.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "attendee")
public class Attendee {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String account;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
