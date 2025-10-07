package edu.ucne.jendri_hidalgo_ap2_p1.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.jendri_hidalgo_ap2_p1.data.local.Database.HuacalesDb
import edu.ucne.jendri_hidalgo_ap2_p1.data.local.Dao.HucalesDao
import edu.ucne.jendri_hidalgo_ap2_p1.data.repository.HuacalesRepositoryImpl
import edu.ucne.jendri_hidalgo_ap2_p1.domain.repository.HuacalesRepository
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.DeleteHuacalesUseCase
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.GetHuacalesUseCase
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.ObserveHuacalesUseCase
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.UpsertHuacalesUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideHuacalesDb(@ApplicationContext appContext: Context): HuacalesDb {
        return Room.databaseBuilder(
            appContext,
            HuacalesDb::class.java,
            "Huacales.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideHuacalesDao(huacalesDb: HuacalesDb): HucalesDao = huacalesDb.HucalesDao()

    @Provides
    @Singleton
    fun provideHuacalesRepository(dao: HucalesDao): HuacalesRepository {
        return HuacalesRepositoryImpl(dao)
    }

    // AGREGAR ESTOS NUEVOS PROVIDERS PARA LOS USECASES:

    @Provides
    fun provideGetHuacalesUseCase(repository: HuacalesRepository): GetHuacalesUseCase {
        return GetHuacalesUseCase(repository)
    }

    @Provides
    fun provideObserveHuacalesUseCase(repository: HuacalesRepository): ObserveHuacalesUseCase {
        return ObserveHuacalesUseCase(repository)
    }

    @Provides
    fun provideUpsertHuacalesUseCase(repository: HuacalesRepository): UpsertHuacalesUseCase {
        return UpsertHuacalesUseCase(repository)
    }

    @Provides
    fun provideDeleteHuacalesUseCase(repository: HuacalesRepository): DeleteHuacalesUseCase {
        return DeleteHuacalesUseCase(repository)
    }
}