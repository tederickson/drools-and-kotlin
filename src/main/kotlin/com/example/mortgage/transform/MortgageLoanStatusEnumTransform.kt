package com.example.mortgage.transform

import com.example.mortgage.digest.MortgageLoanStatusEnum
import com.example.mortgage.model.MortgageLoanStatus

class MortgageLoanStatusEnumTransform {
    fun build(statusEnum: MortgageLoanStatus): MortgageLoanStatusEnum {
        return MortgageLoanStatusEnum.valueOf(statusEnum.toString())
    }
}