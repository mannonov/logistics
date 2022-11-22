package com.myapp.logistics.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.myapp.logistics.repository.*
import com.myapp.logistics.util.LogisticsPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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

    @Provides
    fun provideLoadsRepository(firestore: FirebaseFirestore): LoadsRepository = LoadsRepositoryImpl(firebaseFirestore = firestore)

    @Provides
    fun provideAddDriverRepository(firestore: FirebaseFirestore): AddDriverRepository = AddDriverRepositoryImpl(firebaseFirestore = firestore)

    @Provides
    fun provideDriversRepository(firestore: FirebaseFirestore): DriversRepository = DriversRepositoryImpl(firestore = firestore)

    @Provides
    fun provideSignInRepository(firestore: FirebaseFirestore): SignInRepository = SignInRepositoryImpl(firestore = firestore)

    @Provides
    fun provideDriverLoadsRepository(firestore: FirebaseFirestore): DriverLoadsRepository = DriverLoadsRepositoryImpl(firestore = firestore)

    @Provides
    fun provideLoadInfoRepository(firestore: FirebaseFirestore): LoadInfoRepository = LoadInfoRepositoryImpl(firestore = firestore)

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(@ApplicationContext appContext: Context): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(appContext)

    @Provides
    fun provideMainRepository(fusedLocationProviderClient: FusedLocationProviderClient, firestore: FirebaseFirestore): MainRepository = MainRepositoryImpl(fusedLocationClient = fusedLocationProviderClient, firestore = firestore)
}
