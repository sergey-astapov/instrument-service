package io.instrument.service.repository;

import io.instrument.service.model.Instrument;
import io.instrument.service.model.Source;

import java.util.List;
import java.util.Optional;

public interface InstrumentRepository {
    Instrument add(Instrument instrument);
    List<Instrument> findAll();
    List<Instrument> findBySource(Source source);
    Optional<Instrument> findByKey(String key);
    Optional<Instrument> findBySourceAndKey(Source source, String key);
}
