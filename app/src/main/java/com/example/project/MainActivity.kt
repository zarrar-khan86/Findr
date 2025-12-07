package com.example.project

import android.content.Intent // <-- Add this import statement
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var rvItemsFeed: RecyclerView
    private lateinit var fabAddItem: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide action bar
        supportActionBar?.hide()

        // Initialize views
        rvItemsFeed = findViewById(R.id.rvItemsFeed)
        fabAddItem = findViewById(R.id.fabAddItem)

        // Setup RecyclerView
        setupRecyclerView()

        // Setup FAB click listener
        fabAddItem.setOnClickListener {
            // Navigate to Report Item activity
            startActivity(Intent(this, ReportItemActivity::class.java))
        }

        // Notification bell click listener
        findViewById<android.view.View>(R.id.btnNotifications).setOnClickListener {
            // TODO: Navigate to notifications activity
            Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show()
        }

        // User avatar click listener
        findViewById<android.view.View>(R.id.ivUserAvatar).setOnClickListener {
            // TODO: Navigate to profile activity
            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        rvItemsFeed.layoutManager = LinearLayoutManager(this)
        // TODO: Set adapter when ItemFeedAdapter is created
        // rvItemsFeed.adapter = ItemFeedAdapter(getSampleItems())
    }
}
