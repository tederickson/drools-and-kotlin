package com.example.mortgage.transform

import com.example.mortgage.digest.CustomerDigest
import com.example.mortgage.model.Customer

class CustomerDigestTransform {
    fun build(customer: Customer): CustomerDigest {
        val customerId: Long = customer.customerId
        val firstName: String = customer.firstName
        val lastName: String = customer.lastName
        val phone: String = customer.phone ?: ""
        val email: String = customer.email ?: ""

        return CustomerDigest(
            customerId = customerId,
            firstName = firstName,
            lastName = lastName,
            phone = phone,
            email = email
        )
    }
}