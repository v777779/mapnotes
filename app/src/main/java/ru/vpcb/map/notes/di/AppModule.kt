package ru.vpcb.map.notes.di

import android.content.Context
import android.graphics.Point
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.vpcb.map.notes.data.local.NoteDb
import ru.vpcb.map.notes.model.converter.PointJsonAdapter
import ru.vpcb.map.notes.location.LocationClient
import ru.vpcb.map.notes.location.LocationHolder
import ru.vpcb.map.notes.model.Note
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDb(@ApplicationContext context: Context) = NoteDb.instance(context)

    @Provides
    @Singleton
    fun provideLocationClient(@ApplicationContext context: Context) = LocationClient(context)

    @Provides
    @Singleton
    fun provideLocationHolder() = LocationHolder()

    @Provides
    @Singleton
    fun provideMoshi():Moshi = Moshi.Builder()
        .add(PointJsonAdapter())
        .build()

    @Provides
    @Singleton
    @QPointAdapter
    fun providePointAdapter(moshi: Moshi):JsonAdapter<Point> = moshi.adapter(Point::class.java)

    @Provides
    @Singleton
    @QNoteAdapter
    fun provideNoteAdapter(moshi: Moshi):JsonAdapter<Note> = moshi.adapter(Note::class.java)
}