package com.andiag.welegends.remake.views.summoners

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.graphics.ColorUtils
import android.view.View
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.andiag.welegends.remake.R
import com.andiag.welegends.remake.common.base.ActivityBase
import com.andiag.welegends.remake.models.IVersionRepository
import com.andiag.welegends.remake.models.VersionRepository
import com.andiag.welegends.remake.models.api.RestClient
import com.andiag.welegends.remake.models.database.Summoner
import com.andiag.welegends.remake.views.main.ActivityMain
import com.andiag.welegends.remake.views.summoners.history.FragmentSummonerList
import com.andiag.welegends.remake.views.summoners.search.FragmentFindSummoner
import com.hariofspades.gradientartist.GradientArtistBasic
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop


class ActivitySummoners : ActivityBase() {
    private val TAG = ActivitySummoners::class.java.simpleName
    private val mVersionRepository: IVersionRepository = VersionRepository.getInstance(this)

    @BindView(R.id.imageBackground)
    lateinit var imageBackground: GradientArtistBasic

    //region Activity Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_summoner)
        ButterKnife.bind(this)

        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                FragmentFindSummoner(), FragmentFindSummoner.TAG)
                .commit()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        imageBackground.visibility = View.VISIBLE
    }
    //endregion

    /**
     * Create a new [ActivityMain] [Intent]
     * @param [summoner] found [Summoner]
     * @param [isLocal] boolean indicating if the summoner was load from local database
     */
    fun createMainIntent(summoner: Summoner, isLocal: Boolean): Intent {
        return intentFor<ActivityMain>(ActivityMain.VAL_SUMMONER_ID to summoner.mid,
                ActivityMain.VAL_SUMMONER_RIOT_ID to summoner.riotId,
                ActivityMain.VAL_SUMMONER_NAME to summoner.name,
                ActivityMain.VAL_SUMMONER_REGION to summoner.region,
                ActivityMain.CONF_SEARCH_REQUIRED to isLocal).singleTop()
    }

    /**
     * Load given image(champion splash) as activity background
     */
    fun setBackground(image: String) {
        val endpoint: String = RestClient.splashImgEndpoint + image
        imageBackground.setUrlImage(endpoint, R.drawable.background_default, R.drawable.background_default, ImageView.ScaleType.CENTER_CROP)
        val gd = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(ColorUtils.setAlphaComponent(resolveColorAttribute(this, R.attr.colorPrimaryDark), 0xD9),
                        ColorUtils.setAlphaComponent(resolveColorAttribute(this, R.attr.colorAccent), 0xB3)))
        imageBackground.setGradient(gd)
    }

    /**
     * If container exist and version are loaded change fragment to summoner history
     */
    fun onClickSwapFragment() {
        if (findViewById(R.id.fragmentContainer) != null && !mVersionRepository.isLoading()) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, FragmentSummonerList(), FragmentSummonerList.TAG)
                    .addToBackStack(null)
                    .commit()

            imageBackground.visibility = View.GONE
            return
        }
        if (mVersionRepository.isLoading()) {
            //Notify user data load should end
            Snackbar.make(findViewById(android.R.id.content), R.string.wait_static_data_end, Snackbar.LENGTH_SHORT)
        }
    }

}
