package org.example.expert.domain.manager.repository;

import org.example.expert.domain.manager.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface LogRepository extends JpaRepository<Log,Long> {

    @Query("SELECT l FROM Log l WHERE l.eventTime < :cutoffDate")
    List<Log> findLogsBeforeDate(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Transactional
    @Modifying
    @Query("DELETE FROM Log l WHERE l.eventTime < :cutoffDate")
    Integer deleteLogsBeforeDate(@Param("cutoffDate") LocalDateTime cutoffDate);

}
