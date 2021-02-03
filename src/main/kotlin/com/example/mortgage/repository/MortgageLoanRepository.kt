package com.example.mortgage.repository

import com.example.mortgage.model.MortgageLoan
import org.springframework.data.jpa.repository.JpaRepository

interface MortgageLoanRepository : JpaRepository<MortgageLoan?, Long?> {
    fun findByCustomerId(customerId: Long): List<MortgageLoan>

    fun findByLoanOfficerId(loanOfficerId: Long): List<MortgageLoan>
}
