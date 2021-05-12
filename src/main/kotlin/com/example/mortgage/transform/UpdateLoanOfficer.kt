package com.example.mortgage.transform

import com.example.mortgage.digest.LoanOfficerDigest
import com.example.mortgage.model.LoanOfficer
import com.example.mortgage.model.LoanOfficerActive

class UpdateLoanOfficer {

    fun build(loanOfficerDigest: LoanOfficerDigest): LoanOfficer {
        val activeEnum: LoanOfficerActive = LoanOfficerActive.valueOf(loanOfficerDigest.activeEnum.toString())

        return LoanOfficer(
            loanOfficerId = loanOfficerDigest.loanOfficerId,
            firstName = loanOfficerDigest.firstName,
            lastName = loanOfficerDigest.lastName,
            phone = loanOfficerDigest.phone,
            email = loanOfficerDigest.email,
            managerId = loanOfficerDigest.managerId,
            activeEnum = activeEnum
        )
    }
}