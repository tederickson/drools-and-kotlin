package com.example.mortgage.transform

import com.example.mortgage.digest.MortgageLoanDigest
import com.example.mortgage.digest.MortgageLoanStatusEnum
import com.example.mortgage.model.MortgageLoan

class MortgageLoanDigestTransform {
    fun build(mortgageLoan: MortgageLoan): MortgageLoanDigest {
        val transform = MortgageLoanStatusEnumTransform()
        val statusEnum: MortgageLoanStatusEnum = transform.build(mortgageLoan.statusEnum)
        val loanOfficerId = mortgageLoan.loanOfficerId ?: -1

        return MortgageLoanDigest(
            mortgageId = mortgageLoan.mortgageId,
            customerId = mortgageLoan.customerId,
            loanOfficerId = loanOfficerId,
            statusEnum = statusEnum
        )
    }
}