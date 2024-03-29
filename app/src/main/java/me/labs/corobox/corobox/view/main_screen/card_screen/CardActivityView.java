package me.labs.corobox.corobox.view.main_screen.card_screen;

import android.app.Activity;
import android.os.Bundle;

import javax.inject.Inject;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.common.BaseActivity;
import me.labs.corobox.corobox.common.FragmentType;
import me.labs.corobox.corobox.di.IHasComponent;
import me.labs.corobox.corobox.di.components.ICoroboxAppComponent;
import me.labs.corobox.corobox.di.components.activities.DaggerICardActivityComponent;
import me.labs.corobox.corobox.di.components.activities.ICardActivityComponent;
import me.labs.corobox.corobox.di.modules.activities.CardActivityModule;
import me.labs.corobox.corobox.presenter.main_screen.card_screen.ICardActivityPresenter;

public class CardActivityView extends BaseActivity implements ICardActivityView, IHasComponent<ICardActivityComponent> {

    private ICardActivityComponent cardActivityComponent;

    @Inject
    ICardActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        cardActivityComponent.inject(this);

        if (presenter.getCurrentType() == null) {
            presenter.changeFragment(FragmentType.CARD);
        }
    }

    @Override
    protected void setupComponent(ICoroboxAppComponent appComponent) {
        cardActivityComponent = DaggerICardActivityComponent.builder()
                .iCoroboxAppComponent(appComponent)
                .cardActivityModule(new CardActivityModule(this))
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
    public ICardActivityComponent getComponent() {
        return cardActivityComponent;
    }
}
