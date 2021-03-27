package com.enfle.loanmanager

sealed class ViewState<T> {
    data class Error<T>(val error: BaseError) : ViewState<T>()
    data class Loading<T>(val message: LocalizedString) : ViewState<T>()
    data class Data<T>(val data: T) : ViewState<T>()
}