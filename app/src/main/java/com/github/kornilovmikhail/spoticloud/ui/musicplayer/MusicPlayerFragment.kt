package com.github.kornilovmikhail.spoticloud.ui.musicplayer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.github.kornilovmikhail.spoticloud.R
import com.github.kornilovmikhail.spoticloud.app.App
import com.github.kornilovmikhail.spoticloud.core.model.StreamServiceEnum
import com.github.kornilovmikhail.spoticloud.ui.main.CallbackFromFragments
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_music_player.*
import javax.inject.Inject

class MusicPlayerFragment : MvpAppCompatFragment(), MusicPlayerView {

    @Inject
    @InjectPresenter
    lateinit var musicPlayerPresenter: MusicPlayerPresenter

    @ProvidePresenter
    fun getPresenter(): MusicPlayerPresenter = musicPlayerPresenter

    private var callback: CallbackFromFragments? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as CallbackFromFragments
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component
            .musicPlayerSubComponentBuilder()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        hideViewsInActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_music_player, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addClickListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        musicPlayerPresenter.onCleared()
        showViewsInActivity()
        callback = null
    }

    private fun showViewsInActivity() {
        callback?.showMainViews()
    }

    private fun hideViewsInActivity() {
        callback?.hideMainViews()
    }

    fun updateView(title: String?, author: String?, imgLink: String?, source: StreamServiceEnum, duration: Long) {
        tv_player_track_title.text = title
        tv_player_track_author.text = author
        when (source) {
            StreamServiceEnum.SPOTIFY -> iv_player_track_source.isSelected = true
            StreamServiceEnum.SOUNDCLOUD -> iv_player_track_source.isSelected = false
        }
        sb_player.max = duration.toInt() / DELIMETER
        loadImg(imgLink)
    }

    fun updateSeekBar(position: Int) {
        sb_player.progress = position / DELIMETER
    }

    private fun loadImg(link: String?) {
        Picasso.get()
            .load(link)
            .placeholder(R.drawable.placeholder_music_notes)
            .into(iv_player_track_cover)
    }

    private fun addClickListeners() {
        btn_player_back.setOnClickListener {
            musicPlayerPresenter.onBackButtonClicked()
        }
        btn_player_next.setOnClickListener {
            callback?.nextTrack()
        }
        btn_player_prev.setOnClickListener {
            callback?.prevTrack()
        }
        btn_player_play_pause.setOnClickListener {
            if (btn_player_play_pause.isChecked) {
                callback?.pauseTrack()
            } else {
                callback?.resumeTrack()
            }
        }
    }

    companion object {
        private const val DELIMETER = 1000
    }
}
