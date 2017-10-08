package io.instrument.service.rules;

import io.instrument.service.model.Instrument;

import java.util.Optional;

public interface Rule {
    Optional<Instrument> process(Instrument instrument);
}
