package edu.ucne.jendri_hidalgo_ap2_p1.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.jendri_hidalgo_ap2_p1.data.local.dao.CervezaDao
import edu.ucne.jendri_hidalgo_ap2_p1.data.local.database.AppDatabase
import edu.ucne.jendri_hidalgo_ap2_p1.data.repository.CervezaRepositoryImpl
import edu.ucne.jendri_hidalgo_ap2_p1.domain.repository.CervezaRepository
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.DeleteCervezaUseCase
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.GetCervezaUseCase
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.GetCervezasUseCase
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.UpsertCervezaUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "App.Database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCervezaRepository(dao: CervezaDao): CervezaRepository {
        return CervezaRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideGetCervezaUseCase(repository: CervezaRepository): GetCervezaUseCase {
        return GetCervezaUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCervezasUseCase(repository: CervezaRepository): GetCervezasUseCase {
        return GetCervezasUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpsertCervezaUseCase(repository: CervezaRepository): UpsertCervezaUseCase {
        return UpsertCervezaUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteCervezaUseCase(repository: CervezaRepository): DeleteCervezaUseCase {
        return DeleteCervezaUseCase(repository)
    }
}
