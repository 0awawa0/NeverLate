package com.awawa.neverlate.ui.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.awawa.neverlate.R

class SettingItemView(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs) {

    var title: TextView? = null
    var content: TextView? = null
    private val view = LayoutInflater.from(context).inflate(R.layout.layout_setting_item, this, false) as ConstraintLayout?

    init {
        title = view!!.findViewById(R.id.settingTitle) as TextView
        content = view.findViewById(R.id.settingContent) as TextView
        val typedArray = getContext().obtainStyledAttributes(
            attrs,
            R.styleable.SettingItemView
        )
        (title as TextView).text = typedArray.getText(R.styleable.SettingItemView_setting_title)
        (content as TextView).text = typedArray.getText(R.styleable.SettingItemView_setting_value)
        typedArray.recycle()
        addView(view)
    }

    fun setContentText(text: String?) {
        content!!.text = text
    }

}