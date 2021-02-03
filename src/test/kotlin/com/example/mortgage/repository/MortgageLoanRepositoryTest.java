package com.example.mortgage.repository;

import com.example.mortgage.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MortgageLoanRepositoryTest {

    @Autowired
    private MortgageLoanRepository repository;

    private final long id = 1L;

    @Test
    public void testFindAll() {

        List<MortgageLoan> loans = repository.findAll();

        assertNotNull(loans);

        for (MortgageLoan loan : loans) {
            System.out.println("loan = " + loan);
        }
    }

    @Test
    public void testFindById() {

        Optional<MortgageLoan> entity = repository.findById(id);

        assertTrue(entity.isPresent());

        MortgageLoan loan = entity.get();

        assertEquals(id, loan.getMortgageId());
        assertEquals(2, loan.getCustomerId());
       // assertEquals(1, loan.getLoanOfficerId());
    }

    @Test
    public void testFindByCustomerId() {

        List<MortgageLoan> loans = repository.findByCustomerId(2);

        assertNotNull(loans);
        assertEquals(1, loans.size());
        MortgageLoan loan = loans.get(0);

        assertEquals(1, loan.getMortgageId());
        assertEquals(2, loan.getCustomerId());
       // assertEquals(1, loan.getLoanOfficerId());
    }

    @Test
    public void testFindByCustomerId_InvalidId() {

        List<MortgageLoan> loans = repository.findByCustomerId(297341);

        assertNotNull(loans);
        assertTrue(loans.isEmpty());
    }

    @Test
    public void testExistsById() {

        assertTrue(repository.existsById(id));
        assertFalse(repository.existsById(1234L));
    }

    @Test
    public void testCount() {

        assertEquals(1, repository.count());
    }

}
