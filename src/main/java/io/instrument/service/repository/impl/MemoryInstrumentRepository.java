package io.instrument.service.repository.impl;

import io.instrument.service.model.Instrument;
import io.instrument.service.model.Source;
import io.instrument.service.repository.InstrumentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MemoryInstrumentRepository implements InstrumentRepository {
    private final Map<String, Instrument> instruments = new ConcurrentHashMap<>();

    @Override
    public Instrument add(Instrument instrument) {
        String key = makeKey(instrument.getKey(), instrument.getSource());
        if (instruments.containsKey(key) || instruments.putIfAbsent(key, instrument) != null) {
            throw new IllegalArgumentException("Instrument with key: " + key + "already exists");
        }
        instruments.put(key, instrument);
        return instrument;
    }

    @Override
    public List<Instrument> findAll() {
        return new ArrayList<>(instruments.values());
    }

    @Override
    public List<Instrument> findBySource(Source source) {
        return instruments.values().stream()
                .filter(x -> source == x.getSource())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Instrument> findBySourceAndKey(Source source, String key) {
        return Optional.ofNullable(instruments.get(makeKey(key, source)));
    }

    private static String makeKey(String key, Source source) {
        return key + "-" + source.name();
    }
}
