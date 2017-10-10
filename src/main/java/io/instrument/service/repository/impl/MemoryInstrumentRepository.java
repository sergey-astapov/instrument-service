package io.instrument.service.repository.impl;

import io.instrument.service.model.Instrument;
import io.instrument.service.model.Source;
import io.instrument.service.repository.InstrumentRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemoryInstrumentRepository implements InstrumentRepository {
    private final List<Instrument> instruments = new LinkedList<>();

    @Override
    public Instrument add(Instrument instrument) {
        if (findByKey(instrument.getKey()).isPresent()) {
            throw new IllegalArgumentException("Instrument with key: " + instrument.getKey() + "already exists");
        }
        instruments.add(instrument);
        return instrument;
    }

    @Override
    public List<Instrument> findAll() {
        return new ArrayList<>(instruments);
    }

    @Override
    public Optional<Instrument> findByKey(String key) {
        List<Instrument> list = instruments.stream()
                .filter(x -> key.equals(x.getKey()))
                .collect(Collectors.toList());
        switch (list.size()) {
            case 0:  return Optional.empty();
            case 1:  return Optional.of(list.get(0));
            default: throw new IllegalStateException("Duplicates found for key: " + key);
        }
    }

    @Override
    public List<Instrument> findBySource(Source source) {
        return instruments.stream()
                .filter(x -> source == x.getSource())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Instrument> findBySourceAndKey(Source source, String key) {
        List<Instrument> list = instruments.stream()
                .filter(x -> key.equals(x.getKey()) && source == x.getSource())
                .collect(Collectors.toList());
        switch (list.size()) {
            case 0:  return Optional.empty();
            case 1:  return Optional.of(list.get(0));
            default: throw new IllegalStateException("Duplicates found for source: " + source + ", key: " + key);
        }
    }
}
