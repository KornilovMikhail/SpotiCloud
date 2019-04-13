package com.github.kornilovmikhail.spoticloud.ui.start

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.github.kornilovmikhail.spoticloud.R
import com.github.kornilovmikhail.spoticloud.app.App
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.MySupportAppNavigator
import kotlinx.android.synthetic.main.fragment_start.*
import javax.inject.Inject

class StartFragment : MvpAppCompatFragment(), StartView {

    @Inject
    @InjectPresenter
    lateinit var startPresenter: StartPresenter

    @ProvidePresenter
    fun getPresenter(): StartPresenter = startPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component
            .startSubComponentBuilder()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_start, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_start_soundcloud.setOnClickListener { startPresenter.onSoundcloudButtonClicked() }
        btn_start_spotify.setOnClickListener { startPresenter.onSpotifyButtonClicked(REQUEST_CODE_SPOTIFY) }
        MySupportAppNavigator.setFragment(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_SPOTIFY) {
                val dataBundle = data?.getBundleExtra(EXTRA_AUTH_RESPONSE)
                val any = dataBundle?.get(RESPONSE_KEY)
                startPresenter.onSpotifyLoginResult(any)
            }
        }
    }

    override fun disableSpotifyButton() {
        btn_start_spotify.isEnabled = false
        context?.let {
            btn_start_spotify.setBackgroundColor(ContextCompat.getColor(it, R.color.colorGrey))
            tv_start_choice.setTextColor(ContextCompat.getColor(it, R.color.colorGrey))
        }
    }

    companion object {
        private const val REQUEST_CODE_SPOTIFY = 898
        private const val RESPONSE_KEY = "response"
        private const val EXTRA_AUTH_RESPONSE = "EXTRA_AUTH_RESPONSE"

        fun getInstance(): StartFragment = StartFragment()
    }
}
