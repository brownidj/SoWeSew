package sowesew01

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(SewingMachine)
class SewingMachineSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }


    void "Test that SewingMachine has make, model, dateAcquired"() {
        when: "A SewingMachine is created with the given make, model, dateAcquired"
        SewingMachine.SewingMachineMake make = SewingMachine.SewingMachineMake.BROTHER
        String model  = 'MK21'
        Date date = new Date()
        SewingMachine sewingMachine = new SewingMachine(make: make, model: model, dateAcquired: date)

        then: "make, model, dateAcquired are set to the sewing machine"
        sewingMachine.make == make
        sewingMachine.model == model
        sewingMachine.dateAcquired == date
    }
}
