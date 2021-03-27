package com.enfle.loanmanager.user

import android.text.Spannable

data class TextFieldInput(
    val value: Spannable,
    val hasError: Boolean,
    val errorMessage: Spannable
)