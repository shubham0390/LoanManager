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
package com.enfle.loanmanager.client.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.enfle.loanmanager.Routes
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun ClientProfileScreen(navController: NavController, viewModel: ClientProfileViewModel = viewModel()) {
    Scaffold(
        backgroundColor = Color.White,
        topBar = { TopAppBar() },
        content = { ClientProfile(viewModel) },
        floatingActionButton = {
            FloatingButton(onClick = { navController.navigate(Routes.AddLoan.name) })
        }
    )
}

@Composable
fun TopAppBar() {
    androidx.compose.material.TopAppBar(
        title = { Text("Client Profile") },
        navigationIcon = {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "",
                modifier = Modifier.clickable(
                    onClick = {
                    }
                )
            )
        }
    )
}

@Composable
private fun ClientProfile(viewModel: ClientProfileViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 24.dp, top = 56.dp)
        ) {
            CoilImage(
                data = viewModel.photoUrl.value,
                contentDescription = "My content description",
                fadeIn = true,
                modifier = Modifier
                    .size(120.dp)
                    .clip(shape = CircleShape),
                loading = {
                    Box(Modifier.fillMaxSize()) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                },
                error = {
                    Image(
                        imageVector = Icons.Filled.Error,
                        modifier = Modifier.size(40.dp),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Color.Black),
                        contentScale = ContentScale.Fit
                    )
                }
            )
        }

        val name: String by viewModel.name.collectAsState("")
        Text(
            text = "Name :$name",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Start,
        )

        val email: String by viewModel.email.collectAsState("")
        Text(
            text = "Email: $email",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Start,
        )

        val phoneNumber: String by viewModel.phoneNumber.collectAsState("")
        Text(
            text = "Phone Number: $phoneNumber",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Start,
        )

        Text(
            text = "Active Loans",
            textAlign = TextAlign.Start,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FloatingButton(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
    }
}

@Preview
@Composable
fun PreviewClientProfileScreen() {
    val context = LocalContext.current
    ClientProfileScreen(
        viewModel = ClientProfileViewModel(),
        navController = NavController(context)
    )
}
