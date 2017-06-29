package me.labs.corobox.corobox.view.address_screen;

import android.app.Activity;
import android.os.Bundle;

import javax.inject.Inject;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.common.BaseActivity;
import me.labs.corobox.corobox.common.FragmentType;
import me.labs.corobox.corobox.di.IHasComponent;
import me.labs.corobox.corobox.di.components.ICoroboxAppComponent;
import me.labs.corobox.corobox.di.components.activities.DaggerIAddressActivityComponent;
import me.labs.corobox.corobox.di.components.activities.IAddressActivityComponent;
import me.labs.corobox.corobox.di.modules.activities.AddressActivityModule;
import me.labs.corobox.corobox.presenter.address_screen.IAddressActivityPresenter;

public class AddressActivityView extends BaseActivity implements IAddressActivityView, IHasComponent<IAddressActivityComponent> {

    private IAddressActivityComponent addressActivityComponent;

    @Inject
    IAddressActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        addressActivityComponent.inject(this);

        presenter.changeFragment(FragmentType.ADDRESS);
    }

    @Override
    protected void setupComponent(ICoroboxAppComponent appComponent) {
        addressActivityComponent = DaggerIAddressActivityComponent.builder()
                .iCoroboxAppComponent(appComponent)
                .addressActivityModule(new AddressActivityModule(this))
                .build();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onBackPressed() {
        CoroboxApp.type = FragmentType.SETTINGS;
        finish();
    }

    @Override
    public IAddressActivityComponent getComponent() {
        return addressActivityComponent;
    }
}
