package com.okemwag.pulse.di

import android.content.Context
import android.content.SharedPreferences
import com.okemwag.common.utils.AppCoroutineDispatchers
import com.okemwag.network.interceptor.TokenProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val PREFS_NAME = "pulse_prefs"
    private const val KEY_ACCESS_TOKEN = "access_token"
    private const val KEY_REFRESH_TOKEN = "refresh_token"

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAppCoroutineDispatchers(): AppCoroutineDispatchers {
        return AppCoroutineDispatchers(
            io = Dispatchers.IO,
            computation = Dispatchers.Default,
            main = Dispatchers.Main,
            unconfined = Dispatchers.Unconfined
        )
    }

    @Provides
    @Singleton
    fun provideTokenProvider(
        prefs: SharedPreferences
    ): TokenProvider {
        return object : TokenProvider {
            override fun getAccessToken(): String? {
                return prefs.getString(KEY_ACCESS_TOKEN, null)
            }

            override fun getRefreshToken(): String? {
                return prefs.getString(KEY_REFRESH_TOKEN, null)
            }

            override fun saveTokens(accessToken: String, refreshToken: String) {
                prefs.edit()
                    .putString(KEY_ACCESS_TOKEN, accessToken)
                    .putString(KEY_REFRESH_TOKEN, refreshToken)
                    .apply()
            }

            override fun clearTokens() {
                prefs.edit()
                    .remove(KEY_ACCESS_TOKEN)
                    .remove(KEY_REFRESH_TOKEN)
                    .apply()
            }
        }
    }
}
