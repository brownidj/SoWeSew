package sowesew01

class Material {

    sowesew01.MaterialType materialType = sowesew01.MaterialType.MATERIAL
    sowesew01.MaterialUnit unit = sowesew01.MaterialUnit.METRES
    Integer amount
    String pattern
    Date date = new Date()

    static constraints = {
        date nullable: false
        materialType nullable: false, inList: Arrays.asList(sowesew01.MaterialType.values())
        pattern nullable: true
        amount nullable: false, min: 1
        unit nullable: false
    }

    @Override
    String toString() {
        return String.format("%s (%s, %d %s)", materialType.name(), pattern, amount, unit.name())
    }
}
