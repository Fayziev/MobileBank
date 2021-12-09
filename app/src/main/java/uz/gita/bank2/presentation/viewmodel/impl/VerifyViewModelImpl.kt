package uz.gita.bank2.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bank2.data.request.auth.ResetRequest
import uz.gita.bank2.data.request.auth.VerifyRequest
import uz.gita.bank2.domain.usecase.VerifyUseCase
import uz.gita.bank2.presentation.viewmodel.VerifyViewModel
import uz.gita.bank2.utils.eventFlow
import uz.gita.bank2.utils.eventValueFlow
import uz.gita.bank2.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class VerifyViewModelImpl @Inject constructor(private val useCase: VerifyUseCase) : ViewModel(), VerifyViewModel {

    override val openMainScreenFlow = eventFlow()
    override val errorMessageFlow = eventValueFlow<String>()
    override val progressFlow = eventValueFlow<Boolean>()
    override val resendFlow = eventFlow()
    override val countDownFlow = eventValueFlow<Int>()
    override val stopCountDownFlow = eventFlow()
    override val backBtnFlow = eventFlow()

    override fun countDown() {
        viewModelScope.launch {
            for (i in 0..60) {
                countDownFlow.emit(60 - i)
                delay(1000)
            }
            stopCountDownFlow.emit(Unit)
        }
    }

    override fun verifyUser(request: VerifyRequest) {
        if (!isConnected()) {
            viewModelScope.launch {
                errorMessageFlow.emit("Internet muammosi")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        useCase.verifyUser(request).onEach {
            it.onSuccess {
                viewModelScope.launch {
                    progressFlow.emit(false)
                    openMainScreenFlow.emit(Unit)
                }
            }
            it.onFailure { throwable ->
                viewModelScope.launch {
                    errorMessageFlow.emit(throwable.message.toString())
                    progressFlow.emit(false)
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun resend(request: ResetRequest) {
        if (!isConnected()) {
            viewModelScope.launch {
                errorMessageFlow.emit("Internet muammosi")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        useCase.reset(request).onEach {
            it.onSuccess {
                viewModelScope.launch {
                    progressFlow.emit(false)
                    resendFlow.emit(Unit)
                }
            }
            it.onFailure { throwable ->
                viewModelScope.launch {
                    errorMessageFlow.emit(throwable.message.toString())
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