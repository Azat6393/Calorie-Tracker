package com.berdimyradov.myapplication.domain.use_case

import com.berdimyradov.myapplication.domain.model.CalorieResponse
import com.berdimyradov.myapplication.domain.repository.CalorieRepository
import com.berdimyradov.myapplication.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchProduct @Inject constructor(
    private val repo: CalorieRepository
) {
    operator fun invoke(query: String): Flow<Resource<CalorieResponse>> = flow {
        try {
            emit(Resource.Loading<CalorieResponse>())
            val response = repo.searchForProduct(query = query)
            emit(Resource.Success<CalorieResponse>(response))
        } catch (e: IOException) {
            emit(Resource.Error<CalorieResponse>(e.localizedMessage ?: "Something went wrong"))
        } catch (e: HttpException) {
            emit(Resource.Error<CalorieResponse>(e.localizedMessage ?: "Something went wrong"))
        }
    }
}