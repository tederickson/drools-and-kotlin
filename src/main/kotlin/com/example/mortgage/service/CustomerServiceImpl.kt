package com.example.mortgage.service

import com.example.mortgage.digest.CustomerDigest
import com.example.mortgage.repository.CustomerRepository
import com.example.mortgage.transform.CustomerDigestTransform
import com.example.mortgage.transform.CustomerTransform
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class CustomerServiceImpl : CustomerService {

    @Autowired
    lateinit var repository: CustomerRepository

    val customerDigestTransform = CustomerDigestTransform()
    val customerTransform = CustomerTransform()

    override fun addCustomer(customerDigest: CustomerDigest): CustomerDigest {
        val customer = customerTransform.build(customerDigest)
        customer.customerId = -1

        return customerDigestTransform.build(repository.save(customer))
    }

    override fun deleteCustomer(id: Long) = repository.deleteById(id)

    override fun getCustomerById(id: Long): Optional<CustomerDigest> {
        val entity = repository.findById(id)

        if (entity.isPresent) {
            return Optional.of(customerDigestTransform.build(entity.get()))
        }
        return Optional.empty()
    }

    override fun getCustomers(): MutableList<CustomerDigest> {
        return repository.findAll()
            .stream()
            .filter(Objects::nonNull)
            .map { item -> customerDigestTransform.build(item!!) }
            .collect(Collectors.toList())
    }

    override fun updateCustomer(customerDigest: CustomerDigest): CustomerDigest? {
        val entity = repository.findById(customerDigest.customerId)

        if (!entity.isPresent) {
            println("Customer id ${customerDigest.customerId} not in database")
            return null
        }

        val customer = customerTransform.build(customerDigest)
        return customerDigestTransform.build(repository.save(customer))
    }
}
