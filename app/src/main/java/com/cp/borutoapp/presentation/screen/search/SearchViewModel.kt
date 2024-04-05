package com.cp.borutoapp.presentation.screen.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.cp.borutoapp.data.remote.models.toHero
import com.cp.borutoapp.data.repository.BorutoRepository
import com.cp.borutoapp.presentation.model.Hero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val borutoRepository: BorutoRepository
) : ViewModel() {

    private var _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private var _searchHeroResults = MutableStateFlow<PagingData<Hero>>(PagingData.empty())
    val searchHeroResults = _searchHeroResults

    fun updateSearchQuery(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun searchHeroes(query: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
            borutoRepository.searchHeroes(query).cachedIn(viewModelScope).collect {
                _searchHeroResults.value = it.map { heroResponse ->
                    heroResponse.toHero()
                }
            }
        }
    }
}