package sowesew01

import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestFor
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Tailor)
@Build([Tailor, Material, SewingMachine])
class TailorSpec extends Specification {

    @Shared  def static SewingMachine sewingMachine
    @Shared  def Material material
    @Shared  def sowesew01.Experience experience


    def setup() {
        sewingMachine = new SewingMachine(nickname: 'Tania', model: 'CR12')
        new Material(materialType: 'Browning', pattern: 'AF01', amount: 10, unit: sowesew01.MaterialUnit.METRES)
        experience = sowesew01.Experience.BEGINNER
    }

    def cleanup() {
    }

    // Spock stuff - Spock seems to be built in
    @Unroll
    void "#firstName, #familyName, #email, #countryOfOrigin, #dateJoined, borrowed, experience, isValid: #isValid"() {
        when:
        domain.firstName = firstName
        domain.familyName = familyName
        domain.email = email
        domain.countryOfOrigin = countryOfOrigin
        domain.dateJoined = dateJoined
        domain.borrowed = borrowed
        domain.experience = experience

        then:
        domain.validate() == isValid

        where:
        firstName | familyName | email                | countryOfOrigin | dateJoined | borrowed      | experience                    | isValid
        'David'   | null       | null                 | null            | null       | null          | null                          | false
        'David'   | 'Browning' | null                 | null            | null       | null          | null                          | false
        'David'   | 'Browning' | 'test0@customer.com' | null            | null       | null          | null                          | false
        'David'   | 'Browning' | 'test0@customer.com' | 'UK'            | null       | null          | null                          | false
        'David'   | 'Browning' | 'test0@customer.com' | 'UK'            | new Date() | sewingMachine | null                          | false
        'David'   | 'Browning' | 'test0@customer.com' | 'UK'            | new Date() | sewingMachine | sowesew01.Experience.BEGINNER | true
    }

    // testCompile "org.grails.plugins:build-test-data:3.0.1"
    // NB the @Build annotation for the class
    void "Tailor - SewingMachine - Material relationships"() {
        given: "A Tailor"
        def tailor = Tailor.build(borrowed: [SewingMachine.build()])
        Material.build()

        expect: "Tailor returns 0 materials"
//        tailor.materials.size() == 1
//
//        and: "Entries in the database"
        Tailor.count() == 1
        SewingMachine.count() == 1
    }

    void 'Test that Tailor has properties firstName, familyName, email and a borrowed sewing machine'() {
        when: 'A tailor is created with the given firstName, familyName and email'
        String firstName = 'David'
        String familyName = 'Browning'
        String email = 'test0@customer.com'
        Tailor tailor = new Tailor(firstName: firstName, familyName: familyName, email: email, borrowed: sewingMachine, materials: material, experience: experience)

        then: 'firstName, familyName, email, borrowed and experience are set to the customer'
        tailor.firstName == firstName
        tailor.familyName == familyName
        tailor.email == email
        tailor.borrowed == sewingMachine
        tailor.experience == experience
    }


    void 'Test that customerName should allow letters and spaces only'() {
        expect: 'Tailor instance is valid/invalid'
        new Tailor(firstName: validTailorFirstName, familyName: validTailorFamilyName, email: validEmail, borrowed: sewingMachine, materials: material, experience: experience).validate()       //
        !new Tailor(firstName: invalidTailorFirstName, familyName: validTailorFamilyName, email: validEmail, borrowed: sewingMachine, materials: material, experience: experience).validate()
        !new Tailor(firstName: validTailorFirstName, invalidTailorFamilyName: invalidTailorFamilyName, email: validEmail, borrowed: sewingMachine, materials: material, experience: experience).validate()

        where: 'Given valid/invalid tailor names'
        validTailorFirstName << ['david', '    david   ', 'david name']
        validTailorFamilyName << ['browning', '    browning   ', 'browning name']
        invalidTailorFirstName << ['123david456', 'david-test', 'david@email.com']
        invalidTailorFamilyName << ['123browning456', 'browning-test', '@email.com']
        validEmail << ['test1@email.com', 'test2@email.com', 'test3@email.com']
    }

    void 'Test that email should have proper format'() {
        expect: 'Tailor instance is valid/invalid'
        new Tailor(firstName: 'David', familyName: 'Browning', email: validEmail, borrowed: sewingMachine, materials: material, experience: experience).validate()
        !new Tailor(firstName: 'David', familyName: 'Browning', email: invalidEmail, borrowed: sewingMachine, materials: material, experience: experience).validate()

        where: 'Given valid/invalid emails'
        validEmail << ['customer@email.com', '1234567890@example.com', 'email@test.museum']
        invalidEmail << ['plainaddress', '@example.com', '#@%^%x!.com']
    }

    void 'Test that email should be unique'() {
        when: 'The first customer is saved'
        String firstName = 'David'
        String familyName = 'Browning'
        String email = 'test@nquav.com'
        Tailor firstTailor = new Tailor(firstName: firstName, familyName: familyName, email: email, borrowed: sewingMachine, materials: material, experience: experience)
        firstTailor.save(flush: true)

        firstName = 'Alex'
        familyName = 'Browning'
        Tailor tailorWithSameEmail = new Tailor(firstName: firstName, familyName: familyName, email: email, borrowed: sewingMachine, materials: material, experience: experience)

        then: 'Another customer with same customerName is invalid'
        Tailor.count() == 1
        !tailorWithSameEmail.save(flush: true)
    }

}
