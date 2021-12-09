package uz.gita.bank2.domain.usecase

import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import uz.gita.bank2.data.request.profile.ProfileEditRequest
import uz.gita.bank2.data.response.Data
import uz.gita.bank2.data.response.DataUser
import java.io.File

interface ProfileUseCase {
    fun setAvatar(file: File): Flow<Result<Unit>>
    fun getAvatar(): Flow<Result<ResponseBody>>
    fun editProfile(request: ProfileEditRequest): Flow<Result<Data>>
    fun getInfo(): Flow<Result<DataUser>>
}