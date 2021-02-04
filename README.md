# Overview
Create a Kotlin project that uses Drools.

## Database
Since this is a demo use the H2 in-memory database.

The database schema is found in src/main/resources/schema.sql.

Initial database loading is accomplished by src/main/resources/data.sql.

## Drools 
The business rules are in src/main/resources/MORTGAGE_RULE.drl.

The configuration is in src/main/kotlin/com/example/mortgage/config/DroolsConfig.kt

The business rules are invoked by src/main/kotlin/com/example/mortgage/service/MortgageLoanServiceImpl.kt

## Issues
`mvn clean test` does not run the tests.  Spent hours researching but still have not found a solution.

IntelliJ runs single tests and also runs all tests in src/test/kotlin/com/example/mortgage.

