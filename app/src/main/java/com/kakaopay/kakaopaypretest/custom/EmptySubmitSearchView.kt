package com.kakaopay.kakaopaypretest.custom

import android.content.Context
import androidx.appcompat.widget.SearchView
import android.util.AttributeSet

class EmptySubmitSearchView : SearchView {


    lateinit var searchSrcTextView: SearchAutoComplete
    internal var listener: OnQueryTextListener? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun setOnQueryTextListener(listener: OnQueryTextListener?) {
        super.setOnQueryTextListener(listener)
        this.listener = listener
        searchSrcTextView = this.findViewById(androidx.appcompat.R.id.search_src_text)
        searchSrcTextView.setOnEditorActionListener { textView, i, keyEvent ->
            listener?.onQueryTextSubmit(query.toString())
            true
        }
    }
}