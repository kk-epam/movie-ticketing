package com.example.ticketing.repository;

import com.example.ticketing.model.ShowSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    List<ShowSeat> findByShowIdOrderByIdAsc(Long showId);

    /**
     * Locks the requested seats for the rest of the transaction so that two
     * concurrent bookings cannot both claim the same seat.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from ShowSeat s where s.show.id = :showId and s.seatLabel in :labels")
    List<ShowSeat> findForUpdate(@Param("showId") Long showId, @Param("labels") Collection<String> labels);
}
