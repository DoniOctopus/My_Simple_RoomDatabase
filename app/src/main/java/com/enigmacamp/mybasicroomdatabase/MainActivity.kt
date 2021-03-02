package com.enigmacamp.mybasicroomdatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.enigmacamp.mybasicroomdatabase.room.Note
import com.enigmacamp.mybasicroomdatabase.room.NoteDB
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val db by lazy { NoteDB(this) }
    val activityMain = "MAINACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setupView()
        setupListener()
//        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
           val notes = db.noteDao().getNotes()
            Log.d(activityMain,"DbResponese : $notes")
        }
    }

    //Berpindah ke EditActivity
    private fun setupListener(){
        button_create.setOnClickListener {
            startActivity(Intent(this,EditActivity::class.java))
//            intentEdit(Constant.TYPE_CREATE, 0)
        }
    }

}