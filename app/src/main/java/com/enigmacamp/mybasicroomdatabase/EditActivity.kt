package com.enigmacamp.mybasicroomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.enigmacamp.mybasicroomdatabase.room.Note
import com.enigmacamp.mybasicroomdatabase.room.NoteDB
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    val db by lazy { NoteDB (this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupLstener()
    }

    private fun setupLstener() {
         button_save.setOnClickListener{
             CoroutineScope(Dispatchers.IO).launch {
                 db.noteDao().addNote(
                     //addNote yg kita butuhkan adalah id,title,dan notenya
                     //id disini karena sudah auto generate maka tidak harus diisi
                     Note(0,edit_title.text.toString(),edit_note.text.toString())
                 )
                 finish()
             }
         }
    }
}