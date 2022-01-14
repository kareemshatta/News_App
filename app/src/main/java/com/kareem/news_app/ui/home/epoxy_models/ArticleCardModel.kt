package com.kareem.news_app.ui.home.epoxy_models

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import coil.load
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kareem.domain.models.ArticleDataModel
import com.kareem.news_app.R

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass(layout = R.layout.view_holder_article_layout)
internal abstract class ArticleCardModel : EpoxyModelWithHolder<ArticleCardModel.Holder>() {

    @EpoxyAttribute
    lateinit var articleDataModel: ArticleDataModel

    override fun bind(holder: Holder) {
        holder.tvTitle.text = articleDataModel.title
        holder.tvDescription.text = articleDataModel.description
        holder.tvSource.text = articleDataModel.sourceName
        holder.ivArticleImage.load(articleDataModel.urlToImage)
        holder.cvArticleView.setOnClickListener {
            navigateFromHomeToArticleDetails(it)
        }
    }
    private fun navigateFromHomeToArticleDetails(view: View){
        Navigation.findNavController(view).navigate(
            R.id.action_homeFragment_to_articleDetailsFragment,
            Bundle().apply {
                putString("title", articleDataModel.title)
                putString("urlOfImage", articleDataModel.urlToImage)
                putString("publishedAt", articleDataModel.publishedAt)
                putString("author", articleDataModel.author)
                putString("description", articleDataModel.description)
                putString("content", articleDataModel.content)
            }
        )
    }
    internal class Holder : EpoxyHolder() {
        lateinit var cvArticleView: CardView
        lateinit var tvTitle: TextView
        lateinit var tvDescription: TextView
        lateinit var tvSource: TextView
        lateinit var ivArticleImage: ImageView

        override fun bindView(itemView: View) {
            cvArticleView = itemView.findViewById(R.id.cvArticleView)
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvDescription = itemView.findViewById(R.id.tvDescription)
            tvSource = itemView.findViewById(R.id.tvSource)
            ivArticleImage = itemView.findViewById(R.id.ivArticleImage)
        }
    }
}