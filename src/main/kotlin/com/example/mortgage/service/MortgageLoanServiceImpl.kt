package com.example.mortgage.service

import com.example.mortgage.model.Customer
import com.example.mortgage.model.MortgageLoan
import com.example.mortgage.model.MortgageLoanStatus
import com.example.mortgage.repository.MortgageLoanRepository
import org.kie.api.runtime.KieContainer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class MortgageLoanServiceImpl : MortgageLoanService {
    @Autowired
    lateinit var repository: MortgageLoanRepository

    @Autowired
    lateinit var customerService: CustomerService

    @Autowired
    lateinit var loanOfficerService: LoanOfficerService

    @Autowired
    lateinit var kContainer: KieContainer

    override fun addMortgageLoan(customer: Customer): MortgageLoan {
        val mortgageLoan = MortgageLoan(customerId = customer.customerId)
        return repository.saveAndFlush(mortgageLoan)
    }

    override fun addMortgageLoan(customer: Customer, loanOfficerId: Long): MortgageLoan {
        val mortgageLoan = MortgageLoan(customerId = customer.customerId, loanOfficerId = loanOfficerId)
        return repository.saveAndFlush(mortgageLoan)
    }

    override fun addLoanOfficer(mortgageId: Long, loanOfficerId: Long) {
        val mortgageLoan = findByMortgageId(mortgageId)
        mortgageLoan.loanOfficerId = loanOfficerId
        repository.saveAndFlush(mortgageLoan)
    }

    override fun findByMortgageId(mortgageId: Long): MortgageLoan = repository.getOne(mortgageId)

    override fun findByCustomerId(customerId: Long): List<MortgageLoan> = repository.findByCustomerId(customerId)

    override fun findByLoanOfficerId(loanOfficerId: Long): List<MortgageLoan> = repository.findByLoanOfficerId(loanOfficerId)

    override fun updateStatus(mortgageId: Long): MortgageLoanStatus {
        val kieSession = kContainer.newKieSession()
        val loan: MortgageLoan = findByMortgageId(mortgageId)
        val customer = customerService.getCustomerById(loan.customerId).get()
        val loanOfficerId = loan.loanOfficerId

        if (loanOfficerId != null) {
            kieSession.insert(loanOfficerService.getLoanOfficerById(loanOfficerId))
        }

        kieSession.insert(loan)
        kieSession.insert(customer)

        kieSession.fireAllRules()
        kieSession.dispose()

        repository.save(loan)

        return loan.statusEnum
    }
}
