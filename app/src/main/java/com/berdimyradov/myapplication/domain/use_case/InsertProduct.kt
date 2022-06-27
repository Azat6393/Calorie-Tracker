package com.berdimyradov.myapplication.domain.use_case

import com.berdimyradov.myapplication.domain.model.Item
import com.berdimyradov.myapplication.domain.repository.CalorieRepository
import com.berdimyradov.myapplication.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import java.lang.Exception
import javax.inject.Inject

class InsertProduct @Inject constructor(
    private val repo: CalorieRepository
) {
    suspend operator fun invoke(item: Item): Flow<Resource<String>> = flow {
        try {
            val oldItems = repo.getAllItems().size
            repo.addItem(item)
            val newItems = repo.getAllItems().size
            println(repo.getAllItems())
            if (newItems > oldItems){
                emit(Resource.Success<String>("Product added"))
            } else {
                emit(Resource.Error<String>("Product didn't added"))
            }
        }catch (e: Exception){
            emit(Resource.Error<String>(e.localizedMessage ?: "Product didn't added"))
        }
    }
}