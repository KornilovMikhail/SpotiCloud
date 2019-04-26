package com.github.kornilovmikhail.spoticloud.ui.tracklist


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.kornilovmikhail.spoticloud.R
import kotlinx.android.synthetic.main.activity_main.*


class TrackListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_track_list, container, false)
    }
}
