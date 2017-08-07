package sowesew01

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
class SewingMachineController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond SewingMachine.list(params), model:[sewingMachineCount: SewingMachine.count()]
    }

    def show(SewingMachine sewingMachine) {
        respond sewingMachine
    }

    def create() {
        respond new SewingMachine(params)
    }

    @Transactional
    def save(SewingMachine sewingMachine) {
        if (sewingMachine == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (sewingMachine.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond sewingMachine.errors, view:'create'
            return
        }

        sewingMachine.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'sewingMachine.label', default: 'SewingMachine'), sewingMachine.id])
                redirect sewingMachine
            }
            '*' { respond sewingMachine, [status: CREATED] }
        }
    }

    def edit(SewingMachine sewingMachine) {
        respond sewingMachine
    }

    @Transactional
    def update(SewingMachine sewingMachine) {
        if (sewingMachine == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (sewingMachine.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond sewingMachine.errors, view:'edit'
            return
        }

        sewingMachine.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'sewingMachine.label', default: 'SewingMachine'), sewingMachine.id])
                redirect sewingMachine
            }
            '*'{ respond sewingMachine, [status: OK] }
        }
    }

    @Transactional
    def delete(SewingMachine sewingMachine) {

        if (sewingMachine == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        sewingMachine.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'sewingMachine.label', default: 'SewingMachine'), sewingMachine.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'sewingMachine.label', default: 'SewingMachine'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
