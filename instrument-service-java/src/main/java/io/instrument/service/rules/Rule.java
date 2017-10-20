package io.instrument.service.rules;

import io.instrument.service.api.InstrumentDTO;

import java.util.Optional;

public interface Rule {
    Optional<InstrumentDTO> process(InstrumentDTO instrument);
}
