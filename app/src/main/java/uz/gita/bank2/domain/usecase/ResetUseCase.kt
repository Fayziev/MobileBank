package uz.gita.bank2.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.auth.NewPasswordRequest

interface ResetUseCase {
    fun newPasswordUser(request: NewPasswordRequest): Flow<Result<Unit>>
}