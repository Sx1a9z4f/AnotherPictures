package git.oversadboy.anotherpictures.dager.modules

import androidx.lifecycle.ViewModel
import com.yatochk.secure.app.dagger.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import git.oversadboy.anotherpictures.view_models.CollectionsViewModel
import git.oversadboy.anotherpictures.view_models.ImagesViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ImagesViewModel::class)
    internal abstract fun imagesViewModel(viewModel: ImagesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CollectionsViewModel::class)
    internal abstract fun browserViewModel(viewModel: CollectionsViewModel): ViewModel

}