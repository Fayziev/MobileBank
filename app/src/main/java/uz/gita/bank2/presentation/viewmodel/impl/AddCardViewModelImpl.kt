package uz.gita.bank2.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bank2.data.request.card.AddCardRequest
import uz.gita.bank2.data.request.card.VerifyCardRequest
import uz.gita.bank2.domain.usecase.impl.AddCardUseCaseImpl
import uz.gita.bank2.presentation.viewmodel.AddCardViewModel
import uz.gita.bank2.utils.eventFlow
import uz.gita.bank2.utils.eventValueFlow
import uz.gita.bank2.utils.isConnected
import javax.inject.Inject
@HiltViewModel
class AddCardViewModelImpl @Inject constructor(private val useCase: AddCardUseCaseImpl,
) : ViewModel(), AddCardViewModel {

    override val progressFlow= eventValueFlow<Boolean>()
    override val errorMessageFlow= eventValueFlow<String>()
    override val successFlow= eventValueFlow<Unit>()
    override val backBtnFlow= eventFlow()

    override fun cardAdd(request: AddCardRequest) {
        if (!isConnected()) {
            viewModelScope.launch {
                errorMessageFlow.emit("Internet bilan muammo")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        useCase.addCard(request).onEach {
            it.onSuccess {
                progressFlow.emit(false)
                successFlow.emit(Unit)
            }
            it.onFailure { throwable ->
                errorMessageFlow.emit(throwable.message.toString())
                progressFlow.emit(false)
            }
        }.launchIn(viewModelScope)
    }

    override fun verifyCard(request: VerifyCardRequest) {
        if (!isConnected()) {
            viewModelScope.launch {
                errorMessageFlow.emit("Internet bilan muammo")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        useCase.verifyCard(request).onEach {
            it.onSuccess {
                progressFlow.emit(false)
                successFlow.emit(Unit)
            }
            it.onFailure { throwable ->
                errorMessageFlow.emit(throwable.message.toString())
                progressFlow.emit(false)
            }
        }.launchIn(viewModelScope)
    }

    override fun backBtn() {
        viewModelScope.launch {
            backBtnFlow.emit(Unit)
        }
    }
}