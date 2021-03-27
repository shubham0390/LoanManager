package com.enfle.loanmanager

data class BaseError(
    val message: LocalizedString,
    val errorCode: ErrorCode
)