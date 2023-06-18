package io.dane.meetroom.domain.repository;

import io.dane.meetroom.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
