package uz.gita.bank2.presentation.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.andrognito.patternlockview.utils.PatternLockUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bank2.R
import uz.gita.bank2.databinding.ScreenPinRegisterBinding
import uz.gita.bank2.presentation.viewmodel.PinLockViewModel
import uz.gita.bank2.presentation.viewmodel.impl.PinLockViewModelImpl
import uz.gita.bank2.utils.scope


@AndroidEntryPoint
class PinRegisterScreen : Fragment(R.layout.screen_pin_register) {
    private val binding by viewBinding(ScreenPinRegisterBinding::bind)
    private val viewModel: PinLockViewModel by viewModels<PinLockViewModelImpl>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        viewModel.setStatusPint(0)
        skipBtn.setOnClickListener {
            viewModel.apply {
                startMainScreen()
                setStatusPint(2)
            }
        }
        pinLockView.addPatternLockListener(object : PatternLockViewListener {
            override fun onStarted() {}
            override fun onProgress(progressPattern: MutableList<PatternLockView.Dot>?) {}
            override fun onCleared() {}

            override fun onComplete(pattern: MutableList<PatternLockView.Dot>?) {
                val patternLock = PatternLockUtils.patternToString(pinLockView, pattern)
                if (patternLock.length > 3) {
                    viewModel.enterScreen()
                    viewModel.setPinCode(patternLock)
                    viewModel.setStatusPint(1)
                } else {
                    pinLockView.clearPattern()
                }
            }
        })
        viewModel.openEnterPinFlow.onEach {
            findNavController().navigate(R.id.pinEnterScreen)
        }.launchIn(lifecycleScope)
        viewModel.openMainFlow.onEach {
            findNavController().navigate(R.id.mainScreen)
        }.launchIn(lifecycleScope)
    }
}