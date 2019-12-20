package git.oversadboy.anotherpictures.dager.components

import dagger.Component
import git.oversadboy.anotherpictures.dager.ViewModelFactory
import git.oversadboy.anotherpictures.dager.modules.AppModule
import git.oversadboy.anotherpictures.dager.modules.ViewModelModule
import git.oversadboy.anotherpictures.presentation.activity.BaseActivity
import git.oversadboy.anotherpictures.ui.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(baseActivity: BaseActivity)
    fun inject(viewModelFactory: ViewModelFactory)

}