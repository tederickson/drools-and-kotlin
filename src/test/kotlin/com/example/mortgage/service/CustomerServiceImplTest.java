package com.example.mortgage.service;

import com.example.mortgage.model.Customer;
import com.example.mortgage.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceImplTest {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerService service;

    private final long id = 4L;


    @Test
    public void testFindById() {

        Optional<Customer> entity = service.getCustomerById(id);

        assertTrue(entity.isPresent());

        Customer customer = entity.get();

        assertEquals(id, customer.getCustomerId());
        assertEquals("Almost", customer.getFirstName());
        assertEquals("There", customer.getLastName());
        assertNull(customer.getPhone());
        assertNull(customer.getEmail());
    }

    @Test
    public void testFindAll() {

        List<Customer> customers = service.getCustomers();

        assertEquals(4, customers.size());

        for (Customer customer : customers) {
            System.out.println("customer = " + customer);
        }
    }

    @Test
    public void testUpdateCustomer() {

        final Customer customer = new Customer.Builder()
            .withCustomerId(id)
            .withFirstName("Yogi")
            .withLastName("Bear")
            .withPhone("8005550001")
            .withEmail("main.bear@jellystone.gov")
            .build();

        final Customer serviceResult = service.updateCustomer(customer);
        assertEquals(customer, serviceResult);

        final Optional<Customer> entity = repository.findById(id);
        assertTrue(entity.isPresent());
        assertEquals(customer, entity.get());
    }

    @Test
    public void testUpdateCustomer_invalidId() {

        final long invalidId = 9999;
        final Customer customer = new Customer.Builder()
            .withCustomerId(invalidId)
            .withFirstName("Yogi")
            .withLastName("Bear")
            .withPhone("8005550001")
            .withEmail("main.bear@jellystone.gov")
            .build();

        final Customer serviceResult = service.updateCustomer(customer);
        assertNull(serviceResult);

        final Optional<Customer> entity = repository.findById(invalidId);
        assertFalse(entity.isPresent());
    }

    @Test
    public void testAddAndDeleteCustomer() {

        final Customer customer =
            new Customer.Builder().withFirstName("Teen").withLastName("Titans").withPhone("8005557777").build();

        final long expectedCount = repository.count() + 1;
        final Customer entity = service.addCustomer(customer);

        assertEquals(expectedCount, repository.count());

        customer.setCustomerId(entity.getCustomerId());
        assertEquals(customer, entity);

        service.deleteCustomer(entity.getCustomerId());
        assertEquals(expectedCount - 1, repository.count());
        assertFalse(service.getCustomerById(entity.getCustomerId()).isPresent());
    }

}
