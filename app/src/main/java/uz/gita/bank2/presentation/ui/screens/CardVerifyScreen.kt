package uz.gita.bank2.presentation.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges
import uz.gita.bank2.R
import uz.gita.bank2.data.request.card.VerifyCardRequest
import uz.gita.bank2.databinding.ScreenSmsVerifyBinding
import uz.gita.bank2.presentation.viewmodel.AddCardViewModel
import uz.gita.bank2.presentation.viewmodel.impl.AddCardViewModelImpl
import uz.gita.bank2.utils.scope
import uz.gita.bank2.utils.showToast
import uz.gita.bank2.utils.timber

@AndroidEntryPoint
class CardVerifyScreen : Fragment(R.layout.screen_sms_verify) {

    private val binding by viewBinding(ScreenSmsVerifyBinding::bind)
    private val viewModel: AddCardViewModel by viewModels<AddCardViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        val bundle = requireArguments()
        val pan = bundle.getString("pan") as String
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

        loginBtn.setOnClickListener {
            viewModel.verifyCard(VerifyCardRequest(pan, inputCodeEditText.text.toString()))
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

        viewModel.successFlow.onEach {
            findNavController().navigate(R.id.mainScreen)
        }.launchIn(lifecycleScope)

        viewModel.backBtnFlow.onEach {
            findNavController().popBackStack()
        }.launchIn(lifecycleScope)
    }

}
