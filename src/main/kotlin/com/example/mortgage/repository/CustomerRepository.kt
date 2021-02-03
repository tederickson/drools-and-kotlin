package com.example.mortgage.repository

import com.example.mortgage.model.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer?, Long?>
