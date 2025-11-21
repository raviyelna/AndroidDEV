package com.example.exercise3

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ListViewActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var includeContainer: FrameLayout
    private lateinit var edValue: EditText
    private lateinit var edDesc: EditText
    private lateinit var ivDetail: ImageView
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnRemove: Button
    private lateinit var arrayList: ArrayList<MonHoc>
    private lateinit var adapter: MonHocAdapter
    private var selectedPosition: Int = -1
    private var isCreating: Boolean = false
    private var selectedImageResId: Int = R.drawable.activity_transparent
    private lateinit var headerView: View
    private lateinit var tvNameHeader: TextView
    private lateinit var ivPicHeader: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_customadapterlistview)

        listView = findViewById(R.id.lv)
        includeContainer = findViewById(R.id.include_container)
        layoutInflater.inflate(R.layout.layout_include_detail, includeContainer, true)
        edValue = includeContainer.findViewById(R.id.ed_value)
        edDesc = includeContainer.findViewById(R.id.ed_desc)
        ivDetail = includeContainer.findViewById(R.id.iv_detail)
        btnAdd = findViewById(R.id.bt_add)
        btnUpdate = findViewById(R.id.bt_update)
        btnRemove = findViewById(R.id.bt_remove)

        includeContainer.visibility = View.GONE

        arrayList = ArrayList()
        arrayList.add(MonHoc("Java", "Java", R.drawable.java))
        arrayList.add(MonHoc("C#", "C# 1", R.drawable.cplusplus))
        arrayList.add(MonHoc("PHP", "PHP 1", R.drawable.php))
        arrayList.add(MonHoc("Kotlin", "Kotlin 1", R.drawable.kotlin))
        arrayList.add(MonHoc("Dart", "Dart 1", R.drawable.dart))

        adapter = MonHocAdapter(this@ListViewActivity, R.layout.row_monhoc, arrayList as List<MonHoc>)

        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val realPos = position - listView.headerViewsCount
            if (realPos >= 0 && realPos < arrayList.size) {
                val item = arrayList[realPos]
                selectedPosition = realPos
                tvNameHeader.text = item.getName()
                ivPicHeader.setImageResource(item.getPic())
                includeContainer.visibility = View.VISIBLE
                edValue.setText(item.getName())
                edDesc.setText(item.getDesc())
                selectedImageResId = item.getPic()
                ivDetail.setImageResource(selectedImageResId)
                edValue.requestFocus()
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(edValue, InputMethodManager.SHOW_IMPLICIT)
                listView.post {
                    val headerHeight = headerView.height
                    listView.smoothScrollToPositionFromTop(realPos + listView.headerViewsCount, headerHeight)
                }
            }
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            val realPos = position - listView.headerViewsCount
            if (realPos >= 0 && realPos < arrayList.size) {
                val item = arrayList[realPos]
                AlertDialog.Builder(this@ListViewActivity)
                    .setTitle("Delete")
                    .setMessage("Do you want to delete ${item.getName()}?")
                    .setPositiveButton("Delete") { _, _ ->
                        arrayList.removeAt(realPos)
                        adapter.notifyDataSetChanged()
                        if (selectedPosition == realPos) {
                            selectedPosition = -1
                            includeContainer.visibility = View.GONE
                        }
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
                true
            } else {
                false
            }
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val realPos = position - listView.headerViewsCount
            if (realPos >= 0 && realPos < arrayList.size) {
                val item = arrayList[realPos]
                selectedPosition = realPos
                isCreating = false
                btnAdd.text = "Add"

                includeContainer.visibility = View.VISIBLE
                edValue.setText(item.getName())
                edDesc.setText(item.getDesc())
                selectedImageResId = item.getPic()
                ivDetail.setImageResource(selectedImageResId)

                edValue.requestFocus()
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(edValue, InputMethodManager.SHOW_IMPLICIT)

                Toast.makeText(
                    this@ListViewActivity,
                    "Position: $realPos - ${item.getName()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        ivDetail.setOnClickListener {
            val imageNames = arrayOf("Java", "C#", "PHP", "Kotlin", "Dart", "None")
            val imageRes = intArrayOf(
                R.drawable.java,
                R.drawable.cplusplus,
                R.drawable.php,
                R.drawable.kotlin,
                R.drawable.dart,
                R.drawable.activity_transparent
            )
            AlertDialog.Builder(this)
                .setTitle("Select image")
                .setItems(imageNames) { _, which ->
                    selectedImageResId = imageRes[which]
                    ivDetail.setImageResource(selectedImageResId)
                }
                .show()
        }

        btnAdd.setOnClickListener {
            if (!isCreating) {
                isCreating = true
                selectedPosition = -1
                includeContainer.visibility = View.VISIBLE
                edValue.text.clear()
                edDesc.text.clear()
                selectedImageResId = R.drawable.activity_transparent
                ivDetail.setImageResource(selectedImageResId)
                edValue.requestFocus()
                btnAdd.text = "Save"
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(edValue, InputMethodManager.SHOW_IMPLICIT)
            } else {
                val name = edValue.text.toString().trim()
                val desc = edDesc.text.toString().trim()
                if (name.isEmpty()) {
                    Toast.makeText(this, "Please enter a name before adding", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val newItem = MonHoc(name, desc, selectedImageResId)
                arrayList.add(newItem)
                adapter.notifyDataSetChanged()
                isCreating = false
                btnAdd.text = "Add"
                includeContainer.visibility = View.GONE
                edValue.text.clear()
                edDesc.text.clear()
                selectedImageResId = R.drawable.activity_transparent
                Toast.makeText(this, "Added \"$name\"", Toast.LENGTH_SHORT).show()
                val pos = arrayList.size - 1
                listView.smoothScrollToPosition(pos + listView.headerViewsCount)
            }
        }

        btnUpdate.setOnClickListener {
            val text = edValue.text.toString().trim()
            val descText = edDesc.text.toString().trim()
            if (selectedPosition < 0 || selectedPosition >= arrayList.size) {
                Toast.makeText(this, "Select an item to update", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (text.isEmpty()) {
                Toast.makeText(this, "Enter a new name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val item = arrayList[selectedPosition]
            item.setName(text)
            item.setDesc(descText)
            item.setPic(selectedImageResId)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
        }

        btnRemove.setOnClickListener {
            if (selectedPosition < 0 || selectedPosition >= arrayList.size) {
                Toast.makeText(this, "Select an item to delete", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val item = arrayList[selectedPosition]
            AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you want to delete ${item.getName()}?")
                .setPositiveButton("Delete") { _, _ ->
                    arrayList.removeAt(selectedPosition)
                    adapter.notifyDataSetChanged()
                    selectedPosition = -1
                    edValue.text.clear()
                    edDesc.text.clear()
                    includeContainer.visibility = View.GONE
                }
                .setNegativeButton("Cancel", null)
                .show()
        }


        val bt_back = findViewById<Button>(R.id.bt_back)
        bt_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}