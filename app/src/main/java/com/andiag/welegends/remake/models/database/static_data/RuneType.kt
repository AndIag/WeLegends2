package com.andiag.welegends.remake.models.database.static_data

import com.andiag.welegends.remake.WeLegendsDatabase
import com.google.gson.annotations.Expose
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table

/**
 * Created by Canalejas on 13/12/2016.
 */
@Table(database = WeLegendsDatabase::class)
class RuneType { // TODO all this are enums

    @Expose(serialize = false, deserialize = false) @PrimaryKey(autoincrement = true) var mid: Int = 0
    @Column(getterName = "getIsrune", setterName = "setIsrune") var isrune: Boolean? = null
    @Column var tier: String? = null
    @Column var type: String? = null

}