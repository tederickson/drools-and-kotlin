# Overview
Create a Kotlin project that uses Drools.

The primary goal is invoking business rules within a Kotlin service.

The workflow is a Customer creates a Mortgage Loan and a Loan Officer is assigned to the loan.

## Design
[Separation of concerns](https://java-design-patterns.com/principles/#separation-of-concerns) is incredibly important.
The design principal insulates the calling program from changes in either the database or the legacy backend (aka source of truth).
A prime example is the backend gets a new requirement to encrypt the PII (Personal Identifiable Information).  
Suddenly the SSN, Email and address is encrypted.
The separation of concerns prevents a massive overhaul of the front end code.
- com/example/mortgage/config/ - Spring configuration
- com/example/mortgage/digest/ - the data returned to the calling program
- com/example/mortgage/model/ - the data sent to the database
- com/example/mortgage/repository/ - the methods that can be called on the database.  Spring repositories enable a large number of built in queries.
- com/example/mortgage/service/ - the business logic
- com/example/mortgage/transform/ - factories that create either a model or a digest

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

```
rule "Customer missing phone"
  when
    $c : Customer(phone == null || phone.trim().isEmpty())
  then
    insert(MortgageLoanStatus.USER_INFO_INCOMPLETE);
end
```
The *when* portion checks to see if:
1. A Customer fact exists
1. The Customer.phone is null or an empty String

The *then* is Java code that inserts a new fact MortgageLoanStatus.USER_INFO_INCOMPLETE.

Inserting a new fact causes additional business rules to fire.

## Testing
`mvn clean test` runs all unit tests.

## Enhancements
* Add Rest Controllers
* Add Documents to the database and expand the business rules to provide a workflow based on which documents have been completed.
* Add Credit report and expand workflow to include approval/decline paths.
