package uz.gita.bank2.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import uz.gita.bank2.data.request.profile.ProfileEditRequest
import uz.gita.bank2.data.response.DataUser
import java.io.File

interface ProfileViewModel {

    val errorFlow: Flow<String>
    val successFlow: Flow<String>
    val progressFlow: Flow<Boolean>

    val setAvatarFlow: Flow<Unit>
    val getAvatarFlow: Flow<ResponseBody>
    val editProfileFlow: Flow<Unit>
    val getInfoFlow: Flow<DataUser>


    fun setAvatar(file: File)
    fun getAvatar()
    fun editProfile(request: ProfileEditRequest)
    fun getInfo()
}