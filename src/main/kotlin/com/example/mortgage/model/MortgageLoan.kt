package com.example.mortgage.model

import javax.persistence.*

@Entity
data class MortgageLoan(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var mortgageId: Long = -1,
    var customerId: Long = -1,
    var loanOfficerId: Long? = null,

    @Enumerated(EnumType.STRING)
    var statusEnum: MortgageLoanStatus = MortgageLoanStatus.STARTED
)
