# Overview
Create a Kotlin project that uses Drools.

The primary goal is invoking business rules within a Kotlin service.

The workflow is a Customer creates a Mortgage Loan and a Loan Officer is assigned to the loan.

## Database
Since this is a demo use the H2 in-memory database.

The database schema is found in src/main/resources/schema.sql.

Initial database loading is accomplished by src/main/resources/data.sql.

Very simple database 
* A MortgageLoan has a Customer and an optional LoanOfficer.
* Only primary keys and indices are used at this time.
* No foreign keys are used for database integrity.

## Drools 
The business rules are in src/main/resources/MORTGAGE_RULE.drl.

The configuration is in src/main/kotlin/com/example/mortgage/config/DroolsConfig.kt

The business rules are invoked by src/main/kotlin/com/example/mortgage/service/MortgageLoanServiceImpl.kt

## Issues
`mvn clean test` does not run the tests.  Spent hours researching but still have not found a solution.

Using "Apache Maven 3.6.3".  Tried many different pom configurations.

IntelliJ runs single tests and also runs all tests in src/test/kotlin/com/example/mortgage.

Going to stick with running Kotlin tests through IntelliJ and researching as time permits.

## Enhancements
* Add Rest Controllers
* Add Documents to the database and expand the business rules to provide a workflow based on which documents have been completed.
* Add Credit report and expand workflow to include approval/decline paths.
