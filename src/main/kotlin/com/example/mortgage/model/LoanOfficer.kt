package com.example.mortgage.model

import javax.persistence.*

@Entity
data class LoanOfficer(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var loanOfficerId: Long = -1,
        var firstName: String = "",
        var lastName: String = "",
        var phone: String = "",
        var email: String = "",
        var managerId: Long = -1,

        // To map the Enum to a String database column type, you need to specify the EnumType.STRING
        @Enumerated(EnumType.STRING)
        var activeEnum: LoanOfficerActive = LoanOfficerActive.INVALID
)
