package com.example.mortgage.service

import com.example.mortgage.digest.CustomerDigest
import com.example.mortgage.digest.MortgageLoanDigest
import com.example.mortgage.digest.MortgageLoanStatusEnum

interface MortgageLoanService {
    fun addMortgageLoan(customer: CustomerDigest): MortgageLoanDigest
    fun addMortgageLoan(customer: CustomerDigest, loanOfficerId: Long): MortgageLoanDigest

    fun addLoanOfficer(mortgageId: Long, loanOfficerId: Long)

    fun findByMortgageId(mortgageId: Long): MortgageLoanDigest

    fun findByCustomerId(customerId: Long): List<MortgageLoanDigest>

    fun findByLoanOfficerId(loanOfficerId: Long): List<MortgageLoanDigest>

    fun updateStatus(mortgageId: Long): MortgageLoanStatusEnum
}
