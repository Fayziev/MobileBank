package uz.gita.bank2.domain.usecase.impl

import uz.gita.bank2.domain.repository.AppRepository
import uz.gita.bank2.domain.usecase.SecurityScreenUseCase
import javax.inject.Inject

class SecurityScreenUseCaseImpl @Inject constructor(private val repository: AppRepository) : SecurityScreenUseCase {
    override fun getPinCode(): String = repository.getPinLock()
    override fun setPinCode(pinCode: String) = repository.pinLock(pinCode)
    override fun setPinStatus(pinStatus: Int) = repository.setPinStatus(pinStatus)
    override fun getPinStatus(): Int = repository.getStartPinScreen()

}