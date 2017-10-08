package io.instrument.service.rules.impl;

import io.instrument.service.model.Instrument;
import io.instrument.service.rules.Rule;

import java.util.Optional;

public class SimpleRule implements Rule {
    @Override
    public Optional<Instrument> process(Instrument instrument) {
        return Optional.of(instrument);
    }
}
