package io.dane.meetroom.domain.repository;

import io.dane.meetroom.domain.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
