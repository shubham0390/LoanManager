/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
