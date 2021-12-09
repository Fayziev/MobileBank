package uz.gita.bank2.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import uz.gita.bank2.data.request.profile.ProfileEditRequest
import uz.gita.bank2.data.response.Data
import uz.gita.bank2.data.response.DataUser
import uz.gita.bank2.domain.repository.ProfileRepository
import uz.gita.bank2.domain.usecase.ProfileUseCase
import java.io.File
import javax.inject.Inject

class ProfileUseCaseImpl @Inject constructor(private val repository: ProfileRepository) : ProfileUseCase {

    override fun setAvatar(file: File): Flow<Result<Unit>> = repository.setAvatar(file)

    override fun getAvatar(): Flow<Result<ResponseBody>> = repository.getAvatar()

    override fun editProfile(request: ProfileEditRequest): Flow<Result<Data>> = repository.setInfoProfile(request)

    override fun getInfo(): Flow<Result<DataUser>> = repository.getInfo()

}