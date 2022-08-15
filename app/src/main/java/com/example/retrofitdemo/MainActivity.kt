package com.example.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retService: AlbumService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)

        //path parameters example

        val pathResponse : LiveData<Response<AlbumsItem>> = liveData {
            val response = retService.getAlbum(5)
            emit(response)
        }

        pathResponse.observe(this, Observer {
            val title = it.body()?.title
            Toast.makeText(applicationContext,title,Toast.LENGTH_SHORT)
        })

        val  responseLiveData:LiveData<Response<Albums>> = liveData{
//            val response = retService.getAlbums()
            val response = retService.getSortedAlbums(3)

            emit(response)
        }
        responseLiveData.observe(this, Observer {
            val albumsList = it.body()?.listIterator()
            if (albumsList!=null){
                while (albumsList.hasNext()){
                    val albumsItem = albumsList.next()
                    Log.i("Taggg", "${albumsItem.title}")
                }
            }
        })
    }
}