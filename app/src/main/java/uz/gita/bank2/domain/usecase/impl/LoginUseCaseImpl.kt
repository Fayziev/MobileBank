package uz.gita.bank2.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.auth.LoginRequest
import uz.gita.bank2.data.request.auth.ResetRequest
import uz.gita.bank2.domain.repository.AuthRepository
import uz.gita.bank2.domain.usecase.LoginUseCase
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(private val repository: AuthRepository) : LoginUseCase {
    override fun resetUser(request: ResetRequest): Flow<Result<Unit>> = repository.resetUser(request)

    override fun loginUser(request: LoginRequest): Flow<Result<Unit>> = repository.loginUser(request)
}