package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rclNames = findViewById<RecyclerView>(R.id.recycle)
        // If size of the all items are equal and won't change for a better performance it's better to set setHasFixedSize to truetter to set setHasFixedSize to true

        rclNames.setHasFixedSize(true)
        // Creating an instance of our NameAdapter class and setting it to our RecyclerView
        val nameList = getListOfNames()
        val namesAdapter = NameAdapter(nameList)
        rclNames.adapter = namesAdapter

        // Setting our RecyclerView's layout manager equal to LinearLayoutManager
        rclNames.layoutManager = LinearLayoutManager(this)
        btnUpdateList.setOnClickListener {
            // Show input dialog to add new name
            val inputDialog = LayoutInflater.from(this).inflate(R.layout.input_dialog, null)
            val alertDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
            alertDialogBuilder.setView(inputDialog)

            alertDialogBuilder.setCancelable(false)
            alertDialogBuilder.setPositiveButton("Add") { _, _ ->
                val name =
                    inputDialog.findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.newName).text.toString()
                if (name.isNotEmpty()) {
                    namesAdapter.addItem(nameList.size, name)
                }
            }
            alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

            alertDialogBuilder.create().show()
        }

        val swipeGesture = object : SimpleGesture(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        namesAdapter.deleteItem(viewHolder.adapterPosition)
                    }
                    ItemTouchHelper.RIGHT -> {
                        val archivedName = nameList[viewHolder.adapterPosition]
                        namesAdapter.deleteItem(viewHolder.adapterPosition)
                        namesAdapter.addItem(nameList.size, archivedName)
                    }
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeGesture)
        itemTouchHelper.attachToRecyclerView(rclNames)
    }

    // This function just creates a list of names for us
    private fun getListOfNames(): MutableList<String> {
        val nameList = mutableListOf<String>()
        nameList.add("ahmed")
        nameList.add("yassine")
        nameList.add("mariem")
        nameList.add("oussema")
        nameList.add("fatma")
        nameList.add("haythem")
        nameList.add("ayoub")
        nameList.add("houda")
        return nameList
    }


}