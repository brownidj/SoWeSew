package sowesew01

class Tailor {

    String firstName
    String familyName
    String email
    String countryOfOrigin
    Date dateJoined = new Date()
    SewingMachine borrowed
    sowesew01.Experience experience = sowesew01.Experience.BEGINNER

    static hasMany = [materials: Material]

    static constraints = {
        firstName nullable: false, matches: '^[a-zA-Z ]+$'
        familyName nullable: false, matches: '^[a-zA-Z ]+$'
        email nullable: true, email: true, unique: false
        countryOfOrigin nullable: true
        dateJoined nullable: true
        borrowed nullable: true
        materials nullable: true
        experience nullable: false, inList: Arrays.asList(sowesew01.Experience.values())
    }

    @Override
    String toString() {
        return String.format("%s %s", firstName, familyName)
    }
}
