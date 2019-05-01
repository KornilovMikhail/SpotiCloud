package com.github.kornilovmikhail.spoticloud.app.di.module

import android.content.Context
import androidx.room.Room
import com.github.kornilovmikhail.spoticloud.app.di.scope.ApplicationScope
import com.github.kornilovmikhail.spoticloud.data.db.AbstractTrackDB
import com.github.kornilovmikhail.spoticloud.data.db.dao.TrackSoundCloudDAO
import dagger.Module
import dagger.Provides

@Module
class RoomModule {

    @Provides
    @ApplicationScope
    fun provideTrackDB(context: Context): AbstractTrackDB =
        Room.databaseBuilder(context, AbstractTrackDB::class.java, DATABASE_NAME)
            .build()

    @Provides
    @ApplicationScope
    fun provideTrackSoundCloudDAO(database: AbstractTrackDB): TrackSoundCloudDAO = database.trackSoundCloudDAO()

    companion object {
        private const val DATABASE_NAME = "tracks_db.db"
    }
}
