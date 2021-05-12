package com.example.mortgage.transform

import com.example.mortgage.digest.LoanOfficerActiveEnum
import com.example.mortgage.digest.LoanOfficerDigest
import com.example.mortgage.model.LoanOfficer

class LoanOfficerDigestTransform {

    fun build(loanOfficer: LoanOfficer): LoanOfficerDigest {
        val activeEnum: LoanOfficerActiveEnum = LoanOfficerActiveEnum.valueOf(loanOfficer.activeEnum.toString())

        return LoanOfficerDigest(
            loanOfficerId = loanOfficer.loanOfficerId,
            firstName = loanOfficer.firstName,
            lastName = loanOfficer.lastName,
            phone = loanOfficer.phone,
            email = loanOfficer.email,
            managerId = loanOfficer.managerId,
            activeEnum = activeEnum
        )
    }

}