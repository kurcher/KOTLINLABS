package com.example.kotlin8
import android.graphics.Typeface
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuCompat
import androidx.core.view.children
import androidx.core.view.get
import androidx.core.view.size
import android.widget.Toast
import androidx.appcompat.view.ActionMode
import android.graphics.Color
import android.util.TypedValue

data class MenuItemData(val id: Int, val title: String, val iconRes: Int)

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var myTextView: TextView
    private lateinit var listView: ListView
    private lateinit var adapter: MyListAdapter

    // динамічний список для Options Menu
    private var optionsData = mutableListOf(
        MenuItemData(1, "Опція 1", android.R.drawable.ic_menu_add),
        MenuItemData(2, "Опція 2", android.R.drawable.ic_menu_call),
        MenuItemData(3, "Опція 3", android.R.drawable.ic_menu_camera),
        MenuItemData(4, "Опція 4", android.R.drawable.ic_menu_compass),
        MenuItemData(5, "Опція 5", android.R.drawable.ic_menu_crop)
    )

    private var currentActionMode: ActionMode? = null
    private var actionPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        myTextView = findViewById(R.id.myTextView)
        listView = findViewById(R.id.myListView)

        // register context menu for TextView
        registerForContextMenu(myTextView)

        // List data
        val items = MutableList(10) { index -> "Елемент ${index + 1}" }
        adapter = MyListAdapter(this, items)
        listView.adapter = adapter

        // Long click on list item starts Contextual Action Mode (CAB)
        listView.setOnItemLongClickListener { parent, view, position, id ->
            if (currentActionMode != null) return@setOnItemLongClickListener true
            actionPosition = position
            currentActionMode = startSupportActionMode(actionModeCallback)
            // highlight selected
            adapter.setSelected(position, true)
            true
        }
    }

    // Create Options Menu from optionsData
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        optionsData.forEachIndexed { index, it ->
            val item = menu?.add(Menu.NONE, it.id, index, it.title)
            item?.setIcon(it.iconRes)
            item?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        }
        // add Reorder item (will show popup)
        val reorderId = 9999
        val reorder = menu?.add(Menu.NONE, reorderId, optionsData.size, "Reorder")
        reorder?.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        return true
    }

    // Handle Options Menu clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            9999 -> { // Reorder -> show PopupMenu anchored to toolbar
                showReorderPopup(findViewById(R.id.toolbar))
                return true
            }
            else -> {
                // find title in optionsData by id
                val chosen = optionsData.find { it.id == item.itemId }
                chosen?.let {
                    Toast.makeText(this, "Ви вибрали: ${it.title}", Toast.LENGTH_SHORT).show()
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showReorderPopup(anchor: View) {
        val popup = PopupMenu(this, anchor)
        popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
        popup.setOnMenuItemClickListener { m ->
            when (m.itemId) {
                R.id.order_default -> { resetDefaultOrder() }
                R.id.order_reverse -> { optionsData.reverse() }
                R.id.order_alphabet -> { optionsData.sortBy { it.title } }
                R.id.order_custom1 -> { customOrder1() }
                R.id.order_custom2 -> { customOrder2() }
            }
            invalidateOptionsMenu() // перерендерити Options Menu
            Toast.makeText(this, "Порядок оновлено", Toast.LENGTH_SHORT).show()
            true
        }
        popup.show()
    }

    private fun resetDefaultOrder() {
        optionsData = mutableListOf(
            MenuItemData(1, "Опція 1", android.R.drawable.ic_menu_add),
            MenuItemData(2, "Опція 2", android.R.drawable.ic_menu_call),
            MenuItemData(3, "Опція 3", android.R.drawable.ic_menu_camera),
            MenuItemData(4, "Опція 4", android.R.drawable.ic_menu_compass),
            MenuItemData(5, "Опція 5", android.R.drawable.ic_menu_crop)
        )
    }
    private fun customOrder1() {
        optionsData = mutableListOf(optionsData[2], optionsData[0], optionsData[4], optionsData[1], optionsData[3])
    }
    private fun customOrder2() {
        optionsData = mutableListOf(optionsData[4], optionsData[3], optionsData[2], optionsData[1], optionsData[0])
    }

    // Context Menu for TextView
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if (v?.id == R.id.myTextView) {
            menuInflater.inflate(R.menu.context_menu, menu)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        // change TextView color
        when (item.itemId) {
            R.id.color_red -> myTextView.setTextColor(Color.RED)
            R.id.color_green -> myTextView.setTextColor(Color.GREEN)
            R.id.color_blue -> myTextView.setTextColor(Color.BLUE)
            R.id.color_black -> myTextView.setTextColor(Color.BLACK)
            R.id.color_magenta -> myTextView.setTextColor(Color.MAGENTA)
            else -> return super.onContextItemSelected(item)
        }
        Toast.makeText(this, "Колір змінено", Toast.LENGTH_SHORT).show()
        return true
    }

    // ActionMode callback for Contextual Action Mode (CAB)
    private val actionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.contextual_menu, menu)
            mode?.title = "Contextual Actions"
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            val pos = actionPosition
            if (pos < 0) return false
            when (item?.itemId) {
                R.id.style_bold -> { adapter.applyStyle(pos) { tv -> tv.setTypeface(null, Typeface.BOLD) } }
                R.id.style_italic -> { adapter.applyStyle(pos) { tv -> tv.setTypeface(null, Typeface.ITALIC) } }
                R.id.style_large -> { adapter.applyStyle(pos) { tv -> tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f) } }
                R.id.style_toggle_bg -> { adapter.toggleBackground(pos) }
                R.id.style_reset -> { adapter.resetStyle(pos) }
                else -> return false
            }
            mode?.finish()
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            adapter.clearSelection()
            currentActionMode = null
            actionPosition = -1
        }
    }
}
