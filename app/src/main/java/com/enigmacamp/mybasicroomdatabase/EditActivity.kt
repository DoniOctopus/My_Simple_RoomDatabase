package com.enigmacamp.mybasicroomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.enigmacamp.mybasicroomdatabase.room.Constant
import com.enigmacamp.mybasicroomdatabase.room.Note
import com.enigmacamp.mybasicroomdatabase.room.NoteDB
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    val db by lazy { NoteDB(this) }
    private var noteId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        setupLstener()
    }

    fun setupView() {
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {

            }
            Constant.TYPE_READ -> {
                button_save.visibility = View.GONE
                getNote()
            }
        }
    }

    private fun setupLstener() {
        button_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.noteDao().addNote(
                    //addNote yg kita butuhkan adalah id,title,dan notenya
                    //id disini karena sudah auto generate maka tidak harus diisi
                    Note(
                        0,
                        edit_nama.text.toString(),
                        edit_address1.text.toString(),
                        edit_address2.text.toString()
                    )
                )
                finish()
            }
        }
    }

    fun getNote() {
        noteId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.noteDao().getNote(noteId)[0]
            edit_nama.setText(notes.nama)
            edit_address1.setText(notes.address1)
            edit_address2.setText(notes.address2)
        }
    }
}