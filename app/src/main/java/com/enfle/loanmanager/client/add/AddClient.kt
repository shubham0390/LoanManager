package com.enfle.loanmanager.client.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.enfle.loanmanager.beans.ActionState
import com.enfle.loanmanager.user.LabelTextField
import dev.chrisbanes.accompanist.coil.CoilImage
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun AddClientScreen(navController: NavController, viewModel: AddClientViewModel = viewModel()) {
    Scaffold(backgroundColor = Color.White) {

        viewModel.createClientActionState.collectAsState().value
            .apply {
                when (state) {
                    ActionState.NONE -> {
                    }
                    ActionState.SUCCESS -> {
                    }
                    ActionState.PROCESSING -> {
                    }
                    ActionState.FAILURE -> {
                    }
                }
            }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .clickable(onClick = {})
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 24.dp, top = 56.dp)
            ) {
                CoilImage(
                    data = viewModel.photoUrl.value,
                    fadeIn = true,
                    contentDescription = "My content description",
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

                Image(
                    imageVector = Icons.Filled.CameraAlt,
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clip(CircleShape),
                )
            }

            val name: String by viewModel.name.collectAsState("")
            LabelTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                value = name,
                onValueChange = { viewModel.onNameChange(it) },
                textModifier = Modifier.fillMaxWidth(),
                label = "Enter Client's Name",
                leadingIcon = { Icon(Icons.Filled.SupervisedUserCircle, contentDescription = "") }
            )

            val email: String by viewModel.email.collectAsState("")
            LabelTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                value = email,
                onValueChange = { viewModel.onEmailChange(it) },
                textModifier = Modifier.fillMaxWidth(),
                label = "Enter Client's email",
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "") }
            )

            val phoneNumber: String by viewModel.phoneNumber.collectAsState("")
            LabelTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                value = phoneNumber,
                validate = { viewModel.validatePhoneNumber(it) },
                onValueChange = { viewModel.onPhoneNumberChange(it) },
                textModifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Filled.Phone, contentDescription = "") },
                label = "Enter client's phone number"
            )

            Button(
                onClick = {
                    viewModel.addCustomer()
                    navController.popBackStack()
                },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 24.dp)
            ) {
                Text(text = "Add Client")
            }
        }
    }
}

@ExperimentalCoroutinesApi
@Preview
@Composable
fun PreviewAddClient() {
    val context = LocalContext.current
    AddClientScreen(
        viewModel = AddClientViewModel(),
        navController = NavController(context)
    )
}