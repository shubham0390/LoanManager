package com.enfle.loanmanager.user

import android.net.Uri
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun EditProfileScreen(viewModel: EditProfileViewModel = viewModel(), onProfileSaved: () -> Unit = {}) {
    Scaffold(backgroundColor = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ProfileImage(
                Modifier
                    .clickable(onClick = {})
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 24.dp, top = 56.dp),
                viewModel.photoUrl.value,
            )

            val name: String by viewModel.name.collectAsState("")
            LabelTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                value = name,
                onValueChange = { viewModel.onNameChange(it) },
                textModifier = Modifier.fillMaxWidth(),
                label = "Enter Your Name",
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
                label = "Enter your email",
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "") }
            )

            val phoneNumber: String by viewModel.phoneNumber.collectAsState("")
            LabelTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                value = phoneNumber,
                onValueChange = { viewModel.onPhoneNumberChange(it) },
                textModifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Filled.Phone, contentDescription = "") },
                label = "Enter your phone number"
            )

            Button(
                onClick = {
                    viewModel.updateProfile()
                    onProfileSaved()
                },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 24.dp)
            ) {
                Text(text = "Save Profile")
            }
        }
    }
}

@Composable
private fun ProfileImage(modifier: Modifier = Modifier, imageUri: Uri) {
    Box(
        modifier = modifier
    ) {
        CoilImage(
            data = imageUri,
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

        Image(
            imageVector = Icons.Filled.CameraAlt,
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.Center)
                .clip(CircleShape),
        )
    }
}

@Composable
fun LabelTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    textModifier: Modifier = Modifier,
    validate: (String) -> Boolean = { true },
    errorMessage: String = "",
    errorModifier: Modifier = Modifier,
    label: String = "",
    labelModifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    val invalidInput = !validate(value)
    Box(
        modifier = modifier
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TextField(
                modifier = textModifier,
                value = value,
                onValueChange = onValueChange,
                label = { Text(label, modifier = labelModifier) },
                isError = invalidInput,
                leadingIcon = leadingIcon
            )
            if (invalidInput) {
                Text(
                    textAlign = TextAlign.Center,
                    text = errorMessage,
                    style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.error),
                    modifier = errorModifier
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewEditProfile() {
    EditProfileScreen(viewModel = EditProfileViewModel(inPreviewMode = true))
}