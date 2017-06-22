package me.labs.corobox.corobox.view.main_screen.make_order_screen;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.common.BaseActivity;
import me.labs.corobox.corobox.common.FragmentType;
import me.labs.corobox.corobox.common.adapters.CategoriesOrderRealmAdapter;
import me.labs.corobox.corobox.common.serializers.AddressSerializer;
import me.labs.corobox.corobox.di.IHasComponent;
import me.labs.corobox.corobox.di.components.ICoroboxAppComponent;
import me.labs.corobox.corobox.di.components.activities.DaggerIMakeOrderActivityComponent;
import me.labs.corobox.corobox.di.components.activities.IMakeOrderActivityComponent;
import me.labs.corobox.corobox.di.modules.activities.MakeOrderActivityModule;
import me.labs.corobox.corobox.model.realm.AddressModel;
import me.labs.corobox.corobox.model.realm.CardModel;
import me.labs.corobox.corobox.model.realm.OrderModelTo;
import me.labs.corobox.corobox.presenter.make_order_screen.IMakeOrderActivityPresenter;

public class MakeOrderActivityView extends BaseActivity implements IMakeOrderActivityView, IHasComponent<IMakeOrderActivityComponent> {

    private IMakeOrderActivityComponent makeOrderActivityComponent;

    @Inject IMakeOrderActivityPresenter presenter;

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.address_street) EditText addressStreet;
    @BindView(R.id.address_flat) EditText addressFlat;
    @BindView(R.id.address_access) EditText addressAccess;
    @BindView(R.id.address_floor) EditText addressFloor;
    @BindView(R.id.price) TextView price;
    @BindView(R.id.date) TextView date;
    @BindView(R.id.time) TextView time;

    private String uuid;
    private CategoriesOrderRealmAdapter categoriesOrderRealmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        ButterKnife.bind(this);
        makeOrderActivityComponent.inject(this);

        setTitle("Новый заказ");

        uuid = getIntent().getExtras().getString("uuid");

        initComponents();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void initComponents() {
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(MakeOrderActivityView.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay + ":" + minute);
                    }
                }, 20, 00, true);
                tpd.show();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog tpd = new DatePickerDialog(MakeOrderActivityView.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + month);
                    }
                }, 2017, 04, 21);
                tpd.show();
            }

        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        Realm realm = Realm.getDefaultInstance();
        AddressModel addressModel = realm.where(AddressModel.class).equalTo("useAsDefault", true).findFirst();
        if (addressModel != null) {
            addressStreet.setText(addressModel.getAddress());
            addressFloor.setText(addressModel.getFloor());
            addressFlat.setText(addressModel.getFlat());
            addressAccess.setText(addressModel.getAccess());

        } else {
            Toast.makeText(this, "Адрес по умолчанию не найден", Toast.LENGTH_SHORT).show();
        }

        OrderModelTo orderModel = realm.where(OrderModelTo.class).equalTo("UUID", uuid).findFirst();

        categoriesOrderRealmAdapter = new CategoriesOrderRealmAdapter(orderModel.getCategoryNumberModel().createSnapshot(), presenter, false);

        recyclerView.setAdapter(categoriesOrderRealmAdapter);
    }

    @Override
    protected void setupComponent(ICoroboxAppComponent appComponent) {
        makeOrderActivityComponent = DaggerIMakeOrderActivityComponent.builder()
                .iCoroboxAppComponent(appComponent)
                .makeOrderActivityModule(new MakeOrderActivityModule(this))
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.order) {
            if (addressFlat.getText().toString().length() == 0) {
                Toast.makeText(this, "Заполнены не все поля", Toast.LENGTH_SHORT).show();
            } else {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                OrderModelTo orderModel = realm.where(OrderModelTo.class).equalTo("UUID", uuid).findFirst();
                orderModel.setUUID(UUID.randomUUID().toString());
                AddressModel addressModel = realm.where(AddressModel.class).equalTo("useAsDefault", true).findFirst();
                orderModel.setAddressModel(addressModel);
                orderModel.setType("FROM");
                orderModel.setDate(date.getText().toString() + " " + time.getText().toString());
                orderModel.setStatus("ORDERED");
                realm.copyToRealm(orderModel);
                realm.commitTransaction();

                Gson gson = null;
                try {
                    gson = new GsonBuilder()
                            .registerTypeAdapter(Class.forName("io.realm.AddressModelRealmProxy"), new AddressSerializer())
                            .create();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                Toast.makeText(this, "Заказ передан в обработку", Toast.LENGTH_SHORT).show();
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showPrice(String count) {
        price.setText(count);
    }

    @Override
    public void updateList() {
        categoriesOrderRealmAdapter.notifyDataSetChanged();
    }

    @Override
    public void finishActivity() {
        Toast.makeText(this, "Не выбрано ни одной категории для заказа", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        CoroboxApp.type = FragmentType.SETTINGS;
        finish();
    }

    @Override
    public IMakeOrderActivityComponent getComponent() {
        return makeOrderActivityComponent;
    }
}
