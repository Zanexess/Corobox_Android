package me.labs.corobox.corobox.view.profile_screen;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.BaseFragment;
import me.labs.corobox.corobox.di.components.activities.IAddressActivityComponent;
import me.labs.corobox.corobox.di.components.activities.IProfileActivityComponent;
import me.labs.corobox.corobox.model.realm.ProfileModel;
import me.labs.corobox.corobox.presenter.profile_screen.IProfileFragmentPresenter;
import me.labs.corobox.corobox.presenter.profile_screen.ProfileFragmentPresenter;

public class ProfileFragmentView extends BaseFragment implements IProfileFragmentView  {

    private View view;
    @BindView(R.id.phone) EditText phone;
    @BindView(R.id.first_name) EditText first_name;
    @BindView(R.id.last_name) EditText last_name;
    @BindView(R.id.save) TextView save;

    @Inject
    IProfileFragmentPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_profile, container, false);
            ButterKnife.bind(this, view);
            initComponents();
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return view;
    }

    private void initComponents() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileModel profileModel = new ProfileModel();
                profileModel.setFirst_name(first_name.getText().toString());
                profileModel.setLast_name(last_name.getText().toString());
                profileModel.setPhone(phone.getText().toString());
                presenter.putProfile(profileModel);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IProfileActivityComponent.class).inject(this);
        presenter.init(this);
        presenter.fetchData();
    }

    @Override
    public Activity provideActivity() {
        return getActivity();
    }

    @Override
    public void showProfileModel(ProfileModel body) {
        first_name.setText(body.getFirst_name() != null ? body.getFirst_name() : "");
        last_name.setText(body.getLast_name() != null ? body.getLast_name() : "");
        phone.setText(body.getPhone() != null ? body.getPhone() : "");
    }

}
