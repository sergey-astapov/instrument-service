package io.instrument.service.repository.impl

import io.instrument.service.api.InstrumentDTO
import io.instrument.service.model.InstrumentFactory
import io.instrument.service.model.Source
import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll

@Title("Memory Instrument Repository")
class MemoryInstrumentRepositorySpec extends Specification {
    def "instrument is absent"() {
        given:
        def sut = new MemoryInstrumentRepository()

        expect:
        assert sut.findAll().size() == 0
        assert !sut.findByKey(key).isPresent()
        assert sut.findBySource(Source.valueOf(source)).isEmpty()
        assert !sut.findBySourceAndKey(Source.valueOf(source), key).isPresent()

        where:
        key                | source
        "PB_03_2018"       | "LME"
        "PRIME_PB_03_2018" | "PRIME"
    }

    @Unroll("instrument added: key=#key, source=#source, lastTradingDate=#lastTradingDate, deliveryDate=#deliveryDate, market=#market, label=#label, exchangeCode=#exchangeCode, tradable=#tradable")
    def "instrument added"() {
        given:
        def sut = new MemoryInstrumentRepository()

        when:
        sut.add(InstrumentFactory.from(InstrumentDTO.builder()
                .key(key).source(source)
                .lastTradingDate(lastTradingDate)
                .deliveryDate(deliveryDate)
                .market(market).label(label)
                .tradable(tradable)
                .partialBuild()))

        then:
        assert sut.findAll().size() == 1
        assert sut.findByKey(key).isPresent()
        assert sut.findBySource(Source.valueOf(source)).size() == 1
        assert sut.findBySourceAndKey(Source.valueOf(source), key).isPresent()

        where:
        key                | source  | lastTradingDate | deliveryDate | market   | label                | tradable
        "PB_03_2018"       | "LME"   | "15-03-2018"    | "17-03-2018" | "PB"     | "Lead 13 March 2018" | "TRUE"
        "PRIME_PB_03_2018" | "PRIME" | "14-03-2018"    | "18-03-2018" | "LME_PB" | "Lead 13 March 2018" | "FALSE"
    }
}
