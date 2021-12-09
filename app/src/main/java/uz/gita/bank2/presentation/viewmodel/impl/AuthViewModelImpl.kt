package uz.gita.bank2.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.bank2.presentation.viewmodel.AuthViewModel
import uz.gita.bank2.utils.eventFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModelImpl @Inject constructor() : ViewModel(), AuthViewModel {
    override val openLoginFlow = eventFlow()
    override val openRegisterFlow = eventFlow()

    override fun openLogin() {
        viewModelScope.launch {
            openLoginFlow.emit(Unit)
        }
    }

    override fun openRegister() {
        viewModelScope.launch {
            openRegisterFlow.emit(Unit)
        }
    }
}