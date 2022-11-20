package com.myapp.logistics.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.myapp.logistics.repository.AddLoadRepository
import com.myapp.logistics.repository.AddLoadRepositoryImpl
import com.myapp.logistics.util.LogisticsPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    fun provideFirebaseFireStore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    fun provideLogisticsSharedPref(sharedPreferences: SharedPreferences): LogisticsPref = LogisticsPref(sharedPreferences = sharedPreferences)

    @Provides
    fun provideAddLoadRepository(firestore: FirebaseFirestore): AddLoadRepository = AddLoadRepositoryImpl(firestore = firestore)
}
