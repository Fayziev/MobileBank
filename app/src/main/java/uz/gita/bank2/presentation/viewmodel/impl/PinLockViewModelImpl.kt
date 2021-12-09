package uz.gita.bank2.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.bank2.domain.usecase.SecurityScreenUseCase
import uz.gita.bank2.presentation.viewmodel.PinLockViewModel
import uz.gita.bank2.utils.eventFlow
import uz.gita.bank2.utils.eventValueFlow
import javax.inject.Inject

@HiltViewModel
class PinLockViewModelImpl @Inject constructor(
    private val useCase: SecurityScreenUseCase,
) : ViewModel(), PinLockViewModel {
    override val openMainFlow = eventFlow()
    override val openEnterPinFlow = eventFlow()
    override val pinCodeFlow = eventValueFlow<String>()
    override val pinStatusFlow = eventValueFlow<Int>()

    override fun startMainScreen() {
        viewModelScope.launch {
            openMainFlow.emit(Unit)
        }
    }

    override fun enterScreen() {
        viewModelScope.launch {
            openEnterPinFlow.emit(Unit)
        }
    }

    override fun setStatusPint(pinStatus: Int) {
        useCase.setPinStatus(pinStatus)
    }

    override fun setPinCode(pinCode: String) {
        useCase.setPinCode(pinCode)
    }

    override fun getPin() {
            pinCodeFlow.tryEmit(useCase.getPinCode())
            pinStatusFlow.tryEmit(useCase.getPinStatus())
    }

}