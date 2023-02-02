object Dependencies {
    const val coreKtx = "androidx.core:core-ktx:1.9.0"
    const val appCompat = "androidx.appcompat:appcompat:1.6.0"
    const val material = "com.google.android.material:material:1.7.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

    const val navVersion = "2.5.3"
    const val navComponent = "androidx.navigation:navigation-fragment-ktx:$navVersion"
    const val navComponentUi = "androidx.navigation:navigation-ui:$navVersion"

    const val rxjava = "io.reactivex.rxjava2:rxjava:2.2.21"
    const val rxkotlin = "io.reactivex.rxjava2:rxkotlin:2.4.0"
    const val rxandroid = "io.reactivex.rxjava2:rxandroid:2.1.1"

    const val retrofitVersion = "2.9.0"
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    const val retrofitRxJavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"

    const val okhttpVersion = "5.0.0-alpha.3"
    const val okhttp = "com.squareup.okhttp3:okhttp:$okhttpVersion"
    const val okhttpLogInterceptor = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"

    const val kotlinSerializationJSON = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1"
    const val kotlinSerializationRetrofit = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"

    const val daggerVersion = "2.44"
    const val dagger = "com.google.dagger:dagger:$daggerVersion"
    const val daggerKapt = "com.google.dagger:dagger-compiler:$daggerVersion"

    const val coil = "io.coil-kt:coil:2.2.2"

    const val timber = "com.jakewharton.timber:timber:5.0.1"

    const val viewBindingPropertyDelegate = "com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.6"
    // viewBinding property delegate isn't building without these dependencies
    const val viewModelVersion = "2.5.1"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel:$viewModelVersion"
    const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$viewModelVersion"
}