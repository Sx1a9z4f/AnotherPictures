package git.oversadboy.anotherpictures.dagger

import android.app.Application
import git.oversadboy.anotherpictures.dagger.components.AppComponent
import git.oversadboy.anotherpictures.dagger.components.DaggerAppComponent
import git.oversadboy.anotherpictures.dagger.modules.AppModule

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

}