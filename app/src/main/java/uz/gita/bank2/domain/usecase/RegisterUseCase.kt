package uz.gita.bank2.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.auth.RegisterRequest

interface RegisterUseCase {
    fun registerUser(register: RegisterRequest): Flow<Result<Unit>>
}