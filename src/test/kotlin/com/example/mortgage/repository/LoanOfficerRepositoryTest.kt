package com.example.mortgage.repository

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
class LoanOfficerRepositoryTest {
    @Autowired
    private val repository: LoanOfficerRepository? = null
    private val id = 1L

    @Test
    fun testFindAll() {
        val los = repository!!.findAll()
        Assert.assertNotNull(los)
        for (loanOfficer in los) {
            println("loanOfficer = $loanOfficer")
        }
    }

    @Test
    fun testFindById() {
        val entity = repository!!.findById(id)
        Assert.assertTrue(entity.isPresent)
        val (loanOfficerId, firstName, lastName, phone, email, managerId) = entity.get()
        Assert.assertEquals(id, loanOfficerId)
        Assert.assertEquals("Friendly", firstName)
        Assert.assertEquals("Officer", lastName)
        Assert.assertEquals("3035557777", phone)
        Assert.assertEquals("puppies@gmail.com", email)
        Assert.assertEquals(12345, managerId)
    }

    @Test
    fun testExistsById() {
        Assert.assertTrue(repository!!.existsById(id))
        Assert.assertFalse(repository.existsById(1234L))
    }

    @Test
    fun testCount() {
        Assert.assertEquals(2, repository!!.count())
    }
}
