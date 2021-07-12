package com.github.leuvaarden.testgenericresponse;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

public class ExamplePayload {
    @NotNull
    private final UUID id;
    @NotNull
    private final String context;
    @NotNull
    private final OffsetDateTime at;

    public ExamplePayload(UUID id, String context, OffsetDateTime at) {
        this.id = id;
        this.context = context;
        this.at = at;
    }

    public UUID getId() {
        return id;
    }

    public String getContext() {
        return context;
    }

    public OffsetDateTime getAt() {
        return at;
    }
}
