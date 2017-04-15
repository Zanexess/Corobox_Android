package me.labs.corobox.corobox.di.components;

import dagger.Component;
import me.labs.corobox.corobox.di.modules.ApiModule;
import me.labs.corobox.corobox.di.scope.ApiScope;

@ApiScope
@Component(
        dependencies = INetworkComponent.class,
        modules = ApiModule.class
)
public interface IApiComponent {

}

