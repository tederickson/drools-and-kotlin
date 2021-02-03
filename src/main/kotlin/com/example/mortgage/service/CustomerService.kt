package com.example.mortgage.service

import com.example.mortgage.model.Customer
import java.util.Optional

interface CustomerService {

    fun addCustomer(customer: Customer): Customer
    fun deleteCustomer(id: Long)
    fun getCustomerById(id: Long): Optional<Customer?>
    fun getCustomers(): MutableList<Customer?>
    fun updateCustomer(customer: Customer): Customer?
}
