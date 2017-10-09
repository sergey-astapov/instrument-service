package io.instrument.service.model

import io.instrument.service.api.InstrumentDTO
import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll

@Title("Instrument Factory")
class InstrumentFactorySpec extends Specification {
    @Unroll("instrument created: key=#key, source=#source, lastTradingDate=#lastTradingDate, deliveryDate=#deliveryDate, market=#market, label=#label, exchangeCode=#exchangeCode, tradable=#tradable")
    def "instrument created from args"() {
        def sut = InstrumentFactory.from(InstrumentDTO.builder()
                .key(key).source(source)
                .lastTradingDate(lastTradingDate)
                .deliveryDate(deliveryDate)
                .market(market).label(label)
                .tradable(tradable)
                .partialBuild())

        expect:
        assert sut != null
        assert sut.key == key
        assert sut.source.name() == source
        assert sut.lastTradingDate == Instrument.date(lastTradingDate)
        assert sut.deliveryDate == Instrument.date(deliveryDate)
        assert sut.market.name() == market
        assert sut.label == label
        assert String.valueOf(sut.tradable).toUpperCase() == String.valueOf(tradable).toUpperCase()

        where:
        key                | source  | lastTradingDate | deliveryDate | market   | label                | tradable
        "PB_03_2018"       | "LME"   | "15-03-2018"    | "17-03-2018" | "PB"     | "Lead 13 March 2018" | "TRUE"
        "PRIME_PB_03_2018" | "PRIME" | "14-03-2018"    | "18-03-2018" | "LME_PB" | "Lead 13 March 2018" | "FALSE"
    }

    @Unroll("instrument creation failed: key=#key, source=#source, lastTradingDate=#lastTradingDate, deliveryDate=#deliveryDate, market=#market, label=#label, exchangeCode=#exchangeCode, tradable=#tradable")
    def "instrument creation failed"() {
        when:
        InstrumentFactory.from(InstrumentDTO.builder()
                .key(key).source(source)
                .lastTradingDate(lastTradingDate)
                .deliveryDate(deliveryDate)
                .market(market).label(label)
                .tradable(tradable)
                .partialBuild())

        then:
        thrown(IllegalArgumentException)

        where:
        key                | source  | lastTradingDate | deliveryDate | market    | label                | tradable
        "PB_03_2018"       | "LME"   | "15-03-2018"    | "17-03-2018" | "PB"      | null                 | null
        "PB_03_2018"       | "LME"   | "15-03-2018"    | "17-03-2018" | "NOT PB"  | "Lead 13 March 2018" | null
        "PB_03_2018"       | "LME"   | "15-03-2018"    | "NOT A DATE" | "PB"      | "Lead 13 March 2018" | null
        "PRIME_PB_03_2018" | "PRIME" | "14-03-2018"    | "18-03-2018" | "LME_PB"  | "Lead 13 March 2018" | null
    }
}
