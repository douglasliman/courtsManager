package com.quadraOuro.domain.models;

import java.util.Optional;

public class CourtFilter {
    private final Optional<String> type;
    private final Optional<CourtStatus> status;

    public CourtFilter(Optional<String> type, Optional<CourtStatus> status) {
        this.type = type;
        this.status = status;
    }

    public Optional<String> getType() { return type; }
    public Optional<CourtStatus> getStatus() { return status; }
}