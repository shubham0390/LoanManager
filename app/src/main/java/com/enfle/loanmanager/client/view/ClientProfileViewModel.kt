package com.enfle.loanmanager.client.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enfle.loanmanager.beans.ActionResult
import com.enfle.loanmanager.beans.ActionState
import com.enfle.loanmanager.beans.LoanClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClientProfileViewModel : ViewModel() {
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber

    private val _photoUrl = MutableStateFlow("")
    val photoUrl: StateFlow<String> = _photoUrl

    private val _createClientActionState: MutableStateFlow<ActionResult> = MutableStateFlow(ActionResult(ActionState.NONE))
    val createClientActionState: StateFlow<ActionResult> = _createClientActionState

    fun addCustomer() {
        viewModelScope.launch {
            val loggedInUser = FirebaseAuth.getInstance().currentUser
            val client = LoanClient(
                name = _name.value,
                email = _email.value,
                phoneNumber = "+91" + _phoneNumber.value.trim(),
                photoUrl = _photoUrl.value,
                userId = loggedInUser!!.uid
            )

            val database = FirebaseDatabase.getInstance()
            val clientRefs = database.getReference("clients")

            val child = clientRefs.child(client.phoneNumber)
            val exitingClient = child.get().result?.getValue<LoanClient>()
            if (exitingClient == null) {
                val task = clientRefs.child(client.phoneNumber).setValue(client)
                task.addOnCompleteListener {

                }
                task.addOnSuccessListener {
                    Log.i("Client", "Data added successfully")
                }
                task.addOnFailureListener { error ->
                    Log.e("Client", "Failed", error)
                }
            } else {
                //TODO Show error
            }
        }
    }

    fun validatePhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.length == 10
    }
}