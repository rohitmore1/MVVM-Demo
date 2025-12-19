package com.demo.mvvm.ui.di.modules


import com.demo.data.feature.auth.dataStore.AuthDataStore
import com.demo.data.feature.auth.dataStore.RemoteAuthDataStore
import com.demo.data.feature.auth.repository.PostsDataRepository
import com.demo.data.feature.restapiService.repository.DomainAPIDataRepository
import com.demo.domain.feature.posts.repository.PostsRepository
import com.demo.domain.feature.domainService.repository.DomainAPIRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun provideAuthDataRepository(dataRepository: PostsDataRepository): PostsRepository

    @Binds
    @Singleton
    abstract fun provideAuthDataStore(restAPIRepository: DomainAPIDataRepository): DomainAPIRepository

    @Binds
    @Singleton
    abstract fun provideCloudAuthDataStore(remoteAuthDataStore: RemoteAuthDataStore): AuthDataStore

}

/*@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthDataRepository
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindRestAPIRepository(
        impl: RestAPIDataRepository
    ): RestAPIRepository

    @Binds
    @Singleton
    abstract fun bindAuthDataStore(
        impl: CloudAuthDataStore
    ): AuthDataStore
}*/
