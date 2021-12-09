package uz.gita.bank2.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bank2.data.request.auth.RegisterRequest
import uz.gita.bank2.domain.usecase.RegisterUseCase
import uz.gita.bank2.presentation.viewmodel.RegisterViewModel
import uz.gita.bank2.utils.eventFlow
import uz.gita.bank2.utils.eventValueFlow
import uz.gita.bank2.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class RegisterViewModelImpl @Inject constructor(
    private val useCase: RegisterUseCase,
) : ViewModel(), RegisterViewModel {
    override val backBtnFlow = eventFlow()
    override val openVerifyScreenFlow = eventFlow()
    override val errorMessageFlow = eventValueFlow<String>()
    override val progressFlow = eventValueFlow<Boolean>()

    override fun registerUser(request: RegisterRequest) {
        if (!isConnected()) {
            viewModelScope.launch {
                errorMessageFlow.emit("Internet mavjud emas")
            }
            return
        }
        progressFlow.tryEmit(true)
        useCase.registerUser(request).onEach {
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

    override fun backBtn() {
        viewModelScope.launch {
            backBtnFlow.emit(Unit)
        }
    }

}