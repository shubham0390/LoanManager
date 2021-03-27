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

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

sealed class LocalizedString {

    object EmptyString : LocalizedString()

    data class RawString(val value: String) : LocalizedString()

    data class ResourceString(
        @StringRes val resourceId: Int,
        val args: List<Any>
    ) : LocalizedString()

    data class QuantityResourceString(
        @PluralsRes val resourceId: Int,
        val quantity: Int,
        val args: List<Any>
    ) : LocalizedString()

    data class CompoundString(val parts: List<LocalizedString>) : LocalizedString() {

        override operator fun plus(other: LocalizedString) = CompoundString(this.parts + other)

        override operator fun plus(other: CompoundString) = CompoundString(this.parts + other.parts)
    }

    open operator fun plus(other: LocalizedString) = CompoundString(listOf(this, other))

    open operator fun plus(other: CompoundString) = CompoundString(listOf(this) + other.parts)

    companion object {

        fun empty() = EmptyString

        fun raw(value: String): LocalizedString = when (value) {
            "" -> EmptyString
            else -> RawString(value)
        }

        fun resource(@StringRes resourceId: Int, vararg args: Any) =
            ResourceString(resourceId, args.toList())

        fun quantity(@PluralsRes resourceId: Int, quantity: Int, vararg args: Any) =
            QuantityResourceString(resourceId, quantity, args.toList())
    }
}
