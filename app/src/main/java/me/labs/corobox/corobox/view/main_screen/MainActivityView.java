package me.labs.corobox.corobox.view.main_screen;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.actionitembadge.library.ActionItemBadgeAdder;

import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import io.fabric.sdk.android.Fabric;
import me.labs.corobox.corobox.BuildConfig;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.common.ActivityType;
import me.labs.corobox.corobox.common.BaseActivity;
import me.labs.corobox.corobox.common.FragmentType;
import me.labs.corobox.corobox.di.IHasComponent;
import me.labs.corobox.corobox.di.components.ICoroboxAppComponent;
import me.labs.corobox.corobox.di.components.activities.DaggerIMainActivityComponent;
import me.labs.corobox.corobox.di.components.activities.IMainActivityComponent;
import me.labs.corobox.corobox.di.modules.activities.MainActivityModule;
import me.labs.corobox.corobox.presenter.main_screen.IMainActivityPresenter;
import me.labs.corobox.corobox.presenter.main_screen.MainActivityPresenter;

public class MainActivityView extends BaseActivity implements IMainActivityView, IHasComponent<IMainActivityComponent>, NavigationView.OnNavigationItemSelectedListener {

    private IMainActivityComponent mainActivityComponent;
    private NavigationView navigationView;
    private MenuItem deliveryMenu;
    private boolean visibilityDeliveryButton;

    @Inject
    IMainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityComponent.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        presenter.changeFragment(CoroboxApp.type);
        navigationView.setCheckedItem(R.id.nav_new_box);
    }

    @Override
    protected void setupComponent(ICoroboxAppComponent appComponent) {
        mainActivityComponent = DaggerIMainActivityComponent.builder()
                .iCoroboxAppComponent(appComponent)
                .mainActivityModule(new MainActivityModule(this))
                .build();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void updateMenu(int badgeCount) {
        ActionItemBadge.update(deliveryMenu, presenter.getDeliveryBadgeCount());
        invalidateOptionsMenu();
    }

    @Override
    public void setVisibilityDeliveryMenu(boolean isVisible) {
        visibilityDeliveryButton = isVisible;
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (presenter.getDeliveryBadgeCount() > 0 && visibilityDeliveryButton) {
            ActionItemBadge.update(this, menu.findItem(R.id.truck),
                    ContextCompat.getDrawable(getActivity(), R.drawable.ic_truck),
                    ActionItemBadge.BadgeStyles.DARK_GREY,
                    presenter.getDeliveryBadgeCount());
        } else {
            ActionItemBadge.hide(menu.findItem(R.id.truck));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.truck) {
            if (presenter.getDeliveryBadgeCount() > 0) {
                presenter.openCart(presenter.getHashMap());
            } else {
                // Никогда не будет
                Toast.makeText(this, "Вы не выбрали ни одного товара", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_my_boxes) {
            presenter.changeFragment(FragmentType.BOXES);
        } else if (id == R.id.nav_new_box) {
            presenter.changeFragment(FragmentType.NEW_BOX);
        } else if (id == R.id.nav_terms_of_use) {
            presenter.changeFragment(FragmentType.TERMS);
        } else if (id == R.id.nav_settings) {
            presenter.changeFragment(FragmentType.SETTINGS);
        } else if (id == R.id.nav_history) {
            presenter.changeFragment(FragmentType.HISTORY);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public IMainActivityComponent getComponent() {
        return mainActivityComponent;
    }
}
