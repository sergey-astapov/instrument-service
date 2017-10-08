package io.instrument.service.rules;

import io.instrument.service.model.Instrument;

public interface Rule {
    Instrument process(Instrument input);
}
