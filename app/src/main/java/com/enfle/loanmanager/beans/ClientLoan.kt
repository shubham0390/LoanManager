package com.enfle.loanmanager.beans

data class ClientLoan(
    val loanId: String,
    val userId: String,
    val clientId: String,
    val totalAmountToBePaid: Double,
    val totalInterestToBePaid: Double,
    val paidAmount: Double,
    val loanDuration: Int,
    val emiAmount: Double,

    // Value Provided by UI
    val principleAmount: Double,
    // This value will always be in months
    val interestRate: Float?,
    val interestType: InterestType,
    val startDate: Long,
    val endDate: Long,
    val emiPeriod: EmiPeriod
)

enum class InterestType {
    SIMPLE,
    COMPOUND,
    NONE
}

enum class EmiPeriod {
    DAILY,
    WEEKLY,
    MONTH,
    QUARTERLY,
    HALF_YEARLY,
    YEARLY
}

enum class LoanType {
    TERM,
    EMI
}