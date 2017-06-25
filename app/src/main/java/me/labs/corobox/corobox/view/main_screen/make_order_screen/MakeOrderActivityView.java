package me.labs.corobox.corobox.view.main_screen.make_order_screen;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.common.BaseActivity;
import me.labs.corobox.corobox.common.FragmentType;
import me.labs.corobox.corobox.common.adapters.CategoriesOrderFromRealmAdapter;
import me.labs.corobox.corobox.common.adapters.CategoriesOrderToRealmAdapter;
import me.labs.corobox.corobox.di.IHasComponent;
import me.labs.corobox.corobox.di.components.ICoroboxAppComponent;
import me.labs.corobox.corobox.di.components.activities.DaggerIMakeOrderActivityComponent;
import me.labs.corobox.corobox.di.components.activities.IMakeOrderActivityComponent;
import me.labs.corobox.corobox.di.modules.activities.MakeOrderActivityModule;
import me.labs.corobox.corobox.model.realm.AddressModel;
import me.labs.corobox.corobox.model.realm.Box;
import me.labs.corobox.corobox.model.realm.OrderModelFrom;
import me.labs.corobox.corobox.model.realm.OrderModelTo;
import me.labs.corobox.corobox.presenter.make_order_screen.IMakeOrderActivityPresenter;

import static me.labs.corobox.corobox.common.Utils.Utils.dateToTimestamp;
import static me.labs.corobox.corobox.common.Utils.Utils.stringToDate;

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

    private Realm realm;

    private String uuid;
    private String type;
    private CategoriesOrderToRealmAdapter categoriesOrderToRealmAdapter;
    private CategoriesOrderFromRealmAdapter categoriesOrderFromRealmAdapter;

    private OrderModelFrom orderModelFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        ButterKnife.bind(this);
        makeOrderActivityComponent.inject(this);
        realm = Realm.getDefaultInstance();

        setTitle("Новый заказ");

        uuid = getIntent().getExtras().getString("uuid");
        type = getIntent().getExtras().getString("type");

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
                }, 14, 00, true);
                tpd.show();
            }
        });

        final Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);
        final int mMonth = c.get(Calendar.MONTH);
        final int mYear = c.get(Calendar.YEAR);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog tpd = new DatePickerDialog(MakeOrderActivityView.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth + "." + month + "." + mYear);
                    }
                }, mYear, mMonth, mDay);
                tpd.show();
            }

        });

        date.setText(mDay + "." + mMonth + "." + mYear);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        AddressModel addressModel = realm.where(AddressModel.class).equalTo("useAsDefault", true).findFirst();
        if (addressModel != null) {
            addressStreet.setText(addressModel.getAddress());
            addressFloor.setText(addressModel.getFloor());
            addressFlat.setText(addressModel.getFlat());
            addressAccess.setText(addressModel.getAccess());

        } else {
            Toast.makeText(this, "Адрес по умолчанию не найден", Toast.LENGTH_SHORT).show();
        }

        if (type.equals("TO")) {
            OrderModelTo orderModel = realm.where(OrderModelTo.class).equalTo("uuid_inner", uuid).findFirst();
            categoriesOrderToRealmAdapter = new CategoriesOrderToRealmAdapter(orderModel.getCategoryNumberModel().createSnapshot(), presenter, false);
            recyclerView.setAdapter(categoriesOrderToRealmAdapter);
        } else if (type.equals("FROM")) {
            OrderModelFrom orderModelFrom = generateOrderFrom();
            categoriesOrderFromRealmAdapter = new CategoriesOrderFromRealmAdapter(orderModelFrom.getCategoryNumberModel(), presenter);
            recyclerView.setAdapter(categoriesOrderFromRealmAdapter);
        }
    }

    private OrderModelFrom generateOrderFrom() {
        orderModelFrom = new OrderModelFrom();
        orderModelFrom.setOrder_id(100000 + (5 + (int) (Math.random() * ((999999 - 100000) + 1))));

        AddressModel address = realm.where(AddressModel.class).equalTo("useAsDefault", true).findFirst();
        orderModelFrom.setAddressModel(address);

        try {
            orderModelFrom.setTill(dateToTimestamp(stringToDate(
                    date.getText().toString().trim() + " " +
                            time.getText().toString().trim(),
                    "dd.MM.yyyy HH:mm", Locale.getDefault())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        RealmList<Box> boxes = new RealmList<>();
        List<String> stuffUuid = getIntent().getExtras().getStringArrayList("list");
        if (stuffUuid != null) {
            for (String uuid : stuffUuid) {
                Box box = realm.where(Box.class).equalTo("uuid", uuid).findFirst();
                boxes.add(box);
            }
        }
        orderModelFrom.setCategoryNumberModel(boxes);
        return orderModelFrom;
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
            if (type.equals("TO")) {
                if (addressFlat.getText().toString().length() == 0) {
                    Toast.makeText(this, "Заполнены не все поля", Toast.LENGTH_SHORT).show();
                } else {
                    realm.beginTransaction();
                    OrderModelTo orderModel = realm.where(OrderModelTo.class).equalTo("uuid_inner", uuid).findFirst();
                    orderModel.setUuid_inner(UUID.randomUUID().toString());
                    AddressModel addressModel = realm.where(AddressModel.class).equalTo("useAsDefault", true).findFirst();
                    orderModel.setOrderId(100000 + (5 + (int) (Math.random() * ((999999 - 100000) + 1))));
                    try {
                        orderModel.setTill(dateToTimestamp(stringToDate(
                                date.getText().toString().trim() + " " +
                                        time.getText().toString().trim(),
                                "dd.MM.yyyy HH:mm", Locale.getDefault())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    orderModel.setAddressModel(addressModel);
                    orderModel.setType("FROM");
                    orderModel.setDate(date.getText().toString() + " " + time.getText().toString());
                    orderModel.setStatus("ORDERED");
                    realm.copyToRealm(orderModel);
                    realm.commitTransaction();

                    presenter.putOrder(orderModel);
                }
            } else if (type.equals("FROM")) {
                presenter.putOrderFrom(orderModelFrom);
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
        categoriesOrderToRealmAdapter.notifyDataSetChanged();
    }

    @Override
    public void finishActivity() {
        Toast.makeText(this, "Не выбрано ни одной категории для заказа", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void orderSuccessfullyAdded() {
        if (type.equals("TO")) {
            realm.beginTransaction();
            realm.where(OrderModelTo.class).equalTo("status", "ORDERED").findAll().deleteAllFromRealm();
            realm.commitTransaction();
        }
        Toast.makeText(this, "Заказ передан в обработку", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }
}
