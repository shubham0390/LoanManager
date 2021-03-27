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