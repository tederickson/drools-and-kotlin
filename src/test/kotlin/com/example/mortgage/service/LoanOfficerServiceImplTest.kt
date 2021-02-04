package com.example.mortgage.service

import com.example.mortgage.model.LoanOfficer
import com.example.mortgage.model.LoanOfficerActive
import com.example.mortgage.repository.LoanOfficerRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class LoanOfficerServiceImplTest {
    @Autowired
    lateinit var repository: LoanOfficerRepository

    @Autowired
    lateinit var service: LoanOfficerService

    private val id = 1L

    @Before
    fun resetLoanOfficer() {
        val original = LoanOfficer(id, "Friendly", "Officer", "3035557777", "puppies@gmail.com", 12345, LoanOfficerActive.ACTIVE)
        repository.saveAndFlush(original)
    }

    @Test
    fun testFindById() {
        val loanOfficer = service.getLoanOfficerById(id)
        Assert.assertNotNull(loanOfficer)
        Assert.assertEquals(id, loanOfficer!!.loanOfficerId)
        Assert.assertEquals("Friendly", loanOfficer.firstName)
        Assert.assertEquals("Officer", loanOfficer.lastName)
        Assert.assertNotNull(loanOfficer.phone)
        Assert.assertNotNull(loanOfficer.email)
        Assert.assertNotNull(loanOfficer.managerId)
    }

    @Test
    fun testFindAll() {
        val loanOfficers: List<LoanOfficer?> = service.getLoanOfficers()
        Assert.assertEquals(2, loanOfficers.size.toLong())
        for (loanOfficer in loanOfficers) {
            println("loanOfficer = {loanOfficer}")
        }
    }

    @Test
    fun testUpdateLoanOfficer() {
        val loanOfficer = LoanOfficer()
        loanOfficer.loanOfficerId = id
        loanOfficer.firstName = "Silly"
        loanOfficer.lastName = "Name"
        loanOfficer.activeEnum = LoanOfficerActive.VACATION
        loanOfficer.email = ""
        loanOfficer.phone = ""
        loanOfficer.managerId = -1
        Assert.assertTrue(service.updateLoanOfficer(loanOfficer))
        val entity = repository.findById(id)
        Assert.assertTrue(entity.isPresent)
        val (_, firstName, lastName) = entity.get()
        Assert.assertEquals(loanOfficer.firstName, firstName)
        Assert.assertEquals(loanOfficer.lastName, lastName)
    }

    @Test
    fun testUpdateLoanOfficer_invalidId() {
        val invalidId: Long = 9999
        val loanOfficer = LoanOfficer()
        loanOfficer.loanOfficerId = invalidId
        Assert.assertFalse(service.updateLoanOfficer(loanOfficer))
        val entity = repository.findById(invalidId)
        Assert.assertFalse(entity.isPresent)
    }

    @Test
    fun testAddLoanOfficer() {
        val loanOfficer = LoanOfficer(-1, "Apple", "Pie", "8005557777", "nope", 2134, LoanOfficerActive.ACTIVE)
        val expectedCount = repository.count() + 1
        val entity = service.addLoanOfficer(loanOfficer)
        Assert.assertEquals(expectedCount, repository.count())
        loanOfficer.loanOfficerId = entity.loanOfficerId
        Assert.assertEquals(loanOfficer, entity)
    }
}
