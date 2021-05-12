package com.example.mortgage.service

import com.example.mortgage.digest.CustomerDigest
import com.example.mortgage.digest.MortgageLoanDigest
import com.example.mortgage.digest.MortgageLoanStatusEnum
import com.example.mortgage.model.MortgageLoan
import com.example.mortgage.repository.MortgageLoanRepository
import com.example.mortgage.transform.MortgageLoanDigestTransform
import com.example.mortgage.transform.MortgageLoanStatusEnumTransform
import org.kie.api.runtime.KieContainer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors


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

    val mortgageLoanDigestTransform: MortgageLoanDigestTransform = MortgageLoanDigestTransform()

    override fun addMortgageLoan(customer: CustomerDigest): MortgageLoanDigest {
        val mortgageLoan = MortgageLoan(customerId = customer.customerId)
        return mortgageLoanDigestTransform.build(repository.saveAndFlush(mortgageLoan))
    }

    override fun addMortgageLoan(customer: CustomerDigest, loanOfficerId: Long): MortgageLoanDigest {
        val mortgageLoan = MortgageLoan(customerId = customer.customerId, loanOfficerId = loanOfficerId)
        return mortgageLoanDigestTransform.build(repository.saveAndFlush(mortgageLoan))
    }

    override fun addLoanOfficer(mortgageId: Long, loanOfficerId: Long) {
        val mortgageLoan: MortgageLoan = repository.getOne(mortgageId)
        mortgageLoan.loanOfficerId = loanOfficerId

        repository.saveAndFlush(mortgageLoan)
    }

    override fun findByMortgageId(mortgageId: Long): MortgageLoanDigest {
        return mortgageLoanDigestTransform.build(repository.getOne(mortgageId))
    }

    override fun findByCustomerId(customerId: Long): List<MortgageLoanDigest> {
        return repository.findByCustomerId(customerId)
            .stream()
            .filter(Objects::nonNull)
            .map { item -> mortgageLoanDigestTransform.build(item!!) }
            .collect(Collectors.toList())
    }

    override fun findByLoanOfficerId(loanOfficerId: Long): List<MortgageLoanDigest> {
        return repository.findByLoanOfficerId(loanOfficerId).stream()
            .filter(Objects::nonNull)
            .map { item -> mortgageLoanDigestTransform.build(item!!) }
            .collect(Collectors.toList())
    }

    override fun updateStatus(mortgageId: Long): MortgageLoanStatusEnum {
        val kieSession = kContainer.newKieSession()
        val loan: MortgageLoan = repository.getOne(mortgageId)
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

        val transform = MortgageLoanStatusEnumTransform()

        return transform.build(loan.statusEnum)
    }

}
