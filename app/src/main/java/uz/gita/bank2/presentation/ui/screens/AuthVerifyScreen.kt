package uz.gita.bank2.presentation.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges
import uz.gita.bank2.R
import uz.gita.bank2.data.request.auth.ResetRequest
import uz.gita.bank2.data.request.auth.VerifyRequest
import uz.gita.bank2.databinding.ScreenSmsVerifyBinding
import uz.gita.bank2.presentation.viewmodel.VerifyViewModel
import uz.gita.bank2.presentation.viewmodel.impl.VerifyViewModelImpl
import uz.gita.bank2.utils.scope
import uz.gita.bank2.utils.showToast
import uz.gita.bank2.utils.timber

@AndroidEntryPoint
class AuthVerifyScreen : Fragment(R.layout.screen_sms_verify) {

    private val binding by viewBinding(ScreenSmsVerifyBinding::bind)
    private val viewModel: VerifyViewModel by viewModels<VerifyViewModelImpl>()
    private val args: AuthVerifyScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        viewModel.countDown()
        combine(
            inputCodeEditText.textChanges().map {
                it.length == 6
            },
            transform = { send -> send }
        ).onEach {
            loginBtn.isEnabled = it[0]
        }.launchIn(lifecycleScope)

        backBtn.setOnClickListener {
            viewModel.backBtn()
        }

        resendText.setOnClickListener {
            viewModel.resend(ResetRequest(args.phone))
            viewModel.countDown()
        }

        loginBtn.setOnClickListener {
            viewModel.verifyUser(VerifyRequest(args.phone, inputCodeEditText.text.toString()))
            loginBtn.isEnabled = false
        }

        viewModel.progressFlow.onEach {
            if (it) progress.show()
            else progress.hide()
        }.launchIn(lifecycleScope)

        viewModel.errorMessageFlow.onEach { message ->
            loginBtn.isEnabled = false
            inputCodeEditText.setText("")
            showToast("Error code!")
            inputCode.isErrorEnabled = true
            timber(message)
        }.launchIn(lifecycleScope)

        viewModel.openMainScreenFlow.onEach {
            findNavController().navigate(R.id.securityScreen)
        }.launchIn(lifecycleScope)

        viewModel.backBtnFlow.onEach {
            findNavController().popBackStack()
        }.launchIn(lifecycleScope)

        viewModel.countDownFlow.onEach {
            countTimer.text = it.toString()
        }.launchIn(lifecycleScope)

        viewModel.stopCountDownFlow.onEach {
            loginBtn.isEnabled = false
            resendText.visibility = View.VISIBLE
        }.launchIn(lifecycleScope)
    }

}
