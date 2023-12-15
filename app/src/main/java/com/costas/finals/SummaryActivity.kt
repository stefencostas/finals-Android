package com.costas.finals

import android.os.Bundle
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.costas.finals.adapters.SummaryAdapter
import com.costas.finals.constants.Constants.SHARED_PREFERENCES_NAME
import com.costas.finals.models.ProfileDetails

class SummaryActivity : AppCompatActivity() {

    private lateinit var summaryListView: ListView
    private lateinit var summaryAdapter: SummaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        title = "Acquired Items"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        summaryListView = findViewById(R.id.summaryListView)

        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
        val itemList = ProfileDetails.getListFromSharedPreferences(sharedPreferences)

        // Filter acquired items
        val acquiredItems = itemList.filter { it.acquired }

        // Sort items by category and item name
        val sortedItems = acquiredItems.sortedWith(compareBy({ it.category }, { it.itemName }))

        summaryAdapter = SummaryAdapter(this, sortedItems)

        summaryListView.adapter = summaryAdapter

        summaryListView.onItemClickListener = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
