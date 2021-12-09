package uz.gita.bank2.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.auth.NewPasswordRequest
import uz.gita.bank2.domain.repository.AuthRepository
import uz.gita.bank2.domain.usecase.ResetUseCase
import javax.inject.Inject

class ResetUseCaseImpl @Inject constructor(private val repository: AuthRepository) : ResetUseCase {
    override fun newPasswordUser(request: NewPasswordRequest): Flow<Result<Unit>> = repository.newPasswordUser(request)
}