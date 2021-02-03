package com.example.mortgage.repository;

import com.example.mortgage.model.LoanOfficer;
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
public class LoanOfficerRepositoryTest {

    @Autowired
    private LoanOfficerRepository repository;

    private final long id = 1L;

    @Test
    public void testFindAll() {

        List<LoanOfficer> los = repository.findAll();

        assertNotNull(los);

        for (LoanOfficer loanOfficer : los) {
            System.out.println("loanOfficer = " + loanOfficer);
        }
    }

    @Test
    public void testFindById() {

        Optional<LoanOfficer> entity = repository.findById(id);

        assertTrue(entity.isPresent());

        LoanOfficer loanOfficer = entity.get();

        assertEquals(id, loanOfficer.getLoanOfficerId());
        assertEquals("Friendly", loanOfficer.getFirstName());
        assertEquals("Officer", loanOfficer.getLastName());
        assertEquals("3035557777", loanOfficer.getPhone());
        assertEquals("puppies@gmail.com", loanOfficer.getEmail());
        assertEquals(12345, loanOfficer.getManagerId());
    }

    @Test
    public void testExistsById() {

        assertTrue(repository.existsById(id));
        assertFalse(repository.existsById(1234L));
    }

    @Test
    public void testCount() {

        assertEquals(2, repository.count());
    }

}
