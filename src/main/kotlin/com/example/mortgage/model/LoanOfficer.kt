package com.example.mortgage.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class LoanOfficer(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var loanOfficerId: Long = -1,
        var firstName: String = "",
        var lastName: String = "",
        var phone: String = "",
        var email: String = "",
        var managerId: Long = -1,
        var activeEnum: String = LoanOfficerActive.INVALID.toString()
)
