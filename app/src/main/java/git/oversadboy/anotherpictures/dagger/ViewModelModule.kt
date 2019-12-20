package git.oversadboy.anotherpictures.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import git.oversadboy.anotherpictures.ui.images.ImagesViewModel

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ImagesViewModel::class)
    abstract fun bindMainViewModel(photoViewModel: ImagesViewModel): ViewModel

    //Add more ViewModels here
}