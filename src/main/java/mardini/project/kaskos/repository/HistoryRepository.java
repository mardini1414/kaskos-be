package mardini.project.kaskos.repository;

import mardini.project.kaskos.dto.HistoryResponse;
import mardini.project.kaskos.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.UUID;

public interface HistoryRepository extends JpaRepository<History, UUID> {
    @Query("""
                SELECT new mardini.project.kaskos.dto.HistoryResponse(h.id, u.name, u.email, h.nominal, h.transactionType, h.createdAt) 
                FROM History h JOIN User u ON u.email = h.userEmail
                WHERE (:email IS NULL OR h.userEmail = :email) ORDER BY h.createdAt DESC
            """)
    Page<HistoryResponse> findAllHistory(String email, Pageable pageable);

    @Query("""
                SELECT new mardini.project.kaskos.dto.HistoryResponse(h.id, u.name, u.email, h.transactionType, h.createdAt) 
                FROM History h JOIN User u ON u.email = h.userEmail
                WHERE (:email IS NULL OR h.userEmail = :email) AND DATE(h.createdAt) = :createdAt ORDER BY h.createdAt DESC
            """)
    Page<HistoryResponse> findAllHistoryByCreatedAt(String email, Date createdAt, Pageable pageable);
}
