package com.example.mortgage.service

import com.example.mortgage.digest.CustomerDigest
import com.example.mortgage.model.Customer
import com.example.mortgage.repository.CustomerRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class CustomerServiceImplTest {
    @Autowired
    lateinit var repository: CustomerRepository

    @Autowired
    lateinit var service: CustomerService

    private val id = 4L

    @Before
    fun initialize() {
        val customers = repository.findAll()
        for (customer in customers) {
            if (customer != null) {
                if (customer.customerId > 4L) {
                    println("*** Deleting customer = ${customer}")
                    repository.delete(customer)
                }
            }
        }
    }

    @Test
    fun testFindById() {
        val entity = service.getCustomerById(id)
        Assert.assertTrue(entity.isPresent)
        val (customerId, firstName, lastName, phone, email) = entity.get()
        Assert.assertEquals(id, customerId)
        Assert.assertEquals("Almost", firstName)
        Assert.assertEquals("There", lastName)
        Assert.assertEquals("", phone)
        Assert.assertEquals("", email)
    }

    @Test
    fun testFindAll() {
        val customers: List<CustomerDigest> = service.getCustomers()
        Assert.assertEquals(4, customers.size.toLong())
        for (customer in customers) {
            println("customer = $customer")
        }
    }

    @Test
    fun testUpdateCustomer() {
        val customer = CustomerDigest(
                customerId = id,
                firstName = "Yogi",
                lastName = "Bear",
                phone = "8005550001",
                email = "main.bear@jellystone.gov")

        val serviceResult = service.updateCustomer(customer)
        Assert.assertEquals(customer, serviceResult)
        val entity = repository.findById(id)
        Assert.assertTrue(entity.isPresent)
        assertEquals(customer, entity.get())
    }

    private fun assertEquals(customerDigest: CustomerDigest, customer: Customer) {
        Assert.assertEquals(customerDigest.customerId, customer.customerId)
        Assert.assertEquals(customerDigest.firstName, customer.firstName)
        Assert.assertEquals(customerDigest.lastName, customer.lastName)
        Assert.assertEquals(customerDigest.phone, customer.phone)
        Assert.assertEquals(customerDigest.email, customer.email)
    }

    @Test
    fun testUpdateCustomer_invalidId() {
        val invalidId: Long = 9999
        val customer = CustomerDigest(
                customerId = invalidId,
                firstName = "Yogi",
                lastName = "Bear",
                phone = "8005550001",
                email = "main.bear@jellystone.gov")

        val serviceResult = service.updateCustomer(customer)
        Assert.assertNull(serviceResult)
        val entity = repository.findById(invalidId)
        Assert.assertFalse(entity.isPresent)
    }

    @Test
    fun testAddAndDeleteCustomer() {
        val customer = CustomerDigest(firstName = "Teen", lastName = "Titans", phone = "8005557777",
                customerId = 999, email = "")
        val expectedCount = repository.count() + 1
        val customerDigest = service.addCustomer(customer)

        Assert.assertEquals(expectedCount, repository.count())

        customer.customerId = customerDigest.customerId
        Assert.assertEquals(customer, customerDigest)
        service.deleteCustomer(customerDigest.customerId)
        Assert.assertEquals(expectedCount - 1, repository.count())
        Assert.assertFalse(service.getCustomerById(customerDigest.customerId).isPresent)
    }
}
