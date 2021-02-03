package com.example.mortgage.service

import com.example.mortgage.model.Customer
import com.example.mortgage.model.MortgageLoan
import com.example.mortgage.model.MortgageLoanStatus

interface MortgageLoanService {
    fun addMortgageLoan(customer: Customer): MortgageLoan
    fun addMortgageLoan(customer: Customer, loanOfficerId: Long): MortgageLoan

    fun addLoanOfficer(mortgageId: Long, loanOfficerId: Long)

    fun findByMortgageId(mortgageId: Long): MortgageLoan

    fun findByCustomerId(customerId: Long): List<MortgageLoan>

    fun findByLoanOfficerId(loanOfficerId: Long): List<MortgageLoan>

    fun updateStatus(mortgageId: Long): MortgageLoanStatus
}
