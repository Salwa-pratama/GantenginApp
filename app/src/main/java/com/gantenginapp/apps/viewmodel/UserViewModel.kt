package com.gantenginapp.apps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gantenginapp.apps.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    fun setUser(newUser: User) {
       _user.value = newUser
    }

    fun refreshUser(fetchUser: suspend () -> User) {
        viewModelScope.launch {
            try {
                val updateUser = fetchUser()
                _user.value = updateUser
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



}