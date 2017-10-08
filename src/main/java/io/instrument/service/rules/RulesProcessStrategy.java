package io.instrument.service.rules;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

public interface RulesProcessStrategy<T> {
    Function<Collection<T>, Stream<T>> stream();
}
