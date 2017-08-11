package com.andiag.welegends.remake.views.summoners.search

import com.andiag.welegends.remake.models.database.Summoner

/**
 * Created by Canalejas on 10/02/2017.
 */
interface IViewFindSummoner {
    fun onSummonerFound(summoner: Summoner, isLocal: Boolean)
    fun onSummonerNotFound(message: String)
    fun onVersionLoaded(version: String)
}