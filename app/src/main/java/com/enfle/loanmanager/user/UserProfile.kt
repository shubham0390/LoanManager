package com.enfle.loanmanager.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun UserProfileScreen(

) {
    val user = remember { mutableStateOf(FirebaseAuth.getInstance().currentUser) }.value
    Surface {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.End)
                    .padding(top = 24.dp, end = 24.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(48.dp),
                    contentDescription = "",
                    imageVector = Icons.Filled.Clear
                )
            }

            CoilImage(
                data = user?.photoUrl ?: "https://picsum.photos/300/300",
                contentDescription = "My content description",
                fadeIn = true,
                modifier = Modifier
                    .size(120.dp)
                    .padding(top = 24.dp, start = 24.dp),
                loading = {
                    Box(Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                },
                error = {
                    Image(
                        imageVector = Icons.Filled.Face,
                        modifier = Modifier.size(40.dp),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Color.Black),
                        contentScale = ContentScale.Fit
                    )
                }
            )

            Text(text = user?.displayName ?: "Display Name")
            Text(text = user?.phoneNumber ?: "Phone Number")
            user?.email?.let { email ->
                Text(text = email)
            }
        }
    }
}

@Preview
@Composable
fun UserProfilePreview() {
    UserProfileScreen()
}