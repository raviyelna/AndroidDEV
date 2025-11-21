package com.example.exercise3

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

class RecycleViewActivity : AppCompatActivity() {

    private lateinit var rvItems: RecyclerView
    private lateinit var includeContainer: FrameLayout
    private lateinit var edValue: EditText
    private lateinit var edDesc: EditText
    private lateinit var ivDetail: ImageView
    private lateinit var btAdd: Button
    private lateinit var btUpdate: Button
    private lateinit var btRemove: Button
    private lateinit var btBack: Button


    private lateinit var list: MutableList<MonHoc>
    private lateinit var baseAdapter: MonHocAdapter


    private lateinit var rvAdapter: RecyclerView.Adapter<*>

    private var selectedPosition: Int = -1
    private var isCreating: Boolean = false
    private var selectedImageResId: Int = R.drawable.activity_transparent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customadapterrecycleview)

        rvItems = findViewById(R.id.rv_items)
        includeContainer = findViewById(R.id.include_container)
        btAdd = findViewById(R.id.bt_add)
        btUpdate = findViewById(R.id.bt_update)
        btRemove = findViewById(R.id.bt_remove)
        btBack = findViewById(R.id.bt_back)


        layoutInflater.inflate(R.layout.layout_include_detail, includeContainer, true)
        edValue = includeContainer.findViewById(R.id.ed_value)
        edDesc = includeContainer.findViewById(R.id.ed_desc)
        ivDetail = includeContainer.findViewById(R.id.iv_detail)

        includeContainer.visibility = View.GONE


        rvItems.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvItems.setHasFixedSize(true)
        PagerSnapHelper().attachToRecyclerView(rvItems)


        list = ArrayList()
        list.add(MonHoc("Java", "Java", R.drawable.java))
        list.add(MonHoc("C#", "C# 1", R.drawable.cplusplus))
        list.add(MonHoc("PHP", "PHP 1", R.drawable.php))
        list.add(MonHoc("Kotlin", "Kotlin 1", R.drawable.kotlin))
        list.add(MonHoc("Dart", "Dart 1", R.drawable.dart))


        baseAdapter = MonHocAdapter(this, R.layout.row_monhoc, list)


        class RVAdapter(
            private val base: MonHocAdapter,
            private val onClick: (Int) -> Unit,
            private val onLongClick: (Int) -> Boolean
        ) : RecyclerView.Adapter<RVAdapter.VH>() {

            inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
                val ivPic: ImageView? = itemView.findViewById(R.id.iv_pic)
                val tvName: TextView? = itemView.findViewById(R.id.tv_name)
                val tvDesc: TextView? = itemView.findViewById(R.id.tv_desc)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
                val inflater = LayoutInflater.from(base.getContext())
                val view = inflater.inflate(base.getLayoutl(), parent, false)
                return VH(view)
            }

            override fun getItemCount(): Int = base.getCount()

            override fun onBindViewHolder(holder: VH, position: Int) {
                val data = base.getMonHocList().get(position)
                holder.tvName?.text = data.getName()
                holder.tvDesc?.text = data.getDesc()
                holder.ivPic?.setImageResource(data.getPic())

                holder.itemView.setOnClickListener { onClick(position) }
                holder.itemView.setOnLongClickListener {
                    onLongClick(position)
                }
            }
        }


        rvAdapter = RVAdapter(baseAdapter,
            onClick = { position ->
                if (position >= 0 && position < list.size) {
                    val item = list[position]
                    selectedPosition = position
                    isCreating = false
                    btAdd.text = "Add"

                    includeContainer.visibility = View.VISIBLE
                    edValue.setText(item.getName())
                    edDesc.setText(item.getDesc())
                    selectedImageResId = item.getPic()
                    ivDetail.setImageResource(selectedImageResId)

                    edValue.requestFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(edValue, InputMethodManager.SHOW_IMPLICIT)

                    rvItems.post { rvItems.smoothScrollToPosition(position) }
                    Toast.makeText(this@RecycleViewActivity, "Position: $position - ${item.getName()}", Toast.LENGTH_SHORT).show()
                }
            },
            onLongClick = { position ->
                if (position >= 0 && position < list.size) {
                    val item = list[position]
                    AlertDialog.Builder(this@RecycleViewActivity)
                        .setTitle("Delete")
                        .setMessage("Do you want to delete ${item.getName()}?")
                        .setPositiveButton("Delete") { _, _ ->

                            list.removeAt(position)
                            (rvAdapter as RecyclerView.Adapter<*>).notifyItemRemoved(position)
                            if (selectedPosition == position) {
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
            })

        rvItems.adapter = rvAdapter


        ivDetail.setOnClickListener(null)



        btAdd.setOnClickListener {
            if (!isCreating) {
                isCreating = true
                selectedPosition = -1
                includeContainer.visibility = View.VISIBLE
                edValue.text.clear()
                edDesc.text.clear()
                selectedImageResId = R.drawable.activity_transparent
                ivDetail.setImageResource(selectedImageResId)
                edValue.requestFocus()
                btAdd.text = "Save"
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
                list.add(newItem)
                (rvAdapter as RecyclerView.Adapter<*>).notifyItemInserted(list.size - 1)
                isCreating = false
                btAdd.text = "Add"
                includeContainer.visibility = View.GONE
                edValue.text.clear()
                edDesc.text.clear()
                selectedImageResId = R.drawable.activity_transparent
                Toast.makeText(this, "Added \"$name\"", Toast.LENGTH_SHORT).show()
                rvItems.smoothScrollToPosition(list.size - 1)
            }
        }


        btUpdate.setOnClickListener {
            val text = edValue.text.toString().trim()
            val descText = edDesc.text.toString().trim()
            if (selectedPosition < 0 || selectedPosition >= list.size) {
                Toast.makeText(this, "Select an item to update", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (text.isEmpty()) {
                Toast.makeText(this, "Enter a new name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val item = list[selectedPosition]
            item.setName(text)
            item.setDesc(descText)
            item.setPic(selectedImageResId)
            (rvAdapter as RecyclerView.Adapter<*>).notifyItemChanged(selectedPosition)
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
        }


        btRemove.setOnClickListener {
            if (selectedPosition < 0 || selectedPosition >= list.size) {
                Toast.makeText(this, "Select an item to delete", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val item = list[selectedPosition]
            AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you want to delete ${item.getName()}?")
                .setPositiveButton("Delete") { _, _ ->
                    list.removeAt(selectedPosition)
                    (rvAdapter as RecyclerView.Adapter<*>).notifyItemRemoved(selectedPosition)
                    selectedPosition = -1
                    edValue.text.clear()
                    edDesc.text.clear()
                    includeContainer.visibility = View.GONE
                }
                .setNegativeButton("Cancel", null)
                .show()
        }


        btBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}