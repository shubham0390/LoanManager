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
package com.enfle.loanmanager.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.enfle.loanmanager.ViewState
import com.enfle.loanmanager.beans.LoanClient
import com.enfle.loanmanager.common.Error
import com.enfle.loanmanager.common.Loading

@Composable
fun HomeScreen(
    addNewClient: () -> Unit = {},
    onClientClicked: (LoanClient) -> Unit = {},
    viewModel: HomeViewModel = viewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val clients = viewModel.clientData.collectAsState()
    LaunchedEffect(key1 = "") {
        viewModel.syncClientData()
    }

    when (val viewState = clients.value) {
        is ViewState.Error -> {
            Error()
        }
        is ViewState.Loading -> {
            Loading(modifier = Modifier.fillMaxSize())
        }
        is ViewState.Data ->
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = { TopAppBar() },
                floatingActionButton = { FloatingButton(addNewClient) },
                content = {
                    BodyContent(
                        viewState.data,
                        onClientClicked = onClientClicked,
                    )
                },
            )
    }
}

@Composable
fun TopAppBar() {
    androidx.compose.material.TopAppBar(title = { Text("Loan Manager") })
}

@Composable
private fun BodyContent(clients: List<LoanClient>, onClientClicked: (LoanClient) -> Unit) {
    LazyColumn {
        items(clients) { client ->
            ClientRow(client = client, onClick = { onClientClicked(client) })
        }
    }
}

@Composable
private fun ClientRow(client: LoanClient, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(
                modifier = Modifier
                    .height(10.dp)
                    .background(Color.LightGray)
                    .fillMaxWidth()
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                Column {
                    Text(
                        text = client.name,
                        style = MaterialTheme.typography.subtitle2,
                    )
                    Text(
                        text = "3 Active loans",
                        style = MaterialTheme.typography.caption.copy(fontSize = 10.sp),
                    )
                }

                Image(
                    imageVector = Icons.Default.VerifiedUser, contentDescription = "Client Profile Image",
                )
            }
            Spacer(Modifier.size(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                CardItem("Loan amount", "Rs. 30000")
                CardItem("Last Payment", "Rs. 300")
                CardItem("Payment Date", "23 Feb 2021")
            }
        }
    }
}

@Composable
private fun CardItem(title: String, subtitle: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.caption,
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.body2,
            )
        }
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
fun PreviewBodyContent() {
    Surface {
        BodyContent(
            clients = listOf(
                LoanClient("Subham Tyagi", "subham.k.tyagi@gmail.com", "", "", ""),
                LoanClient("Subham Tyagi", "subham.k.tyagi@gmail.com", "", "", ""),
                LoanClient("Subham Tyagi", "subham.k.tyagi@gmail.com", "", "", ""),
                LoanClient("Subham Tyagi", "subham.k.tyagi@gmail.com", "", "", "")
            ),
            onClientClicked = { /*TODO*/ }
        )
    }
}
