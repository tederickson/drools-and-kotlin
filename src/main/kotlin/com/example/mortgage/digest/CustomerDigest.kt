package com.example.mortgage.digest

data class CustomerDigest(
    var customerId: Long,
    var firstName: String,
    var lastName: String,
    var phone: String,
    var email: String
)
