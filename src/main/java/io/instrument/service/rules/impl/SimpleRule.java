package io.instrument.service.rules.impl;

import io.instrument.service.model.Instrument;
import io.instrument.service.rules.Rule;

public class SimpleRule implements Rule {
    @Override
    public Instrument process(Instrument input) {
        return Instrument.from(input);
    }
}
