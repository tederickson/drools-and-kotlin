package com.example.mortgage.service

import com.example.mortgage.digest.LoanOfficerDigest
import com.example.mortgage.model.LoanOfficer
import com.example.mortgage.repository.LoanOfficerRepository
import com.example.mortgage.transform.LoanOfficerDigestTransform
import com.example.mortgage.transform.UpdateLoanOfficer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class LoanOfficerServiceImpl : LoanOfficerService {

    @Autowired
    lateinit var repository: LoanOfficerRepository

    val loanOfficerDigestTransform = LoanOfficerDigestTransform()
    val updateLoanOfficer = UpdateLoanOfficer()

    override fun addLoanOfficer(loanOfficerDigest: LoanOfficerDigest): LoanOfficerDigest {
        val loanOfficer: LoanOfficer = updateLoanOfficer.build(loanOfficerDigest)
        loanOfficer.loanOfficerId = -1

        return loanOfficerDigestTransform.build(repository.save(loanOfficer))
    }

    override fun getLoanOfficerById(id: Long): LoanOfficerDigest? {
        val opt = repository.findById(id)

        if (opt.isPresent) return loanOfficerDigestTransform.build(opt.get())

        return null
    }

    override fun getLoanOfficers(): MutableList<LoanOfficerDigest> {
        return repository.findAll()
            .stream()
            .filter(Objects::nonNull)
            .map { item -> loanOfficerDigestTransform.build(item!!) }
            .collect(Collectors.toList())
    }

    override fun updateLoanOfficer(loanOfficerDigest: LoanOfficerDigest): Boolean {
        val entity = repository.findById(loanOfficerDigest.loanOfficerId)

        if (!entity.isPresent) {
            println("LoanOfficer id ${loanOfficerDigest.loanOfficerId} not in database")
            return false
        }

        repository.save(updateLoanOfficer.build(loanOfficerDigest))
        return true
    }
}
