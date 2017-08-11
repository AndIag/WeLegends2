package com.andiag.welegends.remake.views.adapters

import com.andiag.welegends.remake.R
import com.andiag.welegends.remake.models.api.RestClient
import com.andiag.welegends.remake.models.database.Summoner
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.siyamed.shapeimageview.RoundedImageView


/**
 * Created by andyq on 15/12/2016.
 */
class AdapterSummonerList(layoutResId: Int, data: List<Summoner>, val version: String) : BaseQuickAdapter<Summoner, BaseViewHolder>(layoutResId, data) {


    override fun convert(holder: BaseViewHolder, summoner: Summoner) {
        holder.setText(R.id.textSummonerName, summoner.name)
                .setText(R.id.textLevel, "" + summoner.summonerLevel)
                .setText(R.id.textSummonerRegion, summoner.region)

        Glide.with(mContext).load(RestClient.getProfileIconEndpoint(version) + summoner.profileIconId + ".png").asBitmap()
                .placeholder(R.drawable.default_champion)
                .error(R.drawable.default_champion_error)
                .into(holder.getView<RoundedImageView>(R.id.imageSummoner))
    }
}
