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
    val db by lazy { NoteDB (this) }
    private var noteId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        setupLstener()
        noteId = intent.getIntExtra("intent_id",0)
        Toast.makeText(this,noteId.toString(), Toast.LENGTH_SHORT).show()
    }

    fun setupView(){
        val intentType = intent.getIntExtra("intent_type",0)
        when(intentType){
            Constant.TYPE_CREATE ->{

            }
            Constant.TYPE_READ ->{
                button_save.visibility = View.GONE
            }
        }
    }

    private fun setupLstener() {
         button_save.setOnClickListener{
             CoroutineScope(Dispatchers.IO).launch {
                 db.noteDao().addNote(
                     //addNote yg kita butuhkan adalah id,title,dan notenya
                     //id disini karena sudah auto generate maka tidak harus diisi
                     Note(0,edit_nama.text.toString(),edit_umur.text.toString(),edit_alamat.text.toString())
                 )
                 finish()
             }
         }
    }
}