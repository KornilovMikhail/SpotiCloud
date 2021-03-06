package com.github.kornilovmikhail.spoticloud.ui.start.di.module

import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.interactor.LoginSoundcloudUseCase
import com.github.kornilovmikhail.spoticloud.interactor.LoginSpotifyUseCase
import com.github.kornilovmikhail.spoticloud.interactor.LoginUseCase
import com.github.kornilovmikhail.spoticloud.navigation.router.Router
import com.github.kornilovmikhail.spoticloud.ui.start.StartPresenter
import dagger.Module
import dagger.Provides

@Module
class StartModule {

    @FeatureScope
    @Provides
    fun provideStartPresenter(
        router: Router,
        loginSpotifyUseCase: LoginSpotifyUseCase,
        loginSoundcloudUseCase: LoginSoundcloudUseCase,
        loginUseCase: LoginUseCase
    ): StartPresenter =
        StartPresenter(router, loginSpotifyUseCase, loginSoundcloudUseCase, loginUseCase)
}
