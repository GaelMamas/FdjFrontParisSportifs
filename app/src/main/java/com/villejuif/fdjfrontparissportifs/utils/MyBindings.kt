package com.villejuif.fdjfrontparissportifs.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.villejuif.fdjfrontparissportifs.R
import com.villejuif.fdjfrontparissportifs.data.model.Team
import com.villejuif.fdjfrontparissportifs.main.MainAdapter

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Team?>?) {
    items?.let {
        (listView.adapter as MainAdapter).submitList(items)
    }
}

@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, strTeamBanner: String?) {
    if (strTeamBanner.isNullOrEmpty()) {
        imageView.setImageResource(R.drawable.ic_photo_placeholder)
    } else {
        Glide.with(imageView.context)
            .load(strTeamBanner)
            .placeholder(R.drawable.ic_photo_placeholder)
            .into(imageView)
    }
}