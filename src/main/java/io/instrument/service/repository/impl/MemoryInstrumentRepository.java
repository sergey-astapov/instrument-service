package io.instrument.service.repository.impl;

import io.instrument.service.model.Instrument;
import io.instrument.service.repository.InstrumentRepository;

import java.util.List;

public class MemoryInstrumentRepository implements InstrumentRepository {
    @Override
    public Instrument add(Instrument instrument) {
        return null;
    }

    @Override
    public List<Instrument> findAll() {
        return null;
    }

    @Override
    public Instrument findByKey(String key) {
        return null;
    }

    @Override
    public Instrument findBySource(String source) {
        return null;
    }
}
