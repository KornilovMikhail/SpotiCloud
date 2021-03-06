package com.github.kornilovmikhail.spoticloud.ui.tracklist

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.kornilovmikhail.spoticloud.core.model.Track
import com.github.kornilovmikhail.spoticloud.interactor.TracksUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class TrackListPresenter(
    private val tracksUseCase: TracksUseCase
) : MvpPresenter<TrackListView>() {

    private val disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        getTracks()
    }

    fun onRefresh() {
        getTracks()
    }

    fun getTracks() {
        disposables.add(
            tracksUseCase.getFavoriteTracks()
                .doOnSubscribe { viewState.showProgressBar() }
                .doAfterTerminate { viewState.hideProgressBar() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isEmpty()) {
                        viewState.showEmptyTracksMessage()
                    } else {
                        viewState.showTracks(it)
                    }
                }, {
                    viewState.showErrorMessage()
                })
        )
    }

    fun onSortClicked() {
        disposables.add(
            tracksUseCase.getSortedTracks()
                .doOnSubscribe { viewState.showProgressBar() }
                .doAfterTerminate { viewState.hideProgressBar() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showTracks(it)
                },{
                    viewState.showErrorMessage()
                })
        )
    }

    fun onCleared() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    fun onTrackClicked(track: Track?) {
        viewState.sendTrackToPlayer(track)
    }
}
