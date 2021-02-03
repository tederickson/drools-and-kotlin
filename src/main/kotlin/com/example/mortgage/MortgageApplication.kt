package com.example.mortgage

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MortgageApplication

fun main(args: Array<String>) {
	runApplication<MortgageApplication>(*args)
}
