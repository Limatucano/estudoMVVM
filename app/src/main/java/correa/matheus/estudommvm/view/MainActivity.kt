package correa.matheus.estudommvm.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import correa.matheus.estudommvm.R
import correa.matheus.estudommvm.databinding.ActivityMainBinding
import correa.matheus.estudommvm.repositories.MainRepository
import correa.matheus.estudommvm.rest.RetrofitService
import correa.matheus.estudommvm.view.adapters.MainAdapter
import correa.matheus.estudommvm.viewmodel.main.MainViewModel
import correa.matheus.estudommvm.viewmodel.main.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivityMainBinding
    lateinit var viewModel : MainViewModel

    private val retrofitService = RetrofitService.getInstance()
    private val adapter = MainAdapter{
        openLink(it.link)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewModel = ViewModelProvider(this,MainViewModelFactory(MainRepository(retrofitService))).get(MainViewModel::class.java)

        viewBinding.recyclerview.adapter = adapter

    }

    override fun onStart() {
        super.onStart()

        viewModel.liveList.observe(this, Observer { lives ->
            adapter.setLiveList(lives)
        })

        viewModel.errorMessage.observe(this, Observer { error ->
            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
        })
    }

    override fun onResume() {
        super.onResume()

        viewModel.getAllLives()
    }

    private fun openLink(link:String){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }
}