package com.example.mortgage.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class MortgageLoan(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var mortgageId: Long = -1,
        var customerId: Long = -1,
        var loanOfficerId: Long? = null,
        var statusEnum: String = MortgageLoanStatus.STARTED.toString()
)
