package sowesew01

import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Material)
@Build([Material])
class MaterialSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void 'Test that Material has properties: pattern, amount , units, type'() {
        when: 'A material is created with the given pattern, amount, units, type'
        String pattern = 'AP01'
        Integer amount = 10
        Material material = new Material(pattern: pattern, amount: amount, unit: sowesew01.MaterialUnit.METRES, materialType: sowesew01.MaterialType.MATERIAL)

        then: 'pattern, amount , units, type are set to the material'
        material.pattern == pattern
        material.amount == amount
        material.unit == sowesew01.MaterialUnit.METRES
        material.materialType == sowesew01.MaterialType.MATERIAL
    }

    // Spock stuff - Spock seems to be built in
    @Unroll
    void "#amount, #pattern, #date, #unit, #materialType, isValid: #isValid"() {
        when:
        domain.date = date
        domain.materialType = materialType
        domain.pattern = pattern
        domain.amount = amount
        domain.unit = unit

        then:
        domain.validate() == isValid

        where:
        date        | materialType                      | pattern  | amount   | unit                            | isValid
        new Date()  | null                              | null     | null     | null                            | false
        new Date()  | sowesew01.MaterialType.MATERIAL   | null     | null     | null                            | false
        new Date()  | sowesew01.MaterialType.MATERIAL   | 'AP01'   | null     | null                            | false
        new Date()  | sowesew01.MaterialType.MATERIAL   | 'AP01'   | 100      | null                            | false
        new Date()  | sowesew01.MaterialType.MATERIAL   | 'AP01'   | 10       | sowesew01.MaterialUnit.METRES   | true
        new Date()  | sowesew01.MaterialType.MATERIAL   | 'AP01'   | 0        | sowesew01.MaterialUnit.METRES   | false
    }


}
