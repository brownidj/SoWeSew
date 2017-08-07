package sowesew01

import grails.test.mixin.*
import spock.lang.*

@TestFor(TailorController)
@Mock([Tailor, SewingMachine, Material])
class TailorControllerSpec extends Specification {

    def material1, material2, material3

    def setup() {
        material1 = new Material(pattern: 'AP091', amount: 10)
        material2 = new Material(pattern: 'AP092', amount: 10)
        material3 = new Material(pattern: 'AP093', amount: 10)
    }

    def populateValidParams(params) {
        assert params != null

        params["firstName"] = 'Rahila'
        params["familyName"] = 'Rahila'
        params["email"] = 'someone@somewhere.com'
        params["borrowed.nickname"] = 'Andy'
        params["borrowed.make"] = SewingMachine.SewingMachineMake.BROTHER
        params["borrowed.model"] = 'MK21'
        params["materials"] = [material1, material2, material3]
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.tailorList
            model.tailorCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.tailor!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def tailor = new Tailor()
            tailor.validate()
            controller.save(tailor)

        then:"The create view is rendered again with the correct model"
            model.tailor!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            tailor = new Tailor(params)

            controller.save(tailor)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/tailor/show/1'
            controller.flash.message != null
            Tailor.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def tailor = new Tailor(params)
            controller.show(tailor)

        then:"A model is populated containing the domain instance"
            model.tailor == tailor
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def tailor = new Tailor(params)
            controller.edit(tailor)

        then:"A model is populated containing the domain instance"
            model.tailor == tailor
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/tailor/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def tailor = new Tailor()
            tailor.validate()
            controller.update(tailor)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.tailor == tailor

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            tailor = new Tailor(params).save(flush: true)
            controller.update(tailor)

        then:"A redirect is issued to the show action"
            tailor != null
            response.redirectedUrl == "/tailor/show/$tailor.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/tailor/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def tailor = new Tailor(params).save(flush: true)

        then:"It exists"
            Tailor.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(tailor)

        then:"The instance is deleted"
            Tailor.count() == 0
            response.redirectedUrl == '/tailor/index'
            flash.message != null
    }


}
