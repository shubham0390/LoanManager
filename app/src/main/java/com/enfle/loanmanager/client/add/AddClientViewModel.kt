package com.enfle.loanmanager.client.add

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enfle.loanmanager.beans.ActionResult
import com.enfle.loanmanager.beans.ActionState
import com.enfle.loanmanager.beans.LoanClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddClientViewModel : ViewModel() {
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

    fun onNameChange(name: String) {
        _name.value = name
    }

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPhoneNumberChange(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
    }

    fun onPhotoUrlChange(photoUrl: String) {
        _photoUrl.value = photoUrl
    }

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

            // Write a message to the database
            val database = FirebaseDatabase.getInstance()
            val loggedInUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            val clientRefs = database.getReference(loggedInUserId).child("clients")
            val child = clientRefs.child(client.phoneNumber)
            child.get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        //TODO: Show error to user
                    } else {
                        it.ref.setValue(client)
                            .addOnSuccessListener { Log.i("Client", "Data added successfully") }
                            .addOnFailureListener { error ->
                                //TODO: Show error to user
                                Log.e("Client", "Failed", error)
                            }
                    }
                }.addOnFailureListener {

                }
        }
    }

    fun validatePhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.length == 10
    }
}