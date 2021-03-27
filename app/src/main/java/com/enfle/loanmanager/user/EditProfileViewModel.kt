package com.enfle.loanmanager.user

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EditProfileViewModel(
    inPreviewMode: Boolean = false
) : ViewModel() {
    private val _name: MutableStateFlow<String> = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _email: MutableStateFlow<String> = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _phoneNumber: MutableStateFlow<String> = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber

    private val _photoUrl: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)
    val photoUrl: StateFlow<Uri> = _photoUrl

    init {
        if (!inPreviewMode) {
            val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
            val firebaseUser = firebaseAuth.currentUser
            _name.value = firebaseUser?.displayName.toString()
            _email.value = firebaseUser?.displayName.toString()
            _phoneNumber.value = firebaseUser?.displayName.toString()
            _photoUrl.value = firebaseUser?.photoUrl ?: Uri.EMPTY
        } else {
            _name.value = ""
            _email.value = ""
            _phoneNumber.value = ""
            _photoUrl.value = Uri.EMPTY
        }
    }

    fun onNameChange(name: String) {
        _name.value = name
    }

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPhoneNumberChange(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
    }

    fun onPhotoUrlChange(photoUrl: Uri) {
        _photoUrl.value = photoUrl
    }

    fun updateProfile() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        firebaseUser?.updateProfile(
            userProfileChangeRequest {
                displayName = _name.value
                photoUri = _photoUrl.value
            }
        )
    }
}