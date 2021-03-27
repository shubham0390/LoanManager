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

import androidx.lifecycle.ViewModel
import com.enfle.loanmanager.BaseError
import com.enfle.loanmanager.ErrorCode
import com.enfle.loanmanager.LocalizedString
import com.enfle.loanmanager.ViewState
import com.enfle.loanmanager.beans.LoanClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _clientData: MutableStateFlow<ViewState<List<LoanClient>>> = MutableStateFlow(ViewState.Loading(LocalizedString.raw("Loading")))
    val clientData: StateFlow<ViewState<List<LoanClient>>> = _clientData

    fun syncClientData() {
        val database = FirebaseDatabase.getInstance()
        val loggedInUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        val clientRefs = database.getReference(loggedInUserId).child("clients")
        clientRefs.get().addOnSuccessListener { dataSnapshot ->
            val clients = dataSnapshot.children.mapNotNull { snapshot -> snapshot.getValue(LoanClient::class.java) }
            _clientData.value = ViewState.Data(clients)
        }

        clientRefs.get().addOnFailureListener {
            _clientData.value = ViewState.Error(BaseError(LocalizedString.raw("Some error"), ErrorCode.NO_INTERNET))
        }
    }
}
