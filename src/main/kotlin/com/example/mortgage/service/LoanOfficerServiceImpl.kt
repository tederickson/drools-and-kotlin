package com.example.mortgage.service

import com.example.mortgage.model.LoanOfficer
import com.example.mortgage.repository.LoanOfficerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LoanOfficerServiceImpl : LoanOfficerService {

    @Autowired
    lateinit var repository: LoanOfficerRepository

    override fun addLoanOfficer(loanOfficer: LoanOfficer): LoanOfficer = repository.save(loanOfficer)

    override fun getLoanOfficerById(id: Long): LoanOfficer? = repository.findById(id).orElseGet(null)

    override fun getLoanOfficers(): MutableList<LoanOfficer?> = repository.findAll()

    override fun updateLoanOfficer(loanOfficer: LoanOfficer): Boolean {
        val entity = repository.findById(loanOfficer.loanOfficerId)

        if (!entity.isPresent) {
            println("LoanOfficer id ${loanOfficer.loanOfficerId} not in database")
            return false
        }

        repository.save(loanOfficer)
        return true
    }
}
