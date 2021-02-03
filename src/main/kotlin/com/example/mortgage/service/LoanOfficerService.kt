package com.example.mortgage.service

import com.example.mortgage.model.LoanOfficer

interface LoanOfficerService {

    fun addLoanOfficer(loanOfficer: LoanOfficer): LoanOfficer
    fun getLoanOfficerById(id: Long): LoanOfficer?
    fun getLoanOfficers(): MutableList<LoanOfficer?>
    fun updateLoanOfficer(loanOfficer: LoanOfficer): Boolean
}
