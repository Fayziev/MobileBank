package uz.gita.bank2.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.auth.RegisterRequest
import uz.gita.bank2.domain.repository.AuthRepository
import uz.gita.bank2.domain.usecase.RegisterUseCase
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(private val repository: AuthRepository) : RegisterUseCase {
    override fun registerUser(register: RegisterRequest): Flow<Result<Unit>> = repository.registerUser(register)
}