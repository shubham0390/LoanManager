package com.enfle.loanmanager

import kotlin.math.pow

object LoanUtils {
    fun calculateCompoundInterest(
        principleAmount: Int,
        loanDurationInYear: Int,
        rateOfInterest: Double,
        noOfTimesInterestIsCalculated: Int
    ): Double {
        return principleAmount * (1 + rateOfInterest / noOfTimesInterestIsCalculated)
            .pow((noOfTimesInterestIsCalculated * loanDurationInYear).toDouble())
    }

    fun calculateSimpleInterest(
        principleAmount: Int,
        loanDurationInYear: Int,
        rateOfInterest: Double
    ): Double {
        return (principleAmount * rateOfInterest * loanDurationInYear) / 100
    }
}