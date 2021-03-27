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

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.enfle.loanmanager.client.add.AddClientScreen
import com.enfle.loanmanager.client.view.ClientProfileScreen
import com.enfle.loanmanager.home.HomeScreen
import com.enfle.loanmanager.loans.NewLoanScreen
import com.enfle.loanmanager.splash.SplashScreen
import com.enfle.loanmanager.user.EditProfileScreen
import com.enfle.loanmanager.user.UserProfileScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun ComposeNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Splash.name
    ) {
        composable(Routes.Splash.name) {
            SplashScreen(navController = navController)
        }
        composable(Routes.Home.name) {
            HomeScreen(
                addNewClient = { navController.navigate(Routes.AddClient.name) },
                onClientClicked = { navController.navigate(Routes.ViewClientProfile.name) }
            )
        }
        composable(Routes.Profile.name) {
            UserProfileScreen()
        }
        composable(Routes.EditProfile.name) {
            EditProfileScreen(
                onProfileSaved = {
                    navController.navigate(Routes.Home.name)
                }
            )
        }
        composable(Routes.AddClient.name) {
            AddClientScreen(navController)
        }
        composable(Routes.ViewClientProfile.name) {
            ClientProfileScreen(navController)
        }
        composable(Routes.AddLoan.name) {
            NewLoanScreen()
        }
    }
}
