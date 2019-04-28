package com.github.kornilovmikhail.spoticloud.ui.main.di.module

import com.github.kornilovmikhail.spoticloud.app.di.scope.FeatureScope
import com.github.kornilovmikhail.spoticloud.interactor.LoginUseCase
import com.github.kornilovmikhail.spoticloud.navigation.FragmentBottomEnum
import com.github.kornilovmikhail.spoticloud.navigation.router.Router
import com.github.kornilovmikhail.spoticloud.ui.main.MainPresenter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.NavigatorHolder
import java.util.*

@Module
class MainModule {

    @FeatureScope
    @Provides
    fun provideMainPresenter(
        router: Router,
        navigatorHolder: NavigatorHolder,
        loginUseCase: LoginUseCase,
        limitedUniqueQueue: LinkedList<FragmentBottomEnum>
    ): MainPresenter = MainPresenter(router, navigatorHolder, loginUseCase, limitedUniqueQueue)
}
