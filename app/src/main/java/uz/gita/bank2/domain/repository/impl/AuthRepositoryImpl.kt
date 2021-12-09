package uz.gita.bank2.domain.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import uz.gita.bank2.data.pref.MyPref
import uz.gita.bank2.data.request.auth.*
import uz.gita.bank2.data.response.auth.BasicResponse
import uz.gita.bank2.data.retrofit.api.AuthApi
import uz.gita.bank2.domain.repository.AuthRepository
import uz.gita.bank2.utils.timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val pref: MyPref, private val api: AuthApi) : AuthRepository {


    override fun registerUser(request: RegisterRequest): Flow<Result<Unit>> = flow {
        try {
            val response = api.register(request)
            if (response.isSuccessful) {
                pref.phoneNumber = request.phone
                pref.password = request.password
                response.body()?.let {
                }
                emit(Result.success(Unit))
            } else {
                emit(Result.failure(Throwable(response.errorBody().toString())))
            }
        } catch (e: Exception) {
            timber(e.message.toString())
            emit(Result.failure(Throwable("Server bilan muammo bo'ldi")))
        }
    }

    override fun loginUser(request: LoginRequest): Flow<Result<Unit>> = flow {
        try {
            val response = api.login(request)
            if (response.isSuccessful) {
                pref.phoneNumber = request.phone
                pref.password = request.password
                response.body()?.let {
                }
                emit(Result.success(Unit))
            } else {
                emit(Result.failure(Throwable(response.errorBody().toString())))
            }
        } catch (e: Exception) {
            timber(e.message.toString())
            val errorMessage = "Server bilan muammo bo'ldi"
            emit(Result.failure(Throwable(errorMessage)))
        }
    }

    override fun logoutUser(): Flow<Result<Unit>> = flow {
        getResult(api.logout())
    }

    override fun verifyUser(request: VerifyRequest): Flow<Result<Unit>> = flow {

        try {
            val response = api.verify(request)
            if (response.isSuccessful) {
                response.body()?.let {
                    pref.refreshToken = it.data.refreshToken
                    pref.accessToken = it.data.accessToken
                }
                emit(Result.success(Unit))
            } else {
                emit(Result.failure(Throwable(response.errorBody().toString())))
            }
        } catch (e: Exception) {
            timber(e.message.toString())
            emit(Result.failure(Throwable("Server bilan muammo bo'ldi")))
        }
    }

    override fun resendUser(request: ResendRequest): Flow<Result<Unit>> = flow {
        getResult(api.resend(request))
    }


    override fun resetUser(request: ResetRequest): Flow<Result<Unit>> = flow {
        getResult(api.reset(request))
    }

    override fun newPasswordUser(request: NewPasswordRequest): Flow<Result<Unit>> = flow {
        getResult(api.newPassword(request))
    }

    private fun getResult(response: Response<BasicResponse>): Flow<Result<Unit>> = flow<Result<Unit>> {
        if (response.code() in 200..299) {
            emit(Result.success(Unit))
        } else {
            emit(Result.failure(Throwable(response.errorBody().toString())))
        }
    }.catch {
        timber(it.message.toString())
        emit(Result.failure(Throwable(response.errorBody().toString())))
    }


}