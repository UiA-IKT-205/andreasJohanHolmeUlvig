package com.example.handleliste

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handleliste.data.ListItem
import com.example.handleliste.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        val listOfItems = Datasource( this).getItemList()

        val recyclerView: RecyclerView = findViewById(R.id.ItemCycler)
        recyclerView.adapter = ItemAdapter(listOfItems)
    }
}