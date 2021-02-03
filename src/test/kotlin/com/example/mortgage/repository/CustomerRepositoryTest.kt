package com.example.mortgage.repository;

import com.example.mortgage.model.Customer;
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
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    private final long id = 2L;

    @Test
    public void testFindAll() {

        List<Customer> customers = repository.findAll();

        assertNotNull(customers);

        for (Customer customer : customers) {
            System.out.println("customer = " + customer);
        }
    }

    @Test
    public void testFindById() {

        Optional<Customer> entity = repository.findById(id);

        assertTrue(entity.isPresent());

        Customer customer = entity.get();

        assertEquals(id, customer.getCustomerId());
        assertEquals("Boba", customer.getFirstName());
        assertEquals("Loo", customer.getLastName());
        assertEquals("8015556874", customer.getPhone());
        assertEquals("bobaloo@live.com", customer.getEmail());
    }

    @Test
    public void testExistsById() {

        assertTrue(repository.existsById(id));
        assertFalse(repository.existsById(1234L));
    }

    @Test
    public void testCount() {

        assertEquals(4, repository.count());
    }

}
