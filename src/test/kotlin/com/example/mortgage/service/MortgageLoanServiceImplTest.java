package com.example.mortgage.service;

import com.example.mortgage.model.Customer;
import com.example.mortgage.model.MortgageLoan;
import com.example.mortgage.model.MortgageLoanStatus;
import com.example.mortgage.repository.CustomerRepository;
import com.example.mortgage.repository.LoanOfficerRepository;
import com.example.mortgage.repository.MortgageLoanRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MortgageLoanServiceImplTest {

    @Autowired
    private MortgageLoanRepository mortgageLoanRepository;

    @Autowired
    private LoanOfficerRepository loanOfficerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MortgageLoanService service;

    @Before
    public void initialize() {

        final List<MortgageLoan> loans = mortgageLoanRepository.findAll();
        for (MortgageLoan loan : loans) {
            if (loan.getMortgageId() > 1) {
                mortgageLoanRepository.delete(loan);
            }
        }
    }

    @Test
    public void addMortgageLoan() {

        final Optional<Customer> customer = customerRepository.findById(3L);
        assertTrue(customer.isPresent());
        final MortgageLoan mortgageLoan = service.addMortgageLoan(customer.get());

        assertNotNull(mortgageLoan);
        validate(MortgageLoanStatus.STARTED, mortgageLoan);
    }

    private void validate(MortgageLoanStatus mortgageLoanStatus, MortgageLoan mortgageLoan) {

        final MortgageLoanStatus actual = MortgageLoanStatus.valueOf(mortgageLoan.getStatusEnum());
        assertEquals(mortgageLoanStatus, actual);
    }

    @Test
    public void testFindByCustomerId() {

        final List<MortgageLoan> loans = service.findByCustomerId(2);
        assertNotNull(loans);
        assertEquals(1, loans.size());
    }

    @Test
    public void testFindByCustomerId_InvalidId() {

        final List<MortgageLoan> loans = service.findByCustomerId(1000);
        assertTrue(loans.isEmpty());
    }

    @Test
    public void testFindByLoanOfficerId() {

        final List<MortgageLoan> loans = service.findByLoanOfficerId(1);
        assertNotNull(loans);
        assertEquals(1, loans.size());
    }

    @Test
    public void testFindByLoanOfficerId_InvalidId() {

        final List<MortgageLoan> loans = service.findByLoanOfficerId(12345);
        assertTrue(loans.isEmpty());
    }

    @Test
    public void testFindByMortgageIdId() {

        final MortgageLoan loan = service.findByMortgageId(1);
        assertNotNull(loan);
        validate(MortgageLoanStatus.APPROVED, loan);
    }

    @Test(expected = org.springframework.dao.DataAccessException.class)
    public void testFindByMortgageId_InvalidId() {

        service.findByMortgageId(11743);
    }

    @Test
    public void testUpdateStatus() {

        final MortgageLoanStatus mortgageLoanStatus = service.updateStatus(1);

        assertEquals(MortgageLoanStatus.APPROVED, mortgageLoanStatus);
    }

    @Test
    public void testAddLoanOfficer() {

        final Optional<Customer> customer = customerRepository.findById(3L);
        assertTrue(customer.isPresent());
        final MortgageLoan mortgageLoan = service.addMortgageLoan(customer.get());
        final long mortgageId = mortgageLoan.getMortgageId();
        service.addLoanOfficer(mortgageId, 1);

        final MortgageLoan entity = mortgageLoanRepository.getOne(mortgageId);

        assertEquals(mortgageId, entity.getMortgageId());
        assertNotNull(entity.getLoanOfficerId());
        assertEquals(1L, entity.getLoanOfficerId().longValue());
    }

    @Test
    public void testGetNewLoanStatus() {

        final Optional<Customer> customer = customerRepository.findById(3L);
        assertTrue(customer.isPresent());
        final MortgageLoan mortgageLoan = service.addMortgageLoan(customer.get());

        assertNotNull(mortgageLoan);

        final MortgageLoanStatus mortgageLoanStatus = service.updateStatus(mortgageLoan.getMortgageId());

        assertEquals(MortgageLoanStatus.LOAN_OFFICER_INCOMPLETE, mortgageLoanStatus);
    }

    @Test
    public void testUpdateStatus_missingPhone() {

        final Customer customer = new Customer.Builder()
            .withFirstName("Yogi")
            .withLastName("Bear")
            .withEmail("main.bear@jellystone.gov")
            .build();

        final Customer entity = customerRepository.save(customer);
        final MortgageLoan mortgageLoan = service.addMortgageLoan(entity, 1);
        final MortgageLoanStatus mortgageLoanStatus = service.updateStatus(mortgageLoan.getMortgageId());

        assertEquals(MortgageLoanStatus.USER_INFO_INCOMPLETE, mortgageLoanStatus);
    }

    @Test
    public void testUpdateStatus_missingEmail() {

        final Customer customer =
            new Customer.Builder().withFirstName("Yogi").withLastName("Bear").withPhone("8005550001").build();

        final Customer entity = customerRepository.save(customer);
        final MortgageLoan mortgageLoan = service.addMortgageLoan(entity, 1);
        final MortgageLoanStatus mortgageLoanStatus = service.updateStatus(mortgageLoan.getMortgageId());

        assertEquals(MortgageLoanStatus.USER_INFO_INCOMPLETE, mortgageLoanStatus);
    }

}
