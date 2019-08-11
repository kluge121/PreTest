package com.kakaopay.kakaopaypretest.view.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.kakaopay.kakaopaypretest.R
import com.kakaopay.kakaopaypretest.databinding.ItemImageViewPagerBinding
import com.kakaopay.kakaopaypretest.model.ImageItem


class ImagePagerAdapter(position:String) : PagerAdapter() {

    private val items = mutableListOf<ImageItem>()

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return true
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: ItemImageViewPagerBinding = DataBindingUtil.inflate(LayoutInflater.from(container.context), R.layout.item_image_view_pager, container, false)
        binding.imageUrl = items[position].image_url
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.invalidate()
        super.destroyItem(container, position, `object`)
    }

    fun replaceItem(list: MutableList<ImageItem>) {
        items.clear()
        items.addAll(list)
    }
}
