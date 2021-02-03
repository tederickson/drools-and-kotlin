package com.example.mortgage.model

enum class LoanOfficerActive {
    ACTIVE, VACATION, INVALID
    // The assumption is that if the Loan Officer leaves the company then all of the loans are reassigned.
}
