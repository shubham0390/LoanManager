package com.enfle.loanmanager

sealed class Routes(val name: String) {
    object Splash : Routes("splash")
    object Home : Routes("home")
    object Profile : Routes("profile")
    object EditProfile : Routes("editProfile")
    object AddClient : Routes("addClient")
    object ViewClientProfile : Routes("viewClientProfile")
    object AddLoan : Routes("addLoan")
}

