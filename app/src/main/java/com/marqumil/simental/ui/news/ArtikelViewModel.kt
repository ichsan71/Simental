package com.marqumil.simental.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marqumil.simental.remote.ResultState
import com.marqumil.simental.remote.response.ArticlesItem
import com.marqumil.simental.remote.response.NewsResponse
import com.marqumil.simental.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.HttpException



class ArtikelViewModel: ViewModel() {
    private val _ArtikelResponse = MutableLiveData<ResultState<List<ArticlesItem>>>()
    val ArtikelResponse: MutableLiveData<ResultState<List<ArticlesItem>>> = _ArtikelResponse

    private val _forceLogout = MutableLiveData(false)
    val forceLogout : LiveData<Boolean> = _forceLogout

    fun getArtikel() {
        _ArtikelResponse.value = ResultState.Loading
         viewModelScope.launch {
             try {
                 val response = ApiConfig.getApiService().getNews("7759fad0007544b1ae1daed0b0a3b1f2")
                 val artikels = response.articles
                 _ArtikelResponse.postValue(ResultState.Success(artikels))
             }
             catch (e: HttpException) {
                 if (e.code() == 401) {
                     _forceLogout.postValue(true)
                 }
             }
             catch (e : Exception) {
                 _ArtikelResponse.postValue(ResultState.Failure(e))
             }
         }
    }


}