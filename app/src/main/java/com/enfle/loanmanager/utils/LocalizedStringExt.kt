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
package com.enfle.loanmanager.utils

import android.content.Context
import android.widget.TextView

fun LocalizedString.get(context: Context): String = when (this) {
    LocalizedString.EmptyString -> ""
    is LocalizedString.RawString -> value
    is LocalizedString.ResourceString -> {
        context.getString(resourceId, *getArgValues(context, args))
    }
    is LocalizedString.QuantityResourceString -> {
        context.resources.getQuantityString(resourceId, quantity, *getArgValues(context, args))
    }
    is LocalizedString.CompoundString -> {
        parts.joinToString(separator = "") { it.get(context) }
    }
}

var TextView.localizedText: LocalizedString
    get() = LocalizedString.raw(text.toString())
    set(value) {
        text = value.get(this.context)
    }

private fun getArgValues(context: Context, args: List<Any>): Array<Any> {
    return args.map {
        if (it is LocalizedString) {
            it.get(context)
        } else {
            it
        }
    }.toTypedArray()
}
