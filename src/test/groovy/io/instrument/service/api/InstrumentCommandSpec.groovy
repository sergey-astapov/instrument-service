package io.instrument.service.api

import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll

import java.util.stream.Collectors

@Title("Instrument Command")
class InstrumentCommandSpec extends Specification {
    @Unroll("instrument command created: key=#key, source=#source, lastTradingDate=#lastTradingDate, deliveryDate=#deliveryDate, market=#market, label=#label, exchangeCode=#exchangeCode, tradable=#tradable")
    def "instrument command created from args"() {
        def cmd = InstrumentCommand.from(args)

        expect:
        assert cmd != null
        assert cmd.key == key
        assert cmd.source == source
        assert cmd.lastTradingDate == InstrumentCommand.date(lastTradingDate)
        assert cmd.deliveryDate == InstrumentCommand.date(deliveryDate)
        assert cmd.market == market
        assert cmd.label == label
        assert cmd.exchangeCode == Optional.ofNullable(exchangeCode)
        cmd.tradable.map { x -> assert x == Boolean.valueOf(tradable) }

        where:
        key                | source  | lastTradingDate | deliveryDate | market   | label                | exchangeCode | tradable
        "PB_03_2018"       | "LME"   | "15-03-2018"    | "17-03-2018" | "PB"     | "Lead 13 March 2018" | null         | null
        "PRIME_PB_03_2018" | "PRIME" | "14-03-2018"    | "18-03-2018" | "LME_PB" | "Lead 13 March 2018" | "PB_03_2018" | "TRUE"

        args = toArgs([key, source, lastTradingDate, deliveryDate, market, label, exchangeCode, tradable])
    }

    @Unroll("instrument command creation failed: key=#key, source=#source, lastTradingDate=#lastTradingDate, deliveryDate=#deliveryDate, market=#market, label=#label, exchangeCode=#exchangeCode, tradable=#tradable")
    def "instrument command creation failed"() {
        when:
        InstrumentCommand.from(args)

        then:
        thrown(IllegalArgumentException)

        where:
        key                | source  | lastTradingDate | deliveryDate | market   | label                | exchangeCode | tradable
        "PB_03_2018"       | "LME"   | "15-03-2018"    | "17-03-2018" | "PB"     | null                 | null         | null
        "PB_03_2018"       | "LME"   | "15-03-2018"    | "NOT A DATE" | "PB"     | "Lead 13 March 2018" | null         | null
        "PRIME_PB_03_2018" | "PRIME" | "14-03-2018"    | "18-03-2018" | "LME_PB" | "Lead 13 March 2018" | "PB_03_2018" | null

        args = toArgs([key, source, lastTradingDate, deliveryDate, market, label, exchangeCode, tradable])
    }

    private static String[] toArgs(List<String> list) {
        list.stream()
                .filter { x -> x != null }
                .collect(Collectors.toList()) as String[]
    }
}
