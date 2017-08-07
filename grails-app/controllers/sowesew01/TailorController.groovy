package sowesew01

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
class TailorController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Tailor.list(params), model:[tailorCount: Tailor.count()]
    }

    def show(Tailor tailor) {
        respond tailor
    }

    def create() {
        respond new Tailor(params)
    }

    @Transactional
    def save(Tailor tailor) {
        if (tailor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tailor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tailor.errors, view:'create'
            return
        }

        tailor.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'tailor.label', default: 'Tailor'), tailor.id])
                redirect tailor
            }
            '*' { respond tailor, [status: CREATED] }
        }
    }

    def edit(Tailor tailor) {
        respond tailor
    }

    @Transactional
    def update(Tailor tailor) {
        if (tailor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tailor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tailor.errors, view:'edit'
            return
        }

        tailor.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'tailor.label', default: 'Tailor'), tailor.id])
                redirect tailor
            }
            '*'{ respond tailor, [status: OK] }
        }
    }

    @Transactional
    def delete(Tailor tailor) {

        if (tailor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        tailor.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'tailor.label', default: 'Tailor'), tailor.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'tailor.label', default: 'Tailor'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
