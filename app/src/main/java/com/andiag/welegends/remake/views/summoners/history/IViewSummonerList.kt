package com.andiag.welegends.remake.views.summoners.history

import com.andiag.welegends.remake.models.database.Summoner

/**
 * Created by Canalejas on 10/02/2017.
 */
interface IViewSummonerList {
    fun onSummonersLoaded(summoners: List<Summoner>)
    fun onSummonersEmpty(error: String?)
}