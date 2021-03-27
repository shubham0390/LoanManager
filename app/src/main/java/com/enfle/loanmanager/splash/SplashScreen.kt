package com.enfle.loanmanager.splash

import android.app.Activity
import android.content.Intent
import android.os.Looper
import android.util.Log
import androidx.activity.compose.registerForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.enfle.loanmanager.R
import com.enfle.loanmanager.Routes
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.EmailBuilder
import com.firebase.ui.auth.AuthUI.IdpConfig.PhoneBuilder
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import java.util.logging.Handler

@Composable
fun SplashScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    if (auth.currentUser != null) {
        if (auth.currentUser?.displayName.isNullOrEmpty()) {
            navController.navigate(Routes.EditProfile.name)
        } else {
            navController.navigate(Routes.Home.name)
        }
    } else {
        val response = remember { mutableStateOf<IdpResponse?>(null) }

        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                navController.navigate(Routes.Home.name)
            } else {
                response.value = IdpResponse.fromResultIntent(result.data)
            }
        }

        android.os.Handler(Looper.getMainLooper()).postDelayed({
            launcher.launch(getSingInIntent())
        }, 500)

        response.value?.let { safeResponse ->
            HandleLoginFailure(safeResponse)
        }
    }
}

@Composable
private fun HandleLoginFailure(response: IdpResponse?) {
    // Sign in failed
    if (response == null) {
        // User pressed back button
        ShowSnackbar(R.string.sign_in_cancelled)
        return
    }

    if (response.error!!.errorCode == ErrorCodes.NO_NETWORK) {
        ShowSnackbar(R.string.no_internet_connection)
        return
    }

    if (response.error!!.errorCode == ErrorCodes.ERROR_USER_DISABLED) {
        ShowSnackbar(R.string.account_disabled)
        return
    }

    ShowSnackbar(R.string.unknown_error)
    Log.e("Splash", "Sign-in error: ", response.error)
}

@Composable
fun ShowSnackbar(@StringRes message: Int) {
    Snackbar(
        content = { Text(text = stringResource(message)) }
    )
}

fun getSingInIntent(): Intent {
    val actionCodeSettings = ActionCodeSettings.newBuilder()
        .setAndroidPackageName(
            "com.enfle.loanmanager",
            true,/*installIfNotAvailable*/
            null /*minimumVersion*/
        )
        .setHandleCodeInApp(true)
        .setUrl("https://google.com") // This URL needs to be whitelisted
        .build();

    return AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setIsSmartLockEnabled(true)
        .setAvailableProviders(
            listOf(
                EmailBuilder()
                    .enableEmailLinkSignIn()
                    .setActionCodeSettings(actionCodeSettings)
                    .build(),
                PhoneBuilder().build()
            )
        )
        .build()
}