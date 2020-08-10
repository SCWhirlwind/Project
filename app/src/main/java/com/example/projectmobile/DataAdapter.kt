package com.example.projectmobile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DataAdapter(
    private var datas: MutableList<Data>,
    private var onDataClick: (data: Data) -> Unit,
    private var myLikedGames : MutableList<LikeGames>) : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    fun appendDatas(datas: List<Data>){
        this.datas.addAll(datas)
        notifyItemRangeInserted(this.datas.size, datas.size)
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.imgGamePhoto)
        private val dataName: TextView = itemView.findViewById((R.id.GameName))

        fun bind(data: Data) {
            val origImageUrl :String = data.boxArtUrl.replace("{height}", "125").replace("{width}", "125")
            val scaledImageUrl :String = origImageUrl.replace("o.jpg", "l.jpg")
            val likeButton = itemView.findViewById<ToggleButton>(R.id.likeButton)
            likeButton.setOnCheckedChangeListener { _, isChecked ->

                val user = FirebaseAuth.getInstance().currentUser
                val likeGames = LikeGames(null, user!!.uid, data.id)
                val database = FirebaseDatabase.getInstance()

                if (isChecked) {

                    var exists = false
                    myLikedGames.forEach {
                        if (it.dataID == data.id ) {
                            exists = true
                        }
                    }

                    if (!exists) {
                        val newLikeReference = database.reference.child("Users").push().key
                        likeGames.key = newLikeReference
                        database.reference.child("Users").child(newLikeReference.toString()).setValue(likeGames)
                        myLikedGames.add(likeGames)
                    }
                }
                else {
                    myLikedGames.forEach {
                        if (it.dataID == data.id) {
                            likeGames.key = it.key.toString()
                            database.reference.child("Users").child(it.key.toString()).removeValue()
                            myLikedGames.remove(likeGames)

                            myLikedGames.removeIf { liked -> liked.key.equals(it.key.toString()) }
                        }
                    }
                }
            }

            var liked = false
            myLikedGames?.forEach {
                if(data.id == it.dataID) {
                    liked = true
                }
            }

            likeButton.isChecked = liked

            Glide.with(itemView)
                .load(scaledImageUrl)
                .transform(CenterCrop())
                .into(poster)
            dataName.text = data.name
            itemView.setOnClickListener { onDataClick.invoke(data) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(datas[position])
    }


}