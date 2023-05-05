package com.ikuzMirel.flick.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ikuzMirel.flick.data.local.database.UserDatabase
import com.ikuzMirel.flick.data.remote.auth.AuthRemote
import com.ikuzMirel.flick.data.remote.auth.AuthRemoteImpl
import com.ikuzMirel.flick.data.remote.chat.ChatRemote
import com.ikuzMirel.flick.data.remote.chat.ChatRemoteImpl
import com.ikuzMirel.flick.data.remote.user.UserRemote
import com.ikuzMirel.flick.data.remote.user.UserRemoteImpl
import com.ikuzMirel.flick.data.remote.websocket.WebSocketService
import com.ikuzMirel.flick.data.remote.websocket.WebSocketServiceImpl
import com.ikuzMirel.flick.data.repositories.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.observer.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Preferences")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePreferenceDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.dataStore

    @Provides
    @Singleton
    fun provideAuthRemote(
        httpClient: HttpClient
    ): AuthRemote = AuthRemoteImpl(httpClient)

    @Provides
    @Singleton
    fun provideUserRemote(
        httpClient: HttpClient
    ): UserRemote = UserRemoteImpl(httpClient)

    @Provides
    @Singleton
    fun provideChatRemote(
        httpClient: HttpClient
    ): ChatRemote = ChatRemoteImpl(httpClient)

    @Provides
    @Singleton
    fun provideWebSocketService(
        httpClient: HttpClient
    ): WebSocketService = WebSocketServiceImpl(httpClient)
}