package com.example.mortgage.service

import com.example.mortgage.digest.CustomerDigest
import com.example.mortgage.model.Customer
import com.example.mortgage.repository.CustomerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class CustomerServiceImpl : CustomerService {

    @Autowired
    lateinit var repository: CustomerRepository

    override fun addCustomer(customerDigest: CustomerDigest): CustomerDigest {
        val customer = customerTransform(customerDigest)
        customer.customerId = -1

        return customerDigestTransform(repository.save(customer))
    }

    override fun deleteCustomer(id: Long) = repository.deleteById(id)

    override fun getCustomerById(id: Long): Optional<CustomerDigest> {
        val entity = repository.findById(id)

        if (entity.isPresent) {
            return Optional.of(customerDigestTransform(entity.get()))
        }
        return Optional.empty()
    }

    override fun getCustomers(): MutableList<CustomerDigest> {
        return repository.findAll()
                .stream()
                .filter(Objects::nonNull)
                .map { item -> customerDigestTransform(item!!) }
                .collect(Collectors.toList())
    }

    override fun updateCustomer(customerDigest: CustomerDigest): CustomerDigest? {
        val entity = repository.findById(customerDigest.customerId)

        if (!entity.isPresent) {
            println("Customer id ${customerDigest.customerId} not in database")
            return null
        }

        val customer = customerTransform(customerDigest)
        return customerDigestTransform(repository.save(customer))
    }


    fun customerDigestTransform(customer: Customer): CustomerDigest {
        val customerId: Long = customer.customerId
        val firstName: String = customer.firstName
        val lastName: String = customer.lastName
        val phone: String = customer.phone ?: ""
        val email: String = customer.email ?: ""

        return CustomerDigest(customerId = customerId,
                firstName = firstName,
                lastName = lastName,
                phone = phone,
                email = email)
    }

    fun customerTransform(customerDigest: CustomerDigest): Customer {
        return Customer(customerId = customerDigest.customerId,
                firstName = customerDigest.firstName,
                lastName = customerDigest.lastName,
                phone = customerDigest.phone,
                email = customerDigest.email)
    }
}
