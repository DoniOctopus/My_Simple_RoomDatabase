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
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                button_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                button_save.visibility = View.GONE
                button_update.visibility = View.GONE
                edit_nama.isEnabled = false
                edit_umur.isEnabled = false
                edit_alamat.isEnabled = false
                getNote()
            }
            Constant.TYPE_UPDATE -> {
                button_save.visibility = View.GONE
                getNote()
            }
        }
    }

    private fun setupLstener() {
        button_save.setOnClickListener {
            var isEmptyFields = false
            when{
                edit_nama.text.isEmpty() ->{
                    isEmptyFields = true
                    edit_nama.error = "Field Ini tidak Boleh Kosong"
                }
                edit_umur.text.isEmpty() ->{
                    isEmptyFields = true
                    edit_umur.error = "Field Ini tidak Boleh Kosong"
                }
                edit_alamat.text.isEmpty() ->{
                    isEmptyFields = true
                    edit_alamat.error = "Field Ini tidak Boleh Kosong"
                }
            }
            if (!isEmptyFields){
               createdCustomer()
            }
        }
        button_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.noteDao().updateNote(
                        //addNote yg kita butuhkan adalah id,title,dan notenya
                        //id disini karena sudah auto generate maka tidak harus diisi
                        Note(
                                noteId,
                                edit_nama.text.toString(),
                                edit_umur.text.toString(),
                                edit_alamat.text.toString()
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
            edit_umur.setText(notes.umur)
            edit_alamat.setText(notes.alamat)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun createdCustomer(){
        CoroutineScope(Dispatchers.IO).launch {
            db.noteDao().addNote(
                    //addNote yg kita butuhkan adalah id,title,dan notenya
                    //id disini karena sudah auto generate maka tidak harus diisi
                    Note(
                            0,
                            edit_nama.text.toString(),
                            edit_umur.text.toString(),
                            edit_alamat.text.toString()
                    )
            )
            finish()
        }
    }
}