package com.example.mortgage.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Customer(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var customerId: Long = -1,
        var firstName: String,
        var lastName: String,
        var phone: String? = null,
        var email: String? = null
) {

    class Builder {
        private var customerId: Long = -1
        private var firstName: String = ""
        private var lastName: String = ""
        private var phone: String? = null
        private var email: String? = null

        fun withCustomerId(customerId: Long): Builder {
            this.customerId = customerId
            return this
        }

        fun withFirstName(firstName: String): Builder {
            this.firstName = firstName
            return this
        }

        fun withLastName(lastName: String): Builder {
            this.lastName = lastName
            return this
        }

        fun withPhone(phone: String?): Builder {
            this.phone = phone
            return this
        }

        fun withEmail(email: String?): Builder {
            this.email = email
            return this
        }

        fun build(): Customer {
            // Named parameters save a lot of refactoring time
            return Customer(customerId = customerId,
                    email = email,
                    phone = phone,
                    firstName = firstName,
                    lastName = lastName)
        }
    }
}
