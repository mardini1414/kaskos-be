package mardini.project.kaskos.dto;

import mardini.project.kaskos.enums.TransactionType;

import java.util.Date;
import java.util.UUID;

public record HistoryResponse(
        UUID id,
        String name,
        String email,
        TransactionType type,
        Date createdAt

) {
}
