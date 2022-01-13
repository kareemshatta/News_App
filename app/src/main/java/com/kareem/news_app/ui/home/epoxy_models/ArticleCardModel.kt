package com.kareem.news_app.ui.home.epoxy_models

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kareem.news_app.R

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass(layout = R.layout.view_holder_article_layout)
internal abstract class ArticleCardModel : EpoxyModelWithHolder<ArticleCardModel.Holder>() {
    // Declare your model properties like this
    @EpoxyAttribute
    lateinit var text: String

    //  @EpoxyAttribute(DoNotHash) OnClickListener clickListener;
    override fun bind(holder: Holder) {
        // Implement this to bind the properties to the view
        holder.tvTitle.text = text
//        holder.button.setOnClickListener(clickListener)
    }

    internal class Holder : EpoxyHolder() {
        lateinit var tvTitle: TextView
        override fun bindView(itemView: View) {
            tvTitle = itemView.findViewById(R.id.tvTitle)
        }
    }
}