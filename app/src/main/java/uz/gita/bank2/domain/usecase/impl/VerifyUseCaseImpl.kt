package uz.gita.bank2.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.models.StartScreenEnum
import uz.gita.bank2.data.request.auth.ResendRequest
import uz.gita.bank2.data.request.auth.ResetRequest
import uz.gita.bank2.data.request.auth.VerifyRequest
import uz.gita.bank2.domain.repository.AppRepository
import uz.gita.bank2.domain.repository.AuthRepository
import uz.gita.bank2.domain.usecase.VerifyUseCase
import javax.inject.Inject

class VerifyUseCaseImpl @Inject constructor(
    private val repositoryAuth: AuthRepository,
    private val repositoryApp: AppRepository
) : VerifyUseCase {
    override fun verifyUser(request: VerifyRequest): Flow<Result<Unit>> = repositoryAuth.verifyUser(request)
    override fun reset(request: ResetRequest): Flow<Result<Unit>> =repositoryAuth.resetUser(request)

    override fun setScreen() = repositoryApp.setStartScreen(StartScreenEnum.MAIN)

}