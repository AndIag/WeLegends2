package com.andiag.welegends.remake.models.database.static_data

import android.util.Log
import com.andiag.welegends.remake.WeLegendsDatabase
import com.andiag.welegends.remake.common.utils.CallbackData
import com.andiag.welegends.remake.models.api.RestClient
import com.andiag.welegends.remake.models.database.static_data.generics.OrmBaseModel
import com.andiag.welegends.remake.models.database.static_data.utils.CallbackSemaphore
import com.andiag.welegends.remake.models.database.static_data.utils.CallbackStaticData
import com.google.gson.JsonArray
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.*
import java.io.Serializable

/**
 * Created by Canalejas on 12/12/2016.
 */

@Table(database = WeLegendsDatabase::class)
class SummonerSpell : OrmBaseModel(), Serializable {

    @Expose(serialize = false, deserialize = false) @PrimaryKey(autoincrement = true) var mid: Int = 0
    @SerializedName("id") @Unique @Column var riotId: String? = null
    @Unique @Column var name: String? = riotId
    @Column var description: String? = null
    @Column var tooltip: String? = null
    @Column var maxrank: Int? = null
    @Column var cooldownBurn: String? = null
    @Column var costBurn: String? = null
    @Column var key: String? = null
    @Column var summonerLevel: Int? = null
    @Column var costType: String? = null
    @Column var maxammo: String? = null
    @Column var rangeBurn: String? = null
    @ForeignKey(tableClass = Image::class) var image: Image? = null
    @Column var resource: String? = null
    @Column var range: JsonArray? = null
    @Column var modes: JsonArray? = null
    @Column var cost: JsonArray? = null
    @Column var cooldown: JsonArray? = null

    companion object {
        private val TAG: String = SummonerSpell::class.java.simpleName

        fun loadFromServer(callback: CallbackData<*>, semaphore: CallbackSemaphore, version: String, locale: String) {
            val call = RestClient.getDdragonStaticData(version, locale).summonerSpells()
            call.enqueue(CallbackStaticData<SummonerSpell>(locale, semaphore, callback, Runnable {
                Log.i(TAG, "Reloading %s Locale From onResponse To: %s".format(SummonerSpell::class.java.simpleName, RestClient.DEFAULT_LOCALE))
                loadFromServer(callback, semaphore, version, RestClient.DEFAULT_LOCALE)
            }))
        }

    }

}