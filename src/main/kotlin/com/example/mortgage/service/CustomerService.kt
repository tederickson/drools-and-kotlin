package com.example.mortgage.service

import com.example.mortgage.digest.CustomerDigest
import java.util.*

interface CustomerService {

    fun addCustomer(customer: CustomerDigest): CustomerDigest
    fun deleteCustomer(id: Long)
    fun getCustomerById(id: Long): Optional<CustomerDigest>
    fun getCustomers(): MutableList<CustomerDigest>
    fun updateCustomer(customerDigest: CustomerDigest): CustomerDigest?
}
