package sowesew01

class SewingMachine {

    String nickname
    SewingMachineMake make = SewingMachineMake.BROTHER
    String model
    Date dateAcquired = new Date()
    String donor

    static constraints = {
        nickname nullable: false
        make nullable: false
        model nullable: false
        dateAcquired nullable: false
        donor nullable: true, blank: true
    }

    enum SewingMachineMake {
        JANOME,
        BROTHER,
        SINGER,
        TOYOTA
    }

    @Override
    String toString() {
        return nickname + " (" + make.name() + " - " + model + ")"
    }
}
