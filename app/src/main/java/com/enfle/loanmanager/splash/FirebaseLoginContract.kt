package com.enfle.loanmanager.splash

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class FirebaseLoginContract : ActivityResultContract<Int, Uri?>() {
    override fun createIntent(context: Context, input: Int?): Intent {
        TODO("Not yet implemented")
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        TODO("Not yet implemented")
    }
}