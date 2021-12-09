package uz.gita.bank2.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.auth.*

interface AuthRepository {

    fun registerUser(data: RegisterRequest): Flow<Result<Unit>>

    fun loginUser(data: LoginRequest): Flow<Result<Unit>>

    fun verifyUser(data: VerifyRequest): Flow<Result<Unit>>

    fun logoutUser(): Flow<Result<Unit>>

    fun resendUser(request: ResendRequest): Flow<Result<Unit>>

    fun resetUser(request: ResetRequest): Flow<Result<Unit>>

    fun newPasswordUser(request: NewPasswordRequest): Flow<Result<Unit>>
}