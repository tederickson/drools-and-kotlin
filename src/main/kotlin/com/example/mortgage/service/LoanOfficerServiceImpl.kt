package com.example.mortgage.service

import com.example.mortgage.digest.LoanOfficerActiveEnum
import com.example.mortgage.digest.LoanOfficerDigest
import com.example.mortgage.model.LoanOfficer
import com.example.mortgage.model.LoanOfficerActive
import com.example.mortgage.repository.LoanOfficerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class LoanOfficerServiceImpl : LoanOfficerService {

    @Autowired
    lateinit var repository: LoanOfficerRepository

    override fun addLoanOfficer(loanOfficerDigest: LoanOfficerDigest): LoanOfficerDigest {
        val loanOfficer: LoanOfficer = loanOfficerTransform(loanOfficerDigest)
        loanOfficer.loanOfficerId = -1

        return loanOfficerDigestTransform(repository.save(loanOfficer))
    }

    override fun getLoanOfficerById(id: Long): LoanOfficerDigest? {
        val opt = repository.findById(id)

        if (opt.isPresent) return loanOfficerDigestTransform(opt.get())

        return null
    }

    override fun getLoanOfficers(): MutableList<LoanOfficerDigest> {
        return repository.findAll()
                .stream()
                .filter(Objects::nonNull)
                .map { item -> loanOfficerDigestTransform(item!!) }
                .collect(Collectors.toList())
    }

    override fun updateLoanOfficer(loanOfficerDigest: LoanOfficerDigest): Boolean {
        val entity = repository.findById(loanOfficerDigest.loanOfficerId)

        if (!entity.isPresent) {
            println("LoanOfficer id ${loanOfficerDigest.loanOfficerId} not in database")
            return false
        }

        repository.save(loanOfficerTransform(loanOfficerDigest))
        return true
    }

    fun loanOfficerDigestTransform(loanOfficer: LoanOfficer): LoanOfficerDigest {
        val activeEnum: LoanOfficerActiveEnum = LoanOfficerActiveEnum.valueOf(loanOfficer.activeEnum.toString())

        return LoanOfficerDigest(loanOfficerId = loanOfficer.loanOfficerId,
                firstName = loanOfficer.firstName,
                lastName = loanOfficer.lastName,
                phone = loanOfficer.phone,
                email = loanOfficer.email,
                managerId = loanOfficer.managerId,
                activeEnum = activeEnum)
    }

    fun loanOfficerTransform(loanOfficerDigest: LoanOfficerDigest): LoanOfficer {
        val activeEnum: LoanOfficerActive = LoanOfficerActive.valueOf(loanOfficerDigest.activeEnum.toString())

        return LoanOfficer(loanOfficerId = loanOfficerDigest.loanOfficerId,
                firstName = loanOfficerDigest.firstName,
                lastName = loanOfficerDigest.lastName,
                phone = loanOfficerDigest.phone,
                email = loanOfficerDigest.email,
                managerId = loanOfficerDigest.managerId,
                activeEnum = activeEnum)
    }
}
