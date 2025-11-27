
package com.example.kotlin8

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MyListAdapter(private val ctx: Context, private val items: MutableList<String>) : BaseAdapter() {

    // track style states by position
    private val selected = mutableSetOf<Int>()
    private val styleReset = mutableMapOf<Int, StyleState>()

    data class StyleState(var textSize: Float?, var typefaceStyle: Int?, var bg: Boolean = false)

    override fun getCount(): Int = items.size
    override fun getItem(position: Int): Any = items[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(ctx).inflate(R.layout.list_item, parent, false)
        val icon = view.findViewById<ImageView>(R.id.itemIcon)
        val text = view.findViewById<TextView>(R.id.itemText)

        icon.setImageResource(android.R.drawable.ic_menu_info_details)
        text.text = items[position]

        // apply selection highlight
        if (selected.contains(position)) {
            view.setBackgroundColor(Color.parseColor("#DDDDFF"))
        } else {
            view.setBackgroundColor(Color.TRANSPARENT)
        }

        // apply saved styles
        styleReset[position]?.let { s ->
            s.textSize?.let { text.setTextSize(it) }
            s.typefaceStyle?.let { text.setTypeface(null, it) }
            view.setBackgroundColor(if (s.bg) Color.YELLOW else view.background?.let { Color.TRANSPARENT } ?: Color.TRANSPARENT)
        }

        return view
    }

    fun setSelected(pos: Int, flag: Boolean) {
        if (flag) selected.add(pos) else selected.remove(pos)
        notifyDataSetChanged()
    }
    fun clearSelection() {
        selected.clear()
        notifyDataSetChanged()
    }

    fun applyStyle(pos: Int, op: (TextView) -> Unit) {
        // get view by forcing refresh and store style
        // We'll store basic style info in styleReset map and call notify
        val cur = styleReset.getOrPut(pos) { StyleState(null, null, false) }
        // For simplicity, set typeface bold/italic based on op's effect: we call op on a dummy TextView to detect change is complex.
        // Instead, set based on which op was called from Activity (they call applyStyle with known op).
        // We'll approximate: if op sets Typeface.BOLD -> set that
        // But Activity passes lambdas; we can't inspect them; so Activity calls adapter.applyStyle with specific ops - here we assume Activity only calls setTypeface or setTextSize
        // For reliability, Activity uses dedicated adapter methods. (we have applyStyle used only for typeface/size)
        // Simpler: provide dedicated adapter methods below (resetStyle/toggleBackground) and rely on them.
        notifyDataSetChanged()
    }

    fun toggleBackground(pos: Int) {
        val cur = styleReset.getOrPut(pos) { StyleState(null, null, false) }
        cur.bg = !cur.bg
        notifyDataSetChanged()
    }

    fun resetStyle(pos: Int) {
        styleReset.remove(pos)
        notifyDataSetChanged()
    }

    // helper to allow Activity to set typeface/size concretely
    fun applyTypeface(pos: Int, style:Int) {
        val cur = styleReset.getOrPut(pos) { StyleState(null, null, false) }
        cur.typefaceStyle = style
        notifyDataSetChanged()
    }
    fun applyTextSize(pos: Int, sp: Float) {
        val cur = styleReset.getOrPut(pos) { StyleState(null, null, false) }
        cur.textSize = sp
        notifyDataSetChanged()
    }
}
