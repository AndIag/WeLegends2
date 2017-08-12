package com.andiag.welegends.remake.views.adapters

import android.graphics.Color
import android.widget.TextView
import com.andiag.welegends.remake.R
import com.andiag.welegends.remake.common.base.ActivityBase
import com.andiag.welegends.remake.views.adapters.items.ItemNavDrawer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


class AdapterNavDrawer(layoutResId: Int, data: List<ItemNavDrawer>) : BaseQuickAdapter<ItemNavDrawer, BaseViewHolder>(layoutResId, data) {

    private var selected = 0
    private var colorTextSelected: Int = 0
    private var firstTime = true

    //    private static int getThemeAccentColor (final Context context) {
    //        final TypedValue value = new TypedValue();
    //        context.getTheme().resolveAttribute(R.attr.colorAccent, value, true);
    //        return value.data;
    //    }

    fun setSelected(position: Int) {
        if (position != selected) {
            selected = position
            notifyDataSetChanged()
        }
    }

    override fun convert(holder: BaseViewHolder, item: ItemNavDrawer) {
        if (firstTime) {
            colorTextSelected = ActivityBase.resolveColorAttribute(mContext,R.attr.mainColor)
            firstTime = false
        }
        holder.setImageResource(R.id.imageItem, item.imageId)
        val title = holder.getView<TextView>(R.id.textItem)
        title.text = item.title
        if (holder.adapterPosition == selected) {
            title.setTextColor(colorTextSelected)
        } else {
            title.setTextColor(Color.GRAY)
        }
    }
}