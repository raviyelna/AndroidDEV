package com.example.bt4

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    lateinit var databaseHandler: DatabaseHandler
    lateinit var listView: ListView
    lateinit var adapter: NotesAdapter
    lateinit var arrayList: ArrayList<NotesModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.menuAddNotes)
        setSupportActionBar(toolbar)


        listView = findViewById(R.id.listview)
        arrayList = ArrayList()
        adapter = NotesAdapter(this, arrayList, R.layout.row_notes)
        listView.adapter = adapter

        InitDatabaseSQLite()

        // createDatabaseSQLite()

        databaseSQLite()
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuAddNotes) {
            DialogThem()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun DialogThem() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)


        dialog.setContentView(R.layout.edit_note)

        val editTextName = dialog.findViewById<EditText>(R.id.editTextName)
        val buttonAdd = dialog.findViewById<Button>(R.id.buttonUpdate) // Reusing button ID
        val buttonCancel = dialog.findViewById<Button>(R.id.buttonCancel)

        buttonAdd.text = "Add"

        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
            } else {
                databaseHandler.queryData("INSERT INTO Notes VALUES(null, '$name')")
                Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                databaseSQLite()
            }
        }

        buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    fun createDatabaseSQLite(){
        databaseHandler.queryData("INSERT INTO Notes VALUES(null, 'Note SQLite')")
        databaseHandler.queryData("INSERT INTO Notes VALUES(null, 'Note SQLite 2')")
    }

    private fun InitDatabaseSQLite(){
        databaseHandler = DatabaseHandler(this, "notes.sqlite", null, 1)
        databaseHandler.queryData("CREATE TABLE IF NOT EXISTS Notes(Id INTEGER PRIMARY KEY AUTOINCREMENT, NameNote VARCHAR(200))")
    }

    private fun databaseSQLite(){
        arrayList.clear()
        val cursor: Cursor = databaseHandler.getData("SELECT * FROM Notes")
        while (cursor.moveToNext()){
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            arrayList.add(NotesModel(id, name))
        }
        adapter.notifyDataSetChanged()
    }


    fun showDialogUpdate(name: String, id: Int) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.edit_note)

        val editTextName = dialog.findViewById<EditText>(R.id.editTextName)
        val buttonUpdate = dialog.findViewById<Button>(R.id.buttonUpdate)
        val buttonCancel = dialog.findViewById<Button>(R.id.buttonCancel)

        editTextName.setText(name)
        buttonUpdate.text = "Confirm"

        buttonUpdate.setOnClickListener {
            val newName = editTextName.text.toString().trim()
            if (newName.isEmpty()) {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
            } else {
                databaseHandler.queryData("UPDATE Notes SET NameNote = '$newName' WHERE Id = '$id'")
                Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                databaseSQLite()
            }
        }

        buttonCancel?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun showDialogDelete(name: String, id: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete '$name'?")
        builder.setPositiveButton("Yes") { _, _ ->
            databaseHandler.queryData("DELETE FROM Notes WHERE Id = '$id'")
            Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show()
            databaseSQLite()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}

class DatabaseHandler(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    fun queryData(sql: String) {
        val database = writableDatabase
        database.execSQL(sql)
    }

    fun getData(sql: String): Cursor {
        val database = readableDatabase
        return database.rawQuery(sql, null)
    }

    override fun onCreate(db: SQLiteDatabase) { }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) { }
}

class NotesModel(var IdNote: Int, var NameNote: String) : Serializable

class NotesAdapter(
    private var context: Context,
    private var arrayList: ArrayList<NotesModel>,
    private var layout: Int
) : BaseAdapter() {

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Any {
        return arrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private class ViewHolder {
        lateinit var textView: TextView
        lateinit var imageViewEdit: ImageView
        lateinit var imageViewDelete: ImageView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            holder = ViewHolder()
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(layout, null)

            holder.textView = view.findViewById(R.id.textView)
            holder.imageViewEdit = view.findViewById(R.id.imageViewEdit)
            holder.imageViewDelete = view.findViewById(R.id.imageViewDelete)

            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val note = arrayList[position]
        holder.textView.text = note.NameNote


        // Handle Edit Click
        holder.imageViewEdit.setOnClickListener {
            if(context is MainActivity){
                (context as MainActivity).showDialogUpdate(note.NameNote, note.IdNote)
            }
        }

        // Handle Delete Click
        holder.imageViewDelete.setOnClickListener {
            if(context is MainActivity){
                (context as MainActivity).showDialogDelete(note.NameNote, note.IdNote)
            }
        }

        return view
    }
}
