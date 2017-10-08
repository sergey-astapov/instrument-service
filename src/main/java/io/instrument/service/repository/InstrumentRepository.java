package io.instrument.service.repository;

import io.instrument.service.model.Instrument;

import java.util.List;

public interface InstrumentRepository {
    Instrument add(Instrument instrument);
    List<Instrument> findAll();
    Instrument findByKey(String key);
    Instrument findBySource(String source);
}
