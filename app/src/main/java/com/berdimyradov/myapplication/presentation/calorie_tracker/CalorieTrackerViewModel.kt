package com.berdimyradov.myapplication.presentation.calorie_tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berdimyradov.myapplication.domain.model.CalorieResponse
import com.berdimyradov.myapplication.domain.model.Item
import com.berdimyradov.myapplication.domain.use_case.SearchProduct
import com.berdimyradov.myapplication.domain.use_case.UseCases
import com.berdimyradov.myapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalorieTrackerViewModel @Inject constructor(
    private val useCases: UseCases,
) : ViewModel() {

    init {
        getProducts()
    }

    private var searchJob: Job? = null

    private val _productInfo = MutableStateFlow<Resource<CalorieResponse>>(Resource.Empty())
    val productInfo = _productInfo.asStateFlow()

    private val _productList = MutableStateFlow<List<Item>>(emptyList())
    val productList = _productList.asStateFlow()

    private val _addedItemResponse = MutableStateFlow<Resource<String>>(Resource.Empty())
    val addedItemResponse = _addedItemResponse.asStateFlow()

    fun searchProduct(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            useCases.searchProduct(query = query).onEach {
                _productInfo.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun insertItem(item: Item) = viewModelScope.launch {
        useCases.insertProduct(item).onEach { result ->
            _addedItemResponse.value = result
            getProducts()
        }.launchIn(viewModelScope)
    }

    fun deleteItem(item: Item) = viewModelScope.launch {
        useCases.deleteProduct(item)
        getProducts()
    }

    private fun getProducts() = viewModelScope.launch {
        useCases.getProducts().onEach {
            _productList.value = it
        }.launchIn(viewModelScope)
    }
}