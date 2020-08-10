package com.example.projectmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.twitch_page.*

const val BUSINESS_BACKDROP = "extra_business_backdrop"
const val DATA_GAME_ID = "extra_data_game_id"

class DataDetailsPage : AppCompatActivity(), View.OnClickListener {
    private lateinit var dataXResults: RecyclerView
    private lateinit var dataXAdapter: StreamAdapter
    private lateinit var dataXResultsLayoutManager: GridLayoutManager
    private lateinit var backdrop: ImageView
    private lateinit var game_Id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stream_page)

        backButton.setOnClickListener(this)

        backdrop = findViewById(R.id.business_backdrop)
        dataXResults = findViewById(R.id.recyclerStreamList)
        dataXResultsLayoutManager = GridLayoutManager(this, 4)

        dataXResults.layoutManager = dataXResultsLayoutManager
        dataXAdapter = StreamAdapter(mutableListOf())
        dataXResults.adapter = dataXAdapter

        val extras = intent.extras

        if(extras != null) {
            businessDetails(extras)
            gameId(extras)
            getStreamResults()
        }
        else {
            finish()
        }
    }

    private fun getStreamResults() {
        DataRepository.getStreamResults(
            game_Id,
            ::onSuccess,
            ::onError
        )
    }

    private fun onSuccess(streams: List<Stream>) {
        dataXAdapter.appendStreams(streams)
        attachStreamOnScrollListener()
    }

    private fun onError() {
        Toast.makeText(this, "Check Internet", Toast.LENGTH_SHORT).show()
    }

    private fun attachStreamOnScrollListener() {
        dataXResults.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = dataXResultsLayoutManager.itemCount

                val visibleItemCount =  dataXResultsLayoutManager.findLastVisibleItemPosition() - dataXResultsLayoutManager.findFirstVisibleItemPosition()

                val firstVisibleItem = dataXResultsLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    Log.d("MainActivity:vitemCount", "${visibleItemCount}")
                    Log.d("MainActivity:itemCount", "${totalItemCount}")

                    dataXResults.removeOnScrollListener(this)
                    getStreamResults()
                }
            }
        })
    }

    private fun gameId(extras: Bundle) {
        game_Id = extras.getString(DATA_GAME_ID).toString()
    }

    private fun businessDetails(extras: Bundle) {
        extras.getString(BUSINESS_BACKDROP)?.let { imageURL ->
            Glide.with(this)
                .load(imageURL)
                .transform(CenterCrop())
                .into(backdrop)
        }

    }

    override fun onClick(p0: View?) {
        when (p0)
        {
            backButton -> {
                finish()
            }
        }
    }
}