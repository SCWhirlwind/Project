package com.example.projectmobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_game.*
import kotlinx.android.synthetic.main.twitch_page.*
import kotlinx.android.synthetic.main.twitch_page.backButton


class TwitchPage : AppCompatActivity(), View.OnClickListener {
    private lateinit var dataResults: RecyclerView
    private lateinit var dataAdapter: DataAdapter
    private lateinit var dataResultsLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.twitch_page)

        backButton.setOnClickListener(this)

        dataResults = findViewById(R.id.recyclerBusinessesList)
        dataResultsLayoutManager = GridLayoutManager(this, 4)

        dataResults.layoutManager = dataResultsLayoutManager

        val mySnapshot = ArrayList<LikeGames>()

        dataAdapter = DataAdapter(mutableListOf(),  { data -> showDataDetails(data)}, mySnapshot)
        dataResults.adapter = dataAdapter

        btnSubmitSearch.setOnClickListener {
            getDataResults()
        }
    }

    private fun showDataDetails (data : Data) {
        val intent = Intent(this, DataDetailsPage::class.java)
        intent.putExtra("extra_business_backdrop", data.boxArtUrl.replace("{height}", "200").replace("{width}", "200"))
        intent.putExtra("extra_data_game_id", data.id)
        startActivity(intent)
    }

    private fun getDataResults() {
        DataRepository.getGameResults(
            ::onSuccess,
            ::onError
        )
    }

    private fun onSuccess(datas: List<Data>) {
        dataAdapter.appendDatas(datas)
        attachDataOnScrollListener()
    }

    private fun onError() {
        Toast.makeText(this, "Check Internet", Toast.LENGTH_SHORT).show()
    }

    private fun attachDataOnScrollListener() {
        dataResults.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = dataResultsLayoutManager.itemCount

                val visibleItemCount =  dataResultsLayoutManager.findLastVisibleItemPosition() - dataResultsLayoutManager.findFirstVisibleItemPosition()

                val firstVisibleItem = dataResultsLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    Log.d("MainActivity:vitemCount", "${visibleItemCount}")
                    Log.d("MainActivity:itemCount", "${totalItemCount}")

                    dataResults.removeOnScrollListener(this)
                    getDataResults()
                }
            }
        })
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