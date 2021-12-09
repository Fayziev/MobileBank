package uz.gita.bank2.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.auth.LoginRequest
import uz.gita.bank2.data.request.auth.ResetRequest

interface LoginUseCase {

    fun resetUser(request: ResetRequest): Flow<Result<Unit>>
    fun loginUser(request: LoginRequest): Flow<Result<Unit>>
}