package com.example.mortgage.service

import com.example.mortgage.model.Customer
import com.example.mortgage.repository.CustomerRepository
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class CustomerServiceImplTest {
    @Autowired
    private val repository: CustomerRepository? = null

    @Autowired
    private val service: CustomerService? = null
    private val id = 4L

    @Test
    fun testFindById() {
        val entity = service!!.getCustomerById(id)
        Assert.assertTrue(entity.isPresent)
        val (customerId, firstName, lastName, phone, email) = entity.get()
        Assert.assertEquals(id, customerId)
        Assert.assertEquals("Almost", firstName)
        Assert.assertEquals("There", lastName)
        Assert.assertNull(phone)
        Assert.assertNull(email)
    }

    @Test
    fun testFindAll() {
        val customers: List<Customer?> = service!!.getCustomers()
        Assert.assertEquals(4, customers.size.toLong())
        for (customer in customers) {
            println("customer = $customer")
        }
    }

    @Test
    fun testUpdateCustomer() {
        val customer = Customer.Builder()
                .withCustomerId(id)
                .withFirstName("Yogi")
                .withLastName("Bear")
                .withPhone("8005550001")
                .withEmail("main.bear@jellystone.gov")
                .build()
        val serviceResult = service!!.updateCustomer(customer)
        Assert.assertEquals(customer, serviceResult)
        val entity = repository!!.findById(id)
        Assert.assertTrue(entity.isPresent)
        Assert.assertEquals(customer, entity.get())
    }

    @Test
    fun testUpdateCustomer_invalidId() {
        val invalidId: Long = 9999
        val customer = Customer.Builder()
                .withCustomerId(invalidId)
                .withFirstName("Yogi")
                .withLastName("Bear")
                .withPhone("8005550001")
                .withEmail("main.bear@jellystone.gov")
                .build()
        val serviceResult = service!!.updateCustomer(customer)
        Assert.assertNull(serviceResult)
        val entity = repository!!.findById(invalidId)
        Assert.assertFalse(entity.isPresent)
    }

    @Test
    fun testAddAndDeleteCustomer() {
        val customer = Customer.Builder().withFirstName("Teen").withLastName("Titans").withPhone("8005557777").build()
        val expectedCount = repository!!.count() + 1
        val entity = service!!.addCustomer(customer)
        Assert.assertEquals(expectedCount, repository.count())
        customer.customerId = entity.customerId
        Assert.assertEquals(customer, entity)
        service.deleteCustomer(entity.customerId)
        Assert.assertEquals(expectedCount - 1, repository.count())
        Assert.assertFalse(service.getCustomerById(entity.customerId).isPresent)
    }
}
