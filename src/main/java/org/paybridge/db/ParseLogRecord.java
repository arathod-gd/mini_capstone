package org.paybridge.db;

import java.time.LocalDateTime;

public record ParseLogRecord(
        long id,
        String rawMessage,
        String status,
        String payload,
        String errorCode,
        LocalDateTime createdAt
) {
}
