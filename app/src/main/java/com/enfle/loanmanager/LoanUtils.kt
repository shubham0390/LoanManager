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
