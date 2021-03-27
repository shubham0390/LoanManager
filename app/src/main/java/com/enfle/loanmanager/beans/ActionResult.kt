package com.enfle.loanmanager.beans

import com.enfle.loanmanager.LocalizedString

data class ActionResult(
    val state: ActionState,
    val message: LocalizedString = LocalizedString.empty()
)

enum class ActionState {
    SUCCESS,
    FAILURE,
    PROCESSING,
    NONE
}