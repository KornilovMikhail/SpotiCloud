package com.github.kornilovmikhail.spoticloud.ui.start

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.kornilovmikhail.spoticloud.interactor.LoginSoundcloudUseCase
import com.github.kornilovmikhail.spoticloud.interactor.LoginSpotifyUseCase
import com.github.kornilovmikhail.spoticloud.navigation.router.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

@InjectViewState
class StartPresenter(
    private val router: Router,
    private val spotifyUseCase: LoginSpotifyUseCase,
    private val soundcloudUseCase: LoginSoundcloudUseCase
) :
    MvpPresenter<StartView>() {

    fun onResume() {
        spotifyUseCase.loadLocalSpotifyToken()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    if (it != "") {
                        viewState.disableSpotifyButton()
                        viewState.showSnackBar()
                    }
                },
                onError = {
                    viewState.showErrorMessage()
                }
            )
        soundcloudUseCase.loadLocalSoundCloudToken()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    if (it != "") {
                        viewState.disableSoundCloudButton()
                        viewState.showSnackBar()
                    }
                },
                onError = {
                    viewState.showErrorMessage()
                }
            )
    }

    fun onSoundcloudButtonClicked() {
        router.navigateToLoginSoundcloudScreen()
    }

    fun onSpotifyButtonClicked(requestCode: Int) {
        router.navigateToLoginSpotifyScreen(requestCode)
    }

    fun onSnackBarClicked() {

    }

    fun onSpotifyLoginResult(any: Any?) {
        val isSuccessful = spotifyUseCase.saveObject(any)
        if (isSuccessful) {
            viewState.disableSpotifyButton()
            viewState.showSnackBar()
        }
    }
}
