package uz.gita.bank2.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bank2.data.request.transfer.MoneyTransferRequest
import uz.gita.bank2.data.request.transfer.TransferFeeRequest
import uz.gita.bank2.domain.usecase.TransferUseCase
import uz.gita.bank2.presentation.viewmodel.TransferViewModel
import uz.gita.bank2.utils.eventFlow
import uz.gita.bank2.utils.eventValueFlow
import uz.gita.bank2.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class TransferViewModelImpl @Inject constructor(
    private val useCase: TransferUseCase
) : ViewModel(), TransferViewModel {

    override val errorMessageFlow = eventValueFlow<String>()
    override val progressFlow = eventValueFlow<Boolean>()
    override val backBtnFlow = eventValueFlow<Unit>()
    override val successFlow = eventFlow()

    override fun sendMoney(card: MoneyTransferRequest) {
        if (!isConnected()) {
            viewModelScope.launch {
                errorMessageFlow.emit("Internet bilan muammo")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        useCase.sendMoney(card).onEach {
            it.onSuccess {
                successFlow.emit(Unit)
                progressFlow.emit(false)
            }
            it.onFailure { throwable ->
                errorMessageFlow.emit(throwable.message.toString())
                progressFlow.emit(false)
            }
        }.launchIn(viewModelScope)
    }

    override fun transferFee(data: TransferFeeRequest) {
        if (!isConnected()) {
            viewModelScope.launch {
                errorMessageFlow.emit("Internet bilan muammo")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        useCase.transferFee(data).onEach {
            it.onSuccess {
                successFlow.emit(Unit)
                progressFlow.emit(false)
            }
            it.onFailure { throwable ->
                errorMessageFlow.emit(throwable.message.toString())
                progressFlow.emit(false)
            }
        }.launchIn(viewModelScope)
    }

    override fun history(pageNumber: Int, pageSize: Int) {
        if (!isConnected()) {
            viewModelScope.launch {
                errorMessageFlow.emit("Internet bilan muammo")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        useCase.history(pageNumber, pageSize).onEach {
            it.onSuccess {
                successFlow.emit(Unit)
                progressFlow.emit(false)
            }
            it.onFailure { throwable ->
                errorMessageFlow.emit(throwable.message.toString())
                progressFlow.emit(false)
            }
        }.launchIn(viewModelScope)
    }
}