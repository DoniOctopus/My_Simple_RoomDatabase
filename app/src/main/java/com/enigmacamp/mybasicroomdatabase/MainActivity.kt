package com.enigmacamp.mybasicroomdatabase

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.enigmacamp.mybasicroomdatabase.room.Constant
import com.enigmacamp.mybasicroomdatabase.room.Note
import com.enigmacamp.mybasicroomdatabase.room.NoteDB
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val db by lazy { NoteDB(this) }
    val activityMain = "MAINACTIVITY"
    lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setupView()
        setupListener()
        setupRecyclerView()
    }


    override fun onStart() {
        super.onStart()
        loadData()
    }

    fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.noteDao().getNotes()
            Log.d(activityMain,"DbResponese : $notes")
            withContext(Dispatchers.Main){
                noteAdapter.setData(notes)
            }
        }
    }

    //Berpindah ke EditActivity
    private fun setupListener(){
        button_create.setOnClickListener {
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter(arrayListOf(),object : NoteAdapter.OnAdapterListener{
            override fun onClick(note: Note) {
                //read detail note
                intentEdit(note.id,Constant.TYPE_READ)
            }
            override fun onUpdate(note: Note) {
                intentEdit(note.id,Constant.TYPE_UPDATE)
            }

            override fun onDelete(note: Note) {
                deletedDialog(note)
            }

        })
        list_note.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = noteAdapter
        }
    }

    fun intentEdit(noteId : Int, intentType : Int){
        startActivity(Intent(
            applicationContext,EditActivity::class.java)
            .putExtra("intent_id", noteId)
            .putExtra("intent_type", intentType)

        )
    }

    private fun deletedDialog(note: Note){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin Mau Menghapus ${note.nama}?")
            setNegativeButton("Batal") { dialogInterface, i ->
            dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.noteDao().deleteNote(note)
                    loadData()
                }
            }
        }
        alertDialog.show()
    }
}
