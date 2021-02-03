package com.example.mortgage.repository

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
class MortgageLoanRepositoryTest {
    @Autowired
    private val repository: MortgageLoanRepository? = null
    private val id = 1L

    @Test
    fun testFindAll() {
        val loans = repository!!.findAll()
        Assert.assertNotNull(loans)
        for (loan in loans) {
            println("loan = $loan")
        }
    }

    @Test
    fun testFindById() {
        val entity = repository!!.findById(id)
        Assert.assertTrue(entity.isPresent)
        val (mortgageId, customerId) = entity.get()
        Assert.assertEquals(id, mortgageId)
        Assert.assertEquals(2, customerId)
        // assertEquals(1, loan.getLoanOfficerId());
    }

    @Test
    fun testFindByCustomerId() {
        val loans = repository!!.findByCustomerId(2)
        Assert.assertNotNull(loans)
        Assert.assertEquals(1, loans.size.toLong())
        val (mortgageId, customerId) = loans[0]
        Assert.assertEquals(1, mortgageId)
        Assert.assertEquals(2, customerId)
        // assertEquals(1, loan.getLoanOfficerId());
    }

    @Test
    fun testFindByCustomerId_InvalidId() {
        val loans = repository!!.findByCustomerId(297341)
        Assert.assertNotNull(loans)
        Assert.assertTrue(loans.isEmpty())
    }

    @Test
    fun testExistsById() {
        Assert.assertTrue(repository!!.existsById(id))
        Assert.assertFalse(repository.existsById(1234L))
    }

    @Test
    fun testCount() {
        Assert.assertEquals(1, repository!!.count())
    }
}
