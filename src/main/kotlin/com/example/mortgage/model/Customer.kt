package com.example.mortgage.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Customer(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var customerId: Long = -1,
    var firstName: String,
    var lastName: String,
    var phone: String? = null,
    var email: String? = null
)
