package com.example.projectmobile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

class StreamAdapter(
    private var streams: MutableList<Stream>) : RecyclerView.Adapter<StreamAdapter.StreamViewHolder>() {

    fun appendStreams(streams: List<Stream>){
        this.streams.addAll(streams)
        notifyItemRangeInserted(this.streams.size, streams.size)
    }

    inner class StreamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.imgStreamPhoto)
        private val streamName: TextView = itemView.findViewById((R.id.StreamName))
        private val viewerCount: TextView = itemView.findViewById((R.id.ViewerCount))

        fun bind(stream: Stream) {
            val origImageUrl :String = stream.thumbnailUrl.replace("{height}", "125").replace("{width}", "125")
            val scaledImageUrl :String = origImageUrl.replace("o.jpg", "l.jpg")

            Glide.with(itemView)
                .load(scaledImageUrl)
                .transform(CenterCrop())
                .into(poster)
            streamName.text = stream.userName
            viewerCount.text = stream.viewerCount.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_stream, parent, false)
        return StreamViewHolder(view)
    }

    override fun getItemCount(): Int {
        return streams.size
    }

    override fun onBindViewHolder(holder: StreamViewHolder, position: Int) {
        holder.bind(streams[position])
    }
}