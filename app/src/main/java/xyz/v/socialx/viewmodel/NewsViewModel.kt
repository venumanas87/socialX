package xyz.v.socialx.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import xyz.v.socialx.models.News
import xyz.v.socialx.network.RetrofitInstance
import xyz.v.socialx.utils.Resource

class NewsViewModel: ViewModel() {
    //e50630e12e244a4781090bf2e7a8054e
    val newsLiveData:MutableLiveData<Resource<News>> = MutableLiveData()

    fun getNews(){
        newsLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val rep = RetrofitInstance.api.fetchAll("apple")
            if(rep.isSuccessful){
                rep.body()?.let {
                    newsLiveData.postValue(Resource.Success(it))
                }
            }else{
                newsLiveData.postValue(Resource.Error(rep.message()))
            }
        }
    }


    fun searchNews(query:String){
        newsLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val rep = RetrofitInstance.api.fetchAll(query)
            if(rep.isSuccessful){
                rep.body()?.let {
                    newsLiveData.postValue(Resource.Success(it))
                }
            }else{
                newsLiveData.postValue(Resource.Error(rep.message()))
            }
        }
    }


}