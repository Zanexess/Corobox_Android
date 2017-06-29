package me.labs.corobox.corobox.view.main_screen.settings_fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.ActivityType;
import me.labs.corobox.corobox.common.BaseFragment;
import me.labs.corobox.corobox.common.FragmentType;
import me.labs.corobox.corobox.di.components.activities.IMainActivityComponent;
import me.labs.corobox.corobox.presenter.main_screen.IMainActivityPresenter;
import me.labs.corobox.corobox.presenter.main_screen.settings.ISettingsFragmentPresenter;
import me.labs.corobox.corobox.presenter.main_screen.terms_of_use.ITermsFragmentPresenter;
import me.labs.corobox.corobox.view.main_screen.IMainActivityView;
import me.labs.corobox.corobox.view.main_screen.terms_of_use_fragment.ITermsFragmentView;

public class SettingsFragmentView extends BaseFragment implements ISettingsFragmentView {

    @Inject
    ISettingsFragmentPresenter presenter;

    @Inject
    IMainActivityPresenter presenterActivity;

    private View view;
    @BindView(R.id.change_avatar) TextView changeAvatar;
    @BindView(R.id.add_card) TextView addCard;
    @BindView(R.id.add_address) TextView addAddress;
    @BindView(R.id.account_image) ImageView accountImage;
    @BindView(R.id.switcher) SwitchCompat switcher;
    @BindView(R.id.profile) TextView profile;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_settings, container, false);
            ButterKnife.bind(this, view);
            initComponents();
        }
        return view;
    }

    private void initComponents() {
        changeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Открываем галерею, меняем аватар", Toast.LENGTH_SHORT).show();
            }
        });

        accountImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Открываем галерею, меняем аватар", Toast.LENGTH_SHORT).show();
            }
        });

        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterActivity.changeActivity(ActivityType.CARD);
            }
        });

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterActivity.changeActivity(ActivityType.ADDRESS);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterActivity.changeActivity(ActivityType.PROFILE);
            }
        });

        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Toast.makeText(getContext(), "Вы подписаны на уведомления", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "Уведомления отключены", Toast.LENGTH_SHORT).show();
            }
        });
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