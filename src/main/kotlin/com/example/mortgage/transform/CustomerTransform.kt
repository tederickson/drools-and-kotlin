package com.example.mortgage.transform

import com.example.mortgage.digest.CustomerDigest
import com.example.mortgage.model.Customer

class CustomerTransform {
    fun build(customerDigest: CustomerDigest): Customer {
        return Customer(
            customerId = customerDigest.customerId,
            firstName = customerDigest.firstName,
            lastName = customerDigest.lastName,
            phone = customerDigest.phone,
            email = customerDigest.email
        )
    }
}