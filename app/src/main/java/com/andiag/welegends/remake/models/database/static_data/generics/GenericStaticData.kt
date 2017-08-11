package com.andiag.welegends.remake.models.database.static_data.generics

import java.io.Serializable

/**
 * Created by Canalejas on 08/12/2016.
 */

class GenericStaticData<K, E> : Serializable {
    var type: String? = null
    var version: String? = null
    var format: String? = null
    var data: Map<K, E>? = null
}
