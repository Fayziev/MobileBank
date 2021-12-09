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
import uz.gita.bank2.databinding.ScreenPinEnterBinding
import uz.gita.bank2.presentation.viewmodel.PinLockViewModel
import uz.gita.bank2.presentation.viewmodel.impl.PinLockViewModelImpl
import uz.gita.bank2.utils.scope
import uz.gita.bank2.utils.timber


@AndroidEntryPoint
class PinEnterScreen : Fragment(R.layout.screen_pin_enter) {
    private val binding by viewBinding(ScreenPinEnterBinding::bind)
    private val viewModel: PinLockViewModel by viewModels<PinLockViewModelImpl>()
    private var code = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        viewModel.getPin()
        viewModel.pinCodeFlow.onEach {
            code = it
        }.launchIn(lifecycleScope)
        pinLockView.addPatternLockListener(object : PatternLockViewListener {
            override fun onStarted() {}
            override fun onProgress(progressPattern: MutableList<PatternLockView.Dot>?) {}
            override fun onCleared() {}
            override fun onComplete(pattern: MutableList<PatternLockView.Dot>?) {
                if (code == PatternLockUtils.patternToString(pinLockView, pattern)) {
                    timber(code, "UUU")
                    findNavController().navigate(R.id.mainScreen)
                } else {
                    pinLockView.clearPattern()
                }
            }
        })

    }

}