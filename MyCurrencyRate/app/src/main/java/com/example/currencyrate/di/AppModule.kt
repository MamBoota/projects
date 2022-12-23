package com.example.currencyrate.di

import com.example.currencyrate.datasource.remotedatasource.datasource.AppRemoteRepository
import com.example.currencyrate.datasource.remotedatasource.datasource.AppRemoteRepositoryImpl
import com.example.currencyrate.datasource.remotedatasource.datasource.CurrencyAPI
import com.example.currencyrate.db.dao.FavoriteDao
import com.example.currencyrate.presentation.mainactivity.domain.MainActivityInteractor
import com.example.currencyrate.presentation.mainactivity.domain.MainActivityInteractorImpl
import com.example.currencyrate.presentation.recyclerview.domain.RecyclerInteractor
import com.example.currencyrate.presentation.recyclerview.domain.RecyclerInteractorImpl
import com.example.currencyrate.rest.RetrofitFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideCurrencyAPI(retrofitFactory: RetrofitFactory): CurrencyAPI {
        return retrofitFactory.getApiInterface(CurrencyAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideAppRemoteRepository(currencyAPI: CurrencyAPI): AppRemoteRepository {
        return AppRemoteRepositoryImpl(currencyAPI)
    }

    @Singleton
    @Provides
    fun provideRecyclerInteractor(favoriteDao: FavoriteDao): RecyclerInteractor {
        return RecyclerInteractorImpl(favoriteDao)
    }


    @Singleton
    @Provides
    fun provideMainActivityInteractor(
        repository: AppRemoteRepository,
        favoriteDao: FavoriteDao
    ): MainActivityInteractor {
        return MainActivityInteractorImpl(repository, favoriteDao)
    }
}