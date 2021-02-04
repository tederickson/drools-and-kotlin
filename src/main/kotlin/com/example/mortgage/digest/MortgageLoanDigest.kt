package com.example.mortgage.digest

data class MortgageLoanDigest(
        var mortgageId: Long,
        var customerId: Long,
        var loanOfficerId: Long,
        var statusEnum: MortgageLoanStatusEnum
)
