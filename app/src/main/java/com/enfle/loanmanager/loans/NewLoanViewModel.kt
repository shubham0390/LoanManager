package com.enfle.loanmanager.loans

import com.enfle.loanmanager.beans.LoanType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NewLoanViewModel {

    fun onPhoneNumberChange(phoneNumber: String) {
    }

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    private val _loanAmount = MutableStateFlow("")
    val loanAmount: StateFlow<String> = _loanAmount

    private val _loanType = MutableStateFlow(LoanType.EMI)
    val loanType: StateFlow<LoanType> = _loanType
}