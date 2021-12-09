package uz.gita.bank2.domain.repository.impl

import android.annotation.SuppressLint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import uz.gita.bank2.data.pref.MyPref
import uz.gita.bank2.data.request.profile.ProfileEditRequest
import uz.gita.bank2.data.response.Data
import uz.gita.bank2.data.response.DataUser
import uz.gita.bank2.data.response.auth.BasicResponse
import uz.gita.bank2.data.retrofit.api.ProfileApi
import uz.gita.bank2.domain.repository.ProfileRepository
import uz.gita.bank2.utils.toRequestData
import java.io.File
import javax.inject.Inject


class ProfileRepositoryImpl @Inject constructor(private val api: ProfileApi, private val pref: MyPref) : ProfileRepository {

    @SuppressLint("NewApi")
    override fun setInfoProfile(register: ProfileEditRequest): Flow<Result<Data>> = flow {
        val responce = api.editProfile(register)
        when {
            responce.isSuccessful -> emit(Result.success<Data>(responce.body()?.data as Data))
            responce.errorBody() != null -> {
                val errorbody = responce.errorBody()!!.string()
                emit(Result.failure<Data>(Throwable(errorbody)))
            }
            else -> {
                emit(Result.failure<Data>(Throwable("Serverda ulanishda xato")))
            }
        }
    }

    override fun setAvatar(file: File): Flow<Result<Unit>> = flow {
        val responce = api.setAvatar(file.toRequestData())
        when {
            responce.isSuccessful -> emit(Result.success(Unit))
            responce.errorBody() != null -> {
                val errorbody = responce.errorBody()!!.string() as Data
                emit(Result.failure<Unit>(Throwable(errorbody.firstName)))
            }
            else -> {
                emit(Result.failure<Unit>(Throwable("Serverda ulanishda xato")))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getAvatar(): Flow<Result<ResponseBody>> = flow {
        val response = api.getAvatar()
        when {
            response.isSuccessful -> emit(Result.success<ResponseBody>(response.body()!!))
            response.errorBody() != null -> {
                val errorbody = response.errorBody()!!.string() as BasicResponse
                emit(Result.failure<ResponseBody>(Throwable(errorbody.message)))
            }
            else -> {
                emit(Result.failure<ResponseBody>(Throwable("Sever bilan muammo bo'ldi")))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getInfo(): Flow<Result<DataUser>> = flow {
        val responce = api.profileInfo()
        when {
            responce.isSuccessful -> emit(Result.success(responce.body()!!.data))
            responce.errorBody() != null -> {
                val errorbody = responce.errorBody()!!.string() as BasicResponse
                emit(Result.failure<DataUser>(Throwable(errorbody.message)))
            }
            else -> {
                emit(Result.failure<DataUser>(Throwable("Sever bilan muammo bo'ldi")))
            }
        }
    }.flowOn(Dispatchers.IO)

}