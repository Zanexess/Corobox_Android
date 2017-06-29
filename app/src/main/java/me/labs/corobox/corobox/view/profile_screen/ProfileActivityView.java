package me.labs.corobox.corobox.view.profile_screen;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.BaseActivity;
import me.labs.corobox.corobox.common.FragmentType;
import me.labs.corobox.corobox.di.IHasComponent;
import me.labs.corobox.corobox.di.components.ICoroboxAppComponent;
import me.labs.corobox.corobox.di.components.activities.DaggerIProfileActivityComponent;
import me.labs.corobox.corobox.di.components.activities.IProfileActivityComponent;
import me.labs.corobox.corobox.di.modules.activities.ProfileActivityModule;
import me.labs.corobox.corobox.presenter.profile_screen.IProfileActivityPresenter;

public class ProfileActivityView extends BaseActivity implements IProfileActivityView, IHasComponent<IProfileActivityComponent> {

    private IProfileActivityComponent profileActivityComponent;

    @Inject
    IProfileActivityPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileActivityComponent.inject(this);

        presenter.changeFragment(FragmentType.PROFILE);
    }

    @Override
    public Activity provideActivity() {
        return this;
    }

    @Override
    public IProfileActivityComponent getComponent() {
        return profileActivityComponent;
    }

    @Override
    protected void setupComponent(ICoroboxAppComponent appComponent) {
        profileActivityComponent = DaggerIProfileActivityComponent.builder()
                .iCoroboxAppComponent(appComponent)
                .profileActivityModule(new ProfileActivityModule(this))
                .build();
    }


}
