package com.cp.borutoapp.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cp.borutoapp.data.repository.BorutoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    borutoRepository: BorutoRepository
) : ViewModel() {

    var shouldSkipOnBoarding = MutableStateFlow(false)
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            shouldSkipOnBoarding.value =
                borutoRepository.readOnBoardingState().stateIn(viewModelScope).value
        }
    }
}