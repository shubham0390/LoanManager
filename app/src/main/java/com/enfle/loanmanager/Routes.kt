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

sealed class Routes(val name: String) {
    object Splash : Routes("splash")
    object Home : Routes("home")
    object Profile : Routes("profile")
    object EditProfile : Routes("editProfile")
    object AddClient : Routes("addClient")
    object ViewClientProfile : Routes("viewClientProfile")
    object AddLoan : Routes("addLoan")
}
