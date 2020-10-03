package com.villejuif.fdjfrontparissportifs.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.villejuif.fdjfrontparissportifs.R
import com.villejuif.fdjfrontparissportifs.data.Team
import com.villejuif.fdjfrontparissportifs.main.MainAdapter
import java.io.File

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Team?>?) {
    items?.let {
        (listView.adapter as MainAdapter).submitList(items)
    }
}

@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, strTeamBanner:String?){
    strTeamBanner?.let {
        Glide.with(imageView.context)
            .load(strTeamBanner)
            .placeholder(R.mipmap.ic_launcher)
            .into(imageView)
    }

    Log.d("MyBindings", "TeamBanner: $strTeamBanner")
}