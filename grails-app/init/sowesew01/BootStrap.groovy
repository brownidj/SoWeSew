package sowesew01

class BootStrap {


    def init = { servletContext ->

        def machine01 = new SewingMachine(nickname: 'Andy', make: SewingMachine.SewingMachineMake.BROTHER,
                dateAcquired: new Date(), model: 'N1182', donor: 'Andy Cole').save()
        def machine02 = new SewingMachine(nickname: 'GumTree', make: SewingMachine.SewingMachineMake.BROTHER,
                dateAcquired: new Date(), model: 'N1182', donor: 'Andy Cole').save()
        def machine03 = new SewingMachine(nickname: 'AirMiles', make: SewingMachine.SewingMachineMake.BROTHER,
                dateAcquired: new Date(), model: 'N1182', donor: 'Andy Cole').save()


        def tailor01 = new Tailor(firstName: 'Rahila', familyName: 'Tactik', countryOfOrigin: 'Indonesia', borrowed: machine01).save()
        tailor01.borrowed = machine01

        def tailor02 = new Tailor(firstName: 'Kara', familyName: 'Kara', countryOfOrigin: 'Korea', borrowed: machine02).save()
        tailor01.borrowed = machine01

        def tailor03 = new Tailor(firstName: 'Anerlie', familyName: 'Anerlie', countryOfOrigin: 'Norway', borrowed: machine03).save()
        tailor01.borrowed = machine01

        tailor01.addToMaterials(new Material(pattern: 'AP01', amount: 5, unit: sowesew01.MaterialUnit.METRES, materialType: sowesew01.MaterialType.MATERIAL))
        tailor01.addToMaterials(new Material(pattern: 'VW02', amount:  70, unit: sowesew01.MaterialUnit.CENTIMETRES, materialType: sowesew01.MaterialType.VELCRO))
        tailor01.addToMaterials(new Material(pattern: 'B03', amount: 15, unit: sowesew01.MaterialUnit.ITEMS, materialType: sowesew01.MaterialType.BUTTON))

        tailor02.addToMaterials(new Material(pattern: 'AP01', amount: 5, unit: sowesew01.MaterialUnit.METRES, materialType: sowesew01.MaterialType.MATERIAL))
        tailor02.addToMaterials(new Material(pattern: 'VW02', amount:  30, unit: sowesew01.MaterialUnit.CENTIMETRES, materialType: sowesew01.MaterialType.VELCRO))
        tailor03.addToMaterials(new Material(pattern: 'B03', amount: 15, unit: sowesew01.MaterialUnit.ITEMS, materialType: sowesew01.MaterialType.BUTTON))

        tailor01.addToMaterials(new Material(pattern: 'AP02', amount: 5, unit: sowesew01.MaterialUnit.METRES, materialType: sowesew01.MaterialType.MATERIAL))
        tailor01.addToMaterials(new Material(pattern: 'VW03', amount:  100, unit: sowesew01.MaterialUnit.CENTIMETRES, materialType: sowesew01.MaterialType.VELCRO))
        tailor01.addToMaterials(new Material(pattern: 'B04', amount: 20, unit: sowesew01.MaterialUnit.ITEMS, materialType: sowesew01.MaterialType.BUTTON))

    }

    def destroy = {
    }
}
