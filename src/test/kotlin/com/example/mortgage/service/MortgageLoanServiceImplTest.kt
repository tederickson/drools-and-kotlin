package com.example.mortgage.service

import com.example.mortgage.model.Customer
import com.example.mortgage.model.MortgageLoanStatus
import com.example.mortgage.repository.CustomerRepository
import com.example.mortgage.repository.MortgageLoanRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DataAccessException
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class MortgageLoanServiceImplTest {
    @Autowired
    lateinit var mortgageLoanRepository: MortgageLoanRepository

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Autowired
    lateinit var service: MortgageLoanService

    @Before
    fun initialize() {
        val loans = mortgageLoanRepository.findAll()
        for (loan in loans) {
            if (loan!!.mortgageId > 1) {
                mortgageLoanRepository.delete(loan)
            }
        }
    }

    @Test
    fun addMortgageLoan() {
        val customer = customerRepository.findById(3L)
        Assert.assertTrue(customer.isPresent)
        val mortgageLoan = service.addMortgageLoan(customer.get())
        Assert.assertNotNull(mortgageLoan)
        Assert.assertEquals(MortgageLoanStatus.STARTED, mortgageLoan.statusEnum)
    }

    @Test
    fun testFindByCustomerId() {
        val loans = service.findByCustomerId(2)
        Assert.assertNotNull(loans)
        Assert.assertEquals(1, loans.size.toLong())
    }

    @Test
    fun testFindByCustomerId_InvalidId() {
        val loans = service.findByCustomerId(1000)
        Assert.assertTrue(loans.isEmpty())
    }

    @Test
    fun testFindByLoanOfficerId() {
        val loans = service.findByLoanOfficerId(1)
        Assert.assertNotNull(loans)
        Assert.assertEquals(1, loans.size.toLong())
    }

    @Test
    fun testFindByLoanOfficerId_InvalidId() {
        val loans = service.findByLoanOfficerId(12345)
        Assert.assertTrue(loans.isEmpty())
    }

    @Test
    fun testFindByMortgageIdId() {
        val loan = service.findByMortgageId(1)
        Assert.assertNotNull(loan)
        Assert.assertEquals(MortgageLoanStatus.APPROVED, loan.statusEnum)
    }

    @Test(expected = DataAccessException::class)
    fun testFindByMortgageId_InvalidId() {
        service.findByMortgageId(11743)
    }

    @Test
    fun testUpdateStatus() {
        val mortgageLoanStatus = service.updateStatus(1)
        Assert.assertEquals(MortgageLoanStatus.APPROVED, mortgageLoanStatus)
    }

    @Test
    fun testAddLoanOfficer() {
        val customerOptional = customerRepository.findById(3L)
        Assert.assertTrue(customerOptional.isPresent)
        val customer = customerOptional.get()
        val (mortgageId) = service.addMortgageLoan(customer)
        service.addLoanOfficer(mortgageId, 1L)
        val (mortgageId1, customerId, loanOfficerId) = mortgageLoanRepository.getOne(mortgageId)
        Assert.assertEquals(mortgageId, mortgageId1)
        Assert.assertEquals(customerId, customer.customerId)
        Assert.assertNotNull(loanOfficerId)
        Assert.assertEquals(1L, loanOfficerId!!.toLong())
    }

    @Test
    fun testGetNewLoanStatus() {
        val customer = customerRepository.findById(3L)
        Assert.assertTrue(customer.isPresent)
        val mortgageLoan = service.addMortgageLoan(customer.get())
        Assert.assertNotNull(mortgageLoan)
        val mortgageLoanStatus = service.updateStatus(mortgageLoan.mortgageId)
        Assert.assertEquals(MortgageLoanStatus.LOAN_OFFICER_INCOMPLETE, mortgageLoanStatus)
    }

    @Test
    fun testUpdateStatus_missingPhone() {
        val customer = Customer(
                firstName = "Yogi",
                lastName = "Bear missing phone",
                email = "main.bear@jellystone.gov")

        val entity = customerRepository.save(customer)
        val (mortgageId) = service.addMortgageLoan(entity, 1)
        val mortgageLoanStatus = service.updateStatus(mortgageId)
        Assert.assertEquals(MortgageLoanStatus.USER_INFO_INCOMPLETE, mortgageLoanStatus)
    }

    @Test
    fun testUpdateStatus_missingEmail() {
        val customer = Customer(firstName = "Yogi", lastName = "Bear missing email", phone = "8005550001")
        val entity = customerRepository.save(customer)
        val (mortgageId) = service.addMortgageLoan(entity, 1)
        val mortgageLoanStatus = service.updateStatus(mortgageId)
        Assert.assertEquals(MortgageLoanStatus.USER_INFO_INCOMPLETE, mortgageLoanStatus)
    }
}
