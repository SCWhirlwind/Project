package com.example.projectmobile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.twitch_page.*

private lateinit var dataResults: RecyclerView
private lateinit var dataAdapter: DataAdapter
private lateinit var dataResultsLayoutManager: GridLayoutManager

class HistoryPage : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_page)

        backButton.setOnClickListener(this)
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