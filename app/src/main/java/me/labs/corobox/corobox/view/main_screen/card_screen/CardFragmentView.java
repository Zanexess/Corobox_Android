package me.labs.corobox.corobox.view.main_screen.card_screen;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.BaseFragment;
import me.labs.corobox.corobox.di.components.activities.IMainActivityComponent;
import me.labs.corobox.corobox.presenter.main_screen.address_fragment.IAddressFragmentPresenter;
import me.labs.corobox.corobox.presenter.main_screen.card_fragment.ICardFragmentPresenter;
import me.labs.corobox.corobox.view.main_screen.address_fragment.IAddressFragmentView;

public class CardFragmentView extends BaseFragment implements ICardFragmentView {

    @Inject
    ICardFragmentPresenter presenter;

    private View view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_card, container, false);
            initComponents();
        }
        return view;
    }

    private void initComponents() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
        presenter.init(this);

    }

    @Override
    public Activity provideActivity() {
        return getActivity();
    }

}
