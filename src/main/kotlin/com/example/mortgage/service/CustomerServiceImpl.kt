package com.example.mortgage.service

import com.example.mortgage.model.Customer
import com.example.mortgage.repository.CustomerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerServiceImpl : CustomerService {

    @Autowired
    lateinit var repository: CustomerRepository

    override fun addCustomer(customer: Customer): Customer = repository.save(customer)

    override fun deleteCustomer(id: Long) = repository.deleteById(id)

    override fun getCustomerById(id: Long): Optional<Customer?> = repository.findById(id)

    override fun getCustomers(): MutableList<Customer?> = repository.findAll()

    override fun updateCustomer(customer: Customer): Customer? {
        val entity = repository.findById(customer.customerId)

        if (!entity.isPresent) {
            println("Customer id ${customer.customerId} not in database")
            return null
        }

        return repository.save(customer)
    }
}
