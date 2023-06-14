package ru.vpcb.map.notes

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.vpcb.map.notes.data.remote.FirebaseRepo
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFBUserRepo() = FirebaseRepo()
}