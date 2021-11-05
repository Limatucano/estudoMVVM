package correa.matheus.estudommvm.viewmodel.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import correa.matheus.estudommvm.models.Live
import correa.matheus.estudommvm.repositories.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository : MainRepository) : ViewModel() {

    val liveList = MutableLiveData<List<Live>>()
    val errorMessage = MutableLiveData<String>()
    fun getAllLives(){
        val requestAllLives = repository.getAllLives()

        requestAllLives.enqueue(
            object : Callback<List<Live>>{
                override fun onResponse(call: Call<List<Live>>, response: Response<List<Live>>) {

                    if(response.isSuccessful){
                        liveList.postValue(response.body())
                        return
                    }
                    errorMessage.postValue(response.message())
                }

                override fun onFailure(call: Call<List<Live>>, t: Throwable) {
                    errorMessage.postValue(t.message)
                }

            }
        )
    }
}