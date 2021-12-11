package uz.gita.bank2.presentation.ui.screens

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import okhttp3.ResponseBody
import ru.ldralighieri.corbind.widget.textChanges
import uz.gita.bank2.R
import uz.gita.bank2.data.request.profile.ProfileEditRequest
import uz.gita.bank2.databinding.PageProfileBinding
import uz.gita.bank2.presentation.viewmodel.ProfileViewModel
import uz.gita.bank2.presentation.viewmodel.impl.ProfileViewModelImpl
import uz.gita.bank2.utils.FileUtils.getPath
import uz.gita.bank2.utils.myLog
import uz.gita.bank2.utils.scope
import uz.gita.bank2.utils.showToast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

@AndroidEntryPoint
class PageProfile : Fragment(R.layout.page_profile) {
    private val binding by viewBinding(PageProfileBinding::bind)
    private val startForProfileImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        val resultCode = result.resultCode
        val data = result.data
        when (resultCode) {
            Activity.RESULT_OK -> {
                val fileUri = data?.data!!
                binding.imageAccount.setImageURI(fileUri)
                file = File(getPath(requireContext(), fileUri))
            }
        }
    }
    private var file: File? = null
    private val viewModel: ProfileViewModel by viewModels<ProfileViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        combine(
            inputFirstEditText.textChanges().map {
                it.length > 4
            },
            inputLastEditText.textChanges().map {
                it.length > 4
            },
            phoneEditText.textChanges().map {
                myLog("Phone=${it}", "RRR")
                it.length == 19
            },
            inputPasswordEditText.textChanges().map {
                it.length in 7..19
            },

            transform = { firstName, lastName, phone, password ->
                firstName && lastName && phone
                        && password
            }
        ).onEach { loginBtn.isEnabled = it }
            .launchIn(lifecycleScope)

        imageAccount.setOnClickListener {
            ImagePicker.with(requireActivity())
                .compress(1024)
                .crop()
                .maxResultSize(512, 512)
                .saveDir(File(requireContext().getExternalFilesDir(null)?.absolutePath, "MyImage"))
                .createIntent {
                    startForProfileImageResult.launch(it)
                }
        }
        loginBtn.setOnClickListener {
            file?.let { file -> viewModel.setAvatar(file) }
            viewModel.editProfile(
                ProfileEditRequest(
                    inputFirstEditText.text.toString(),
                    inputLastEditText.text.toString(),
                    inputPasswordEditText.text.toString()
                )
            )
        }
        viewModel.getAvatar()
        viewModel.getInfo()
        viewModel.getInfoFlow.onEach {
            inputFirstEditText.setText(it.firstName)
            inputLastEditText.setText(it.lastName)
            phoneEditText.setText(it.phone.substring(4, it.phone.length))
            inputPasswordEditText.setText(it.password)
        }.launchIn(lifecycleScope)
        viewModel.errorFlow.onEach {
            showToast(it)
        }.launchIn(lifecycleScope)

        viewModel.getAvatarFlow.onEach {
            downloadImage(it)
        }.launchIn(lifecycleScope)
        viewModel.progressFlow.onEach {
            if (it) progress.show()
            else progress.hide()
        }.launchIn(lifecycleScope)
        viewModel.editProfileFlow.onEach {
            showToast("Success")
        }


    }

    private fun downloadImage(body: ResponseBody): Boolean {
        return try {
            var `in`: InputStream? = null
            var out: FileOutputStream? = null
            try {
                `in` = body.byteStream()
                out = FileOutputStream(requireActivity().getExternalFilesDir(null).toString() + File.separator + "Android.jpg")
                var c: Int
                while (`in`.read().also { c = it } != -1) {
                    out.write(c)
                }
            } catch (e: IOException) {
                return false
            } finally {
                `in`?.close()
                out?.close()
            }
            val width: Int
            val height: Int
            val bMap = BitmapFactory.decodeFile(requireActivity().getExternalFilesDir(null).toString() + File.separator + "Android.jpg")
            width = 2 * bMap.width
            height = 3 * bMap.height
            val bMap2 = Bitmap.createScaledBitmap(bMap, width, height, false)
            binding.imageAccount.setImageBitmap(bMap2)
            true
        } catch (e: IOException) {
            false
        }
    }

}