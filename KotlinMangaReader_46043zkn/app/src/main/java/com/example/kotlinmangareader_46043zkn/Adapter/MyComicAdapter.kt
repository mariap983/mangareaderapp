package com.example.kotlinmangareader_46043zkn.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmangareader_46043zkn.Common.Common
import com.example.kotlinmangareader_46043zkn.Interface.IRecyclerOnClick
import com.example.kotlinmangareader_46043zkn.Model.Comic
import com.example.kotlinmangareader_46043zkn.R
import com.squareup.picasso.Picasso

class MyComicAdapter(internal var context: Context,
                     internal var mangaList:List<Comic> ):
RecyclerView.Adapter<MyComicAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.comic_item,p0,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mangaList.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        Picasso.get().load(mangaList[p1].Image).into(p0.comic_image)
        p0.comic_name.text = mangaList[p1].Name
        p0.setClick(object:IRecyclerOnClick{
            override fun onClick(view: View, position: Int) {
                //Set manga selected
                Common.selected_comic = mangaList[position]
            }

        })
    }


    inner class MyViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView),View.OnClickListener{

        internal var comic_image: ImageView
        internal var comic_name: TextView
        lateinit var iRecyclerOnClick:IRecyclerOnClick

        fun setClick(iRecyclerOnClick: IRecyclerOnClick)
        {
            this.iRecyclerOnClick = iRecyclerOnClick;
        }



        init {
            comic_image = itemView.findViewById(R.id.comic_image) as ImageView
            comic_name = itemView.findViewById(R.id.comic_name) as TextView
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            iRecyclerOnClick.onClick(p0!!,adapterPosition)
        }

    }

}