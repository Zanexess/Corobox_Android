package me.labs.corobox.corobox.view.main_screen.address_fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import javax.inject.Inject;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.BaseFragment;
import me.labs.corobox.corobox.common.adapters.BoxesAdapter;
import me.labs.corobox.corobox.di.components.activities.IAddressActivityComponent;
import me.labs.corobox.corobox.di.components.activities.IMainActivityComponent;
import me.labs.corobox.corobox.model.Box;
import me.labs.corobox.corobox.presenter.main_screen.address_fragment.IAddressFragmentPresenter;
import me.labs.corobox.corobox.presenter.main_screen.boxes_fragment.IBoxesFragmentPresenter;
import me.labs.corobox.corobox.view.main_screen.boxes_fragment.IBoxesFragmentView;

public class AddressFragmentView extends BaseFragment implements IAddressFragmentView {

    @Inject
    IAddressFragmentPresenter presenter;

    private View view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_address, container, false);
            initComponents();
        }
        return view;
    }

    private void initComponents() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IAddressActivityComponent.class).inject(this);
        presenter.init(this);

    }

    @Override
    public Activity provideActivity() {
        return getActivity();
    }

}
