package me.labs.corobox.corobox.di.components;

import dagger.Component;
import me.labs.corobox.corobox.di.modules.ApiModule;
import me.labs.corobox.corobox.di.scope.ApiScope;
import me.labs.corobox.corobox.presenter.main_screen.boxes_fragment.BoxesFragmentPresenter;
import me.labs.corobox.corobox.presenter.main_screen.categories_fragment.CategoryFragmentPresenter;

@ApiScope
@Component(
        dependencies = INetworkComponent.class,
        modules = ApiModule.class
)
public interface IApiComponent {
    void inject(BoxesFragmentPresenter boxesFragmentPresenter);
    void inject(CategoryFragmentPresenter categoryFragmentPresenter);
}

