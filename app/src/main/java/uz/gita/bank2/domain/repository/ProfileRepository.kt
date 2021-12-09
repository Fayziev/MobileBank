package uz.gita.bank2.domain.repository

import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import uz.gita.bank2.data.request.profile.ProfileEditRequest
import uz.gita.bank2.data.response.Data
import uz.gita.bank2.data.response.DataUser
import java.io.File


interface ProfileRepository {

    fun setInfoProfile(register: ProfileEditRequest): Flow<Result<Data>>
    fun setAvatar(file: File): Flow<Result<Unit>>
    fun getAvatar(): Flow<Result<ResponseBody>>
    fun getInfo(): Flow<Result<DataUser>>
}