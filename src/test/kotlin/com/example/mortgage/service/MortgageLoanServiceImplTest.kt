package com.example.mortgage.service

import com.example.mortgage.digest.CustomerDigest
import com.example.mortgage.digest.MortgageLoanStatusEnum
import com.example.mortgage.model.Customer
import com.example.mortgage.model.MortgageLoan
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

        //  INSERT INTO Mortgage_Loan(customer_id, loan_officer_id, status_enum) VALUES (2, 1, 'APPROVED');
        val loan = MortgageLoan(
            customerId = 2,
            loanOfficerId = 1,
            statusEnum = MortgageLoanStatus.APPROVED,
            mortgageId = 1
        )
        mortgageLoanRepository.saveAndFlush(loan)
    }

    @Test
    fun addMortgageLoan() {
        val customer = customerRepository.findById(3L)
        Assert.assertTrue(customer.isPresent)

        val digest = customerDigestTransform(customer.get())
        val mortgageLoan = service.addMortgageLoan(digest)
        Assert.assertNotNull(mortgageLoan)
        Assert.assertEquals(MortgageLoanStatusEnum.STARTED, mortgageLoan.statusEnum)
    }

    fun customerDigestTransform(customer: Customer): CustomerDigest {
        val customerId: Long = customer.customerId
        val firstName: String = customer.firstName
        val lastName: String = customer.lastName
        val phone: String = customer.phone ?: ""
        val email: String = customer.email ?: ""

        return CustomerDigest(
            customerId = customerId,
            firstName = firstName,
            lastName = lastName,
            phone = phone,
            email = email
        )
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
        Assert.assertEquals(MortgageLoanStatusEnum.APPROVED, loan.statusEnum)
    }

    @Test(expected = DataAccessException::class)
    fun testFindByMortgageId_InvalidId() {
        service.findByMortgageId(11743)
    }

    @Test
    fun testUpdateStatus() {
        val mortgageLoanStatus = service.updateStatus(1)
        Assert.assertEquals(MortgageLoanStatusEnum.APPROVED, mortgageLoanStatus)
    }

    @Test
    fun testAddLoanOfficer() {
        val assignedLoanOfficer = 1L
        val customerId = 3L
        val customerOptional = customerRepository.findById(customerId)
        Assert.assertTrue(customerOptional.isPresent)
        val customer = customerOptional.get()
        val (mortgageId) = service.addMortgageLoan(customerDigestTransform(customer))
        service.addLoanOfficer(mortgageId, assignedLoanOfficer)

        // Verify loan is persisted
        verifyPersistedLoan(mortgageId, customer.customerId, assignedLoanOfficer, MortgageLoanStatus.STARTED)
    }

    private fun verifyPersistedLoan(
        mortgageId: Long,
        customerId: Long,
        loanOfficerId: Long,
        status: MortgageLoanStatus
    ) {
        val loan = mortgageLoanRepository.getOne(mortgageId)

        Assert.assertEquals(mortgageId, loan.mortgageId)
        Assert.assertEquals(customerId, loan.customerId)
        Assert.assertEquals(loanOfficerId, loan.loanOfficerId)
        Assert.assertEquals(status, loan.statusEnum)
    }

    private fun verifyPersistedLoan(mortgageId: Long, customerId: Long, status: MortgageLoanStatus) {
        val loan = mortgageLoanRepository.getOne(mortgageId)

        Assert.assertEquals(mortgageId, loan.mortgageId)
        Assert.assertEquals(customerId, loan.customerId)
        Assert.assertNull(loan.loanOfficerId)
        Assert.assertEquals(status, loan.statusEnum)
    }

    @Test
    fun testGetNewLoanStatus() {
        val customerId = 3L
        val customer = customerRepository.findById(customerId)
        Assert.assertTrue(customer.isPresent)
        val mortgageLoan = service.addMortgageLoan(customerDigestTransform(customer.get()))
        Assert.assertNotNull(mortgageLoan)
        val mortgageLoanStatus = service.updateStatus(mortgageLoan.mortgageId)
        Assert.assertEquals(MortgageLoanStatusEnum.LOAN_OFFICER_INCOMPLETE, mortgageLoanStatus)

        verifyPersistedLoan(mortgageLoan.mortgageId, customerId, MortgageLoanStatus.LOAN_OFFICER_INCOMPLETE)
    }

    @Test
    fun testUpdateStatus_missingPhone() {
        val customer = Customer(
            firstName = "Yogi",
            lastName = "Bear missing phone",
            email = "main.bear@jellystone.gov"
        )

        val entity = customerRepository.save(customer)
        val (mortgageId) = service.addMortgageLoan(customerDigestTransform(entity), 1)
        val mortgageLoanStatus = service.updateStatus(mortgageId)
        Assert.assertEquals(MortgageLoanStatusEnum.USER_INFO_INCOMPLETE, mortgageLoanStatus)
    }

    @Test
    fun testUpdateStatus_missingEmail() {
        val customer = Customer(firstName = "Yogi", lastName = "Bear missing email", phone = "8005550001")
        val entity = customerRepository.save(customer)
        val (mortgageId) = service.addMortgageLoan(customerDigestTransform(entity), 1)
        val mortgageLoanStatus = service.updateStatus(mortgageId)
        Assert.assertEquals(MortgageLoanStatusEnum.USER_INFO_INCOMPLETE, mortgageLoanStatus)
    }
}
