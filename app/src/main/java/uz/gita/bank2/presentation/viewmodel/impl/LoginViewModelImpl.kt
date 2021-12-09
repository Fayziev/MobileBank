package uz.gita.bank2.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bank2.data.request.auth.LoginRequest
import uz.gita.bank2.domain.usecase.LoginUseCase
import uz.gita.bank2.presentation.viewmodel.LoginViewModel
import uz.gita.bank2.utils.eventFlow
import uz.gita.bank2.utils.eventValueFlow
import uz.gita.bank2.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class LoginViewModelImpl @Inject constructor(
    private val useCase: LoginUseCase,
) : ViewModel(), LoginViewModel {

    override val openVerifyScreenFlow = eventFlow()
    override val progressFlow = eventValueFlow<Boolean>()
    override val errorMessageFlow = eventValueFlow<String>()
    override val openRegisterFlow = eventFlow()
    override val backBtnFlow = eventFlow()

    override fun loginUser(request: LoginRequest) {

        if (!isConnected()) {
            viewModelScope.launch {
                errorMessageFlow.emit("Internet mavjud emas")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        useCase.loginUser(request).onEach {
            it.onFailure { throwable ->
                viewModelScope.launch {
                    errorMessageFlow.emit(throwable.message.toString())
                    progressFlow.emit(false)
                }
            }
            it.onSuccess {
                viewModelScope.launch {
                    openVerifyScreenFlow.emit(Unit)
                    progressFlow.emit(false)
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun openRegister() {
        viewModelScope.launch {
            openRegisterFlow.emit(Unit)
        }
    }
    override fun backBtn() {
        viewModelScope.launch {
            backBtnFlow.emit(Unit)
        }
    }

}