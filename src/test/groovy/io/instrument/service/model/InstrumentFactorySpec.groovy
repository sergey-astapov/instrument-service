package io.instrument.service.model

import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll

import java.util.stream.Collectors

@Title("Instrument Factory")
class InstrumentFactorySpec extends Specification {
    @Unroll("instrument created: key=#key, source=#source, lastTradingDate=#lastTradingDate, deliveryDate=#deliveryDate, market=#market, label=#label, exchangeCode=#exchangeCode, tradable=#tradable")
    def "instrument created from args"() {
        def reslt = InstrumentFactory.from(args)

        expect:
        assert reslt != null
        assert reslt.key == key
        assert reslt.source.name() == source
        assert reslt.lastTradingDate == Instrument.date(lastTradingDate)
        assert reslt.deliveryDate == Instrument.date(deliveryDate)
        assert reslt.market.name() == market
        assert reslt.label == label
        assert reslt.exchangeCode == exchangeCode
        assert String.valueOf(reslt.tradable).toUpperCase() == String.valueOf(tradable).toUpperCase()

        where:
        key                | source  | lastTradingDate | deliveryDate | market   | label                | exchangeCode | tradable
        "PB_03_2018"       | "LME"   | "15-03-2018"    | "17-03-2018" | "PB"     | "Lead 13 March 2018" | null         | null
        "PRIME_PB_03_2018" | "PRIME" | "14-03-2018"    | "18-03-2018" | "LME_PB" | "Lead 13 March 2018" | "PB_03_2018" | "TRUE"

        args = toArgs([key, source, lastTradingDate, deliveryDate, market, label, exchangeCode, tradable])
    }

    @Unroll("instrument creation failed: key=#key, source=#source, lastTradingDate=#lastTradingDate, deliveryDate=#deliveryDate, market=#market, label=#label, exchangeCode=#exchangeCode, tradable=#tradable")
    def "instrument creation failed"() {
        when:
        InstrumentFactory.from(args)

        then:
        thrown(IllegalArgumentException)

        where:
        key                | source  | lastTradingDate | deliveryDate | market    | label                | exchangeCode | tradable
        "PB_03_2018"       | "LME"   | "15-03-2018"    | "17-03-2018" | "PB"      | null                 | null         | null
        "PB_03_2018"       | "LME"   | "15-03-2018"    | "17-03-2018" | "NOT PB"  | "Lead 13 March 2018" | null         | null
        "PB_03_2018"       | "LME"   | "15-03-2018"    | "NOT A DATE" | "PB"      | "Lead 13 March 2018" | null         | null
        "PRIME_PB_03_2018" | "PRIME" | "14-03-2018"    | "18-03-2018" | "LME_PB"  | "Lead 13 March 2018" | "PB_03_2018" | null

        args = toArgs([key, source, lastTradingDate, deliveryDate, market, label, exchangeCode, tradable])
    }

    static String[] toArgs(List<String> list) {
        list.stream()
                .filter { x -> x != null }
                .collect(Collectors.toList()) as String[]
    }
}
