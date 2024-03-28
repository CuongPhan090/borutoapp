package com.cp.borutoapp.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.cp.borutoapp.data.local.entities.toHero
import com.cp.borutoapp.data.repository.BorutoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val borutoRepository: BorutoRepository
) : ViewModel() {

    fun getAllHeroes() = borutoRepository.getAllHeroes().map { pagingData ->
        pagingData.map {
            it.toHero()
        }
    }.cachedIn(viewModelScope)
}