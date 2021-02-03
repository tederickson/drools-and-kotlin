package com.example.mortgage.repository

import com.example.mortgage.model.LoanOfficer
import org.springframework.data.jpa.repository.JpaRepository

interface LoanOfficerRepository : JpaRepository<LoanOfficer?, Long?>
