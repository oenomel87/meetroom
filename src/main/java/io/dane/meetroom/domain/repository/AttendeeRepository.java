package io.dane.meetroom.domain.repository;

import io.dane.meetroom.domain.entity.Attendee;
import io.dane.meetroom.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendeeRepository extends JpaRepository<Attendee, Long> {

    List<Attendee> findAllByBook_Id(Long bookId);

    void deleteByBook(Book book);

    void deleteAllByBook_Id(Long bookId);
}
