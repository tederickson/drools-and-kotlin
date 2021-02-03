package com.example.mortgage.service;

import com.example.mortgage.model.LoanOfficer;
import com.example.mortgage.model.LoanOfficerActive;
import com.example.mortgage.repository.LoanOfficerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoanOfficerServiceImplTest {

    @Autowired
    private LoanOfficerRepository repository;

    @Autowired
    private LoanOfficerService service;

    private final long id = 1L;

    @Before
    public void resetLoanOfficer() {

        final LoanOfficer original =
            new LoanOfficer(id, "Friendly", "Officer", "3035557777", "puppies@gmail.com", 12345, "ACTIVE");
        repository.saveAndFlush(original);
    }

    @Test
    public void testFindById() {

        LoanOfficer loanOfficer = service.getLoanOfficerById(id);

        assertNotNull(loanOfficer);
        assertEquals(id, loanOfficer.getLoanOfficerId());
        assertEquals("Friendly", loanOfficer.getFirstName());
        assertEquals("Officer", loanOfficer.getLastName());
        assertNotNull(loanOfficer.getPhone());
        assertNotNull(loanOfficer.getEmail());
        assertNotNull(loanOfficer.getManagerId());
    }

    @Test
    public void testFindAll() {

        List<LoanOfficer> loanOfficers = service.getLoanOfficers();

        assertEquals(2, loanOfficers.size());

        for (LoanOfficer loanOfficer : loanOfficers) {
            System.out.println("loanOfficer = {loanOfficer}");
        }
    }

    @Test
    public void testUpdateLoanOfficer() {

        final LoanOfficer loanOfficer = new LoanOfficer();
        loanOfficer.setLoanOfficerId(id);
        loanOfficer.setFirstName("Silly");
        loanOfficer.setLastName("Name");
        loanOfficer.setActiveEnum(LoanOfficerActive.VACATION.toString());
        loanOfficer.setEmail("");
        loanOfficer.setPhone("");
        loanOfficer.setManagerId(-1);

        assertTrue(service.updateLoanOfficer(loanOfficer));

        final Optional<LoanOfficer> entity = repository.findById(id);
        assertTrue(entity.isPresent());

        final LoanOfficer actual = entity.get();
        assertEquals(loanOfficer.getFirstName(), actual.getFirstName());
        assertEquals(loanOfficer.getLastName(), actual.getLastName());
    }

    @Test
    public void testUpdateLoanOfficer_invalidId() {

        final long invalidId = 9999;
        final LoanOfficer loanOfficer = new LoanOfficer();
        loanOfficer.setLoanOfficerId(invalidId);

        assertFalse(service.updateLoanOfficer(loanOfficer));

        final Optional<LoanOfficer> entity = repository.findById(invalidId);
        assertFalse(entity.isPresent());
    }

    @Test
    public void testAddLoanOfficer() {

        final LoanOfficer loanOfficer =
            new LoanOfficer(-1, "Apple", "Pie", "8005557777", "nope", 2134, LoanOfficerActive.ACTIVE.toString());

        final long expectedCount = repository.count() + 1;
        final LoanOfficer entity = service.addLoanOfficer(loanOfficer);

        assertEquals(expectedCount, repository.count());

        loanOfficer.setLoanOfficerId(entity.getLoanOfficerId());
        assertEquals(loanOfficer, entity);
    }

}
