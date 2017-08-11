package com.andiag.welegends.remake.models.database.static_data.utils

/**
 * Created by Canalejas on 12/12/2016.
 * Used to clean responses in where the Riot ID is the key of the returned [Map]
 * and its not included on the value.
 */
interface KeyInMapTypeAdapter {
    fun setKey(key: String)
}