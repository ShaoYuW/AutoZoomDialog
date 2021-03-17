package com.yucheng.autozoomviewpaggerdialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.auto_zoome_dialog.*
import kotlinx.android.synthetic.main.item_page.view.*

class AutoZoomViewpagerDialog(private val layoutIdList: List<Int>) : DialogFragment() {

    private val viewList = mutableListOf<View>()
    private val heightList = mutableListOf<Int>()

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setLayout(width, height)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.auto_zoome_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutIdList.forEach {
            val newView = LayoutInflater.from(view.context)
                .inflate(it, null, false)
                .apply { measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED) }
            viewList.add(newView)
            heightList.add(newView.measuredHeight)
        }
        val adapter = MyAdapter()
        adapter.setList(viewList)
        vpContainer.adapter = adapter
        vpContainer.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                println(positionOffset)
                try {
                    val newHeight =
                        (heightList[position] + (heightList[position + 1] - heightList[position]) * positionOffset).toInt()
                    vpContainer.layoutParams = vpContainer.layoutParams.apply {
                        height = newHeight
                    }
                } catch (e: Exception) {

                }
            }
        })

        btnNext.visibility = if (viewList.size > 1) View.VISIBLE else View.GONE
        btnNext.setOnClickListener {
            vpContainer.setCurrentItem(vpContainer.currentItem + 1, true)
        }
    }
}


class MyAdapter : RecyclerView.Adapter<MyAdapter.PagerViewHolder>() {
    private var viewList: List<View> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false))
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bindData(viewList[position])
    }

    fun setList(list: List<View>) {
        viewList = list
    }

    override fun getItemCount(): Int {
        return viewList.size
    }

    class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(i: View) {
            itemView.container.removeAllViews()
            itemView.container.addView(i)
        }
    }
}