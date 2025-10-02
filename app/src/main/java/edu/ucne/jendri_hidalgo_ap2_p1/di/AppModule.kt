package edu.ucne.jendri_hidalgo_ap2_p1.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.jendri_hidalgo_ap2_p1.data.local.Database.HuacalesDb
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideHuacalesDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            HuacalesDb::class.java,
            "Huacales.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideHuacalesDao(huacalesDb: HuacalesDb) = huacalesDb.HucalesDao()

}