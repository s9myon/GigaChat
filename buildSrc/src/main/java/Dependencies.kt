object Dependencies {
    ///////////////////////////////////////////////////
    //////////////////// android //////////////////////
    ///////////////////////////////////////////////////
    private const val coreVersion = "1.9.0"
    const val core = "androidx.core:core-ktx:$coreVersion"

    private const val appCompatVersion = "1.6.0"
    const val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"

    private const val materialVersion = "1.7.0"
    const val material = "com.google.android.material:material:$materialVersion"

    private const val constraintLayoutVersion = "2.1.4"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"

    private const val fragmentVersion = "1.5.5"
    const val fragment = "androidx.fragment:fragment-ktx:$fragmentVersion"

    private const val viewPagerVersion = "1.0.0"
    const val viewPager = "androidx.viewpager2:viewpager2:$viewPagerVersion"

    private const val swipeRefreshLayoutVersion = "1.1.0"
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:$swipeRefreshLayoutVersion"

    ///////////////////////////////////////////////////
    ///////////////////// rxjava //////////////////////
    ///////////////////////////////////////////////////
    private const val rxjavaVersion = "2.2.21"
    const val rxjava = "io.reactivex.rxjava2:rxjava:$rxjavaVersion"

    private const val rxkotlinVersion = "2.4.0"
    const val rxkotlin = "io.reactivex.rxjava2:rxkotlin:$rxkotlinVersion"

    private const val rxandroidVersion = "2.1.1"
    const val rxandroid = "io.reactivex.rxjava2:rxandroid:$rxandroidVersion"

    private const val rxrelayVersion = "2.1.1"
    const val rxrelay = "com.jakewharton.rxrelay2:rxrelay:$rxrelayVersion"

    ////////////////////////////////////////////////////
    //////////////////// retrofit //////////////////////
    ////////////////////////////////////////////////////
    private const val retrofitVersion = "2.9.0"
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    const val retrofitRxJavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"


    ///////////////////////////////////////////////////
    //////////////////// okhttp ///////////////////////
    ///////////////////////////////////////////////////
    private const val okhttpVersion = "5.0.0-alpha.3"
    const val okhttp = "com.squareup.okhttp3:okhttp:$okhttpVersion"
    const val okhttpLogInterceptor = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"

    ///////////////////////////////////////////////////
    ///////////////////// room ////////////////////////
    ///////////////////////////////////////////////////
    private const val roomVersion = "2.5.0"
    const val room = "androidx.room:room-runtime:$roomVersion"
    const val roomKapt = "androidx.room:room-compiler:$roomVersion"
    const val roomRxJavaAdapter = "androidx.room:room-rxjava2:$roomVersion"

    ///////////////////////////////////////////////////
    /////////////// kotlinSerialization ///////////////
    ///////////////////////////////////////////////////
    private const val kotlinSerializationJSONVersion = "1.4.1"
    const val kotlinSerializationJSON =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationJSONVersion"

    private const val kotlinSerializationRetrofitVersion = "0.8.0"
    const val kotlinSerializationRetrofit =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:$kotlinSerializationRetrofitVersion"

    ///////////////////////////////////////////////////
    //////////////////// dagger ///////////////////////
    ///////////////////////////////////////////////////
    private const val daggerVersion = "2.44"
    const val dagger = "com.google.dagger:dagger:$daggerVersion"
    const val daggerKapt = "com.google.dagger:dagger-compiler:$daggerVersion"

    ///////////////////////////////////////////////////
    ///////////////////// coil ////////////////////////
    ///////////////////////////////////////////////////
    private const val coilVersion = "2.2.2"
    const val coil = "io.coil-kt:coil:$coilVersion"

    ///////////////////////////////////////////////////
    //////////////////// timber ///////////////////////
    ///////////////////////////////////////////////////
    private const val timberVersion = "5.0.1"
    const val timber = "com.jakewharton.timber:timber:$timberVersion"

    ///////////////////////////////////////////////////
    /////////// viewBindingPropertyDelegate ///////////
    ///////////////////////////////////////////////////
    private const val viewBindingPropertyDelegateVersion = "1.5.6"
    const val viewBindingPropertyDelegate =
        "com.github.kirich1409:viewbindingpropertydelegate-noreflection:$viewBindingPropertyDelegateVersion"
    // viewBinding property delegate isn't building without these dependencies
    private const val viewModelVersion = "2.5.1"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel:$viewModelVersion"
    const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$viewModelVersion"
}