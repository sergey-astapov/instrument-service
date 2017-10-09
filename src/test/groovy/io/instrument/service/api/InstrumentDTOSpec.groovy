package io.instrument.service.api

import io.instrument.service.model.Instrument
import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll

@Title("Instrument DTO")
class InstrumentDTOSpec extends Specification {
    @Unroll("instrument dto created: key=#key, source=#source, lastTradingDate=#lastTradingDate, deliveryDate=#deliveryDate, market=#market, label=#label, exchangeCode=#exchangeCode, tradable=#tradable")
    def "instrument dto created"() {
        def sut = InstrumentDTO.builder()
                .key(key).source(source)
                .lastTradingDate(lastTradingDate)
                .deliveryDate(deliveryDate)
                .market(market).label(label)
                .exchangeCode(exchangeCode)
                .tradable(tradable)
                .build()

        expect:
        assert sut != null
        assert sut.key == key
        assert sut.source.name() == source
        assert sut.lastTradingDate == Instrument.date(lastTradingDate)
        assert sut.deliveryDate == Instrument.date(deliveryDate)
        assert sut.market.name() == market
        assert sut.label == label
        assert sut.exchangeCode == exchangeCode
        assert String.valueOf(sut.tradable).toUpperCase() == String.valueOf(tradable).toUpperCase()

        where:
        key                | source  | lastTradingDate | deliveryDate | market   | label                | exchangeCode | tradable
        "PB_03_2018"       | "LME"   | "15-03-2018"    | "17-03-2018" | "PB"     | "Lead 13 March 2018" | null         | null
        "PRIME_PB_03_2018" | "PRIME" | "14-03-2018"    | "18-03-2018" | "LME_PB" | "Lead 13 March 2018" | "PB_03_2018" | "TRUE"
    }

    @Unroll("instrument dto creation failed: key=#key, source=#source, lastTradingDate=#lastTradingDate, deliveryDate=#deliveryDate, market=#market, label=#label, exchangeCode=#exchangeCode, tradable=#tradable")
    def "instrument dto creation failed"() {
        when:
        Instrument.builder()
                .key(key)
                .source(source)
                .lastTradingDate(lastTradingDate)
                .deliveryDate(deliveryDate)
                .market(market)
                .label(label)
                .exchangeCode(exchangeCode)
                .tradable(tradable)
                .build()

        then:
        thrown(IllegalArgumentException)

        where:
        key                | source  | lastTradingDate | deliveryDate | market   | label                | exchangeCode | tradable
        "PB_03_2018"       | "LME"   | "15-03-2018"    | "17-03-2018" | "PB"     | null                 | null         | null
        "PB_03_2018"       | "LME"   | "15-03-2018"    | "17-03-2018" | "NOT PB" | "Lead 13 March 2018" | null         | null
        "PB_03_2018"       | "LME"   | "15-03-2018"    | "NOT A DATE" | "PB"     | "Lead 13 March 2018" | null         | null
        "PRIME_PB_03_2018" | "PRIME" | "14-03-2018"    | "18-03-2018" | "LME_PB" | "Lead 13 March 2018" | "PB_03_2018" | null
    }
}
