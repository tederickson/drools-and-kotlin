package com.example.mortgage.digest

data class LoanOfficerDigest(
        var loanOfficerId: Long,
        var firstName: String,
        var lastName: String,
        var phone: String,
        var email: String,
        var managerId: Long,
        var activeEnum: LoanOfficerActiveEnum
)
