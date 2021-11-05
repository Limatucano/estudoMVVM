package correa.matheus.estudommvm.repositories

import correa.matheus.estudommvm.rest.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllLives() = retrofitService.getAllLives()
}