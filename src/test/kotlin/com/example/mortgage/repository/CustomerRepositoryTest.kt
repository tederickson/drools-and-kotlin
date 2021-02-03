package com.example.mortgage.repository

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    private val repository: CustomerRepository? = null
    private val id = 2L

    @Test
    fun testFindAll() {
        val customers = repository!!.findAll()
        Assert.assertNotNull(customers)
        for (customer in customers) {
            println("customer = $customer")
        }
    }

    @Test
    fun testFindById() {
        val entity = repository!!.findById(id)
        Assert.assertTrue(entity.isPresent)
        val (customerId, firstName, lastName, phone, email) = entity.get()
        Assert.assertEquals(id, customerId)
        Assert.assertEquals("Boba", firstName)
        Assert.assertEquals("Loo", lastName)
        Assert.assertEquals("8015556874", phone)
        Assert.assertEquals("bobaloo@live.com", email)
    }

    @Test
    fun testExistsById() {
        Assert.assertTrue(repository!!.existsById(id))
        Assert.assertFalse(repository.existsById(1234L))
    }

    @Test
    fun testCount() {
        Assert.assertEquals(4, repository!!.count())
    }
}
