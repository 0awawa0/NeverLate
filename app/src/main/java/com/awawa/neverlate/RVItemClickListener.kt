package com.awawa.neverlate

import android.view.ContextMenu
import android.view.View

interface RVItemClickListener {
    fun onClick(view: View)
    fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?,
        position: Int
    )
}