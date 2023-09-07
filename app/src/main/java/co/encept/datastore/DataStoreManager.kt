package co.encept.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


val Context.user: DataStore<Preferences> by preferencesDataStore(name = "userInfo")


object DataStoreKeys {
    val USER_NAME = stringPreferencesKey("user_name")
    val EMAIL = stringPreferencesKey("email")
}