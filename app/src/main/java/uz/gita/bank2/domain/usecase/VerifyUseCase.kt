package uz.gita.bank2.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.auth.ResendRequest
import uz.gita.bank2.data.request.auth.ResetRequest
import uz.gita.bank2.data.request.auth.VerifyRequest

interface VerifyUseCase {
    fun verifyUser(request: VerifyRequest): Flow<Result<Unit>>
    fun reset(request: ResetRequest):Flow<Result<Unit>>
    fun setScreen()
}