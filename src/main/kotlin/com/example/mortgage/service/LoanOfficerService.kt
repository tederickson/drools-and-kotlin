package com.example.mortgage.service

import com.example.mortgage.digest.LoanOfficerDigest
import com.example.mortgage.model.LoanOfficer

interface LoanOfficerService {

    fun addLoanOfficer(loanOfficerDigest: LoanOfficerDigest): LoanOfficerDigest
    fun getLoanOfficerById(id: Long): LoanOfficerDigest?
    fun getLoanOfficers(): MutableList<LoanOfficerDigest>
    fun updateLoanOfficer(loanOfficerDigest: LoanOfficerDigest): Boolean
}
