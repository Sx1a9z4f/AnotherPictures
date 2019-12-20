package git.oversadboy.anotherpictures.dager

import android.app.Application
import git.oversadboy.anotherpictures.dager.components.AppComponent
import git.oversadboy.anotherpictures.dager.components.DaggerAppComponent
import git.oversadboy.anotherpictures.dager.modules.AppModule

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