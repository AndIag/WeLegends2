package com.andiag.welegends.remake.models.database.static_data.utils

import android.util.Log
import com.andiag.welegends.remake.common.utils.CallbackData
import com.andiag.welegends.remake.models.api.RestClient
import com.andiag.welegends.remake.models.database.static_data.generics.GenericStaticData
import com.andiag.welegends.remake.models.database.static_data.generics.OrmBaseModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by Canalejas on 11/12/2016.
 * Generic callback to simplify all StaticData Requests
 * @constructor [semaphore] used to allow concurrency
 * @constructor [locale] needed to know if a reload to {@link RestClient.DEFAULT_LOCALE} is required
 * @constructor [callback] called when callback process ends
 * @constructor [runnable] method to run when reload is required
 */
class CallbackStaticData<T : OrmBaseModel>(
        private var locale: String,
        private var semaphore: CallbackSemaphore,
        private var callback: CallbackData<*>,
        private var runnable: Runnable)
    : Callback<GenericStaticData<String, T>> {

    private val TAG: String = CallbackStaticData::class.java.simpleName

    override fun onResponse(call: Call<GenericStaticData<String, T>>?, response: Response<GenericStaticData<String, T>>?) {
        if (!response!!.isSuccessful || response.body() == null) {
            if (locale != RestClient.DEFAULT_LOCALE) {
                runnable.run()
                return
            }
            Log.e(TAG, "ERROR: onResponse: %s".format(response.errorBody().string()))
            Log.i(TAG, "Semaphore released with errors")
            semaphore.release(1)
            callback.onError(Throwable(response.message()))
        } else {
            doAsync {
                try {
                    val clazz: Class<T> = response.body().data!!.values.first().javaClass
                    Log.i(TAG, "Loaded %s: %s".format(clazz.simpleName, response.body().data!!.keys))
                    if (clazz.interfaces.contains(KeyInMapTypeAdapter::class.java)) {
                        for ((k, v) in response.body().data!!) {
                            (v as KeyInMapTypeAdapter).setKey(k)
                            v.saveOrUpdate()
                        }
                    }
                    if (!clazz.interfaces.contains(KeyInMapTypeAdapter::class.java)) {
                        for (v in response.body().data!!.values) {
                            v.saveOrUpdate()
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error saving %s".format(e.message))
                    uiThread {
                        callback.onError(e)
                    }
                } finally {
                    Log.i(TAG, "Semaphore released %s")
                    semaphore.release(1)
                }
            }
        }
    }

    override fun onFailure(call: Call<GenericStaticData<String, T>>?, t: Throwable?) {
        if (locale != RestClient.DEFAULT_LOCALE) {
            runnable.run()
            return
        }
        Log.e(TAG, "ERROR: onFailure: %s".format(t!!.message))
        Log.i(TAG, "Semaphore released with errors")
        semaphore.release(1)
        callback.onError(t)
    }

}