package me.labs.corobox.corobox.view.make_order_screen;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
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
import me.labs.corobox.corobox.view.main_screen.address_screen.AddressActivityView;

import static me.labs.corobox.corobox.common.Utils.Utils.dateToTimestamp;
import static me.labs.corobox.corobox.common.Utils.Utils.stringToDate;

public class MakeOrderActivityView extends BaseActivity implements IMakeOrderActivityView, IHasComponent<IMakeOrderActivityComponent> {

    private IMakeOrderActivityComponent makeOrderActivityComponent;

    @Inject
    IMakeOrderActivityPresenter presenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.address_street)
    TextView addressStreet;
    @BindView(R.id.address_flat)
    TextView addressFlat;
    @BindView(R.id.address_access)
    TextView addressAccess;
    @BindView(R.id.address_floor)
    TextView addressFloor;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.time)
    TextView time;

    @BindView(R.id.date_from)
    TextView date_from;
    @BindView(R.id.date_till)
    TextView date_till;
    @BindView(R.id.date_divider)
    TextView date_divider;

    @BindView(R.id.date_store)
    LinearLayout date_store;
    @BindView(R.id.date_arrive)
    LinearLayout date_arrive;
    @BindView(R.id.select_address)
    LinearLayout select_address;
    @BindView(R.id.price_periods)
    LinearLayout price_periods;

    private Realm realm;

    private String uuid;
    private String type;
    private CategoriesOrderToRealmAdapter categoriesOrderToRealmAdapter;
    private CategoriesOrderFromRealmAdapter categoriesOrderFromRealmAdapter;

    private OrderModelTo orderModel;
    private OrderModelFrom orderModelFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        ButterKnife.bind(this);
        makeOrderActivityComponent.inject(this);
        realm = Realm.getDefaultInstance();

        setTitle("Новый заказ");

        SharedPreferences sp = getSharedPreferences("order_info", MODE_PRIVATE);
        uuid = sp.getString("uuid", null);
        type = sp.getString("type", null);

        initComponents();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void initComponents() {
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);
        final int mMonth = c.get(Calendar.MONTH) + 1;
        final int mYear = c.get(Calendar.YEAR);

        date_arrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(MakeOrderActivityView.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        try {
                            Date dateNow = new Date();
                            final long date_arr = dateToTimestamp(stringToDate(date.getText().toString(), "dd.MM.yyyy", Locale.getDefault()));
                            final long date_t = dateToTimestamp(stringToDate(date_till.getText().toString(), "dd.MM.yyyy", Locale.getDefault()));
                            String iday = String.valueOf(dayOfMonth);
                            String imonth = String.valueOf(month + 1);
                            if (month + 1 < 10 && month + 1 > 1)
                                imonth = "0" + String.valueOf(month + 1);
                            if (dayOfMonth < 10 && dayOfMonth > 1)
                                iday = "0" + String.valueOf(dayOfMonth);

                            date.setText(iday + "." + imonth + "." + mYear);
                            date_from.setText(date.getText().toString());


                            presenter.setTill(date_from.getText().toString(), date_till.getText().toString());
                            presenter.countAll(orderModel.getCategoryNumberModel().createSnapshot(), presenter.countDays());

                            TimePickerDialog tpd = new TimePickerDialog(MakeOrderActivityView.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    time.setText(hourOfDay + ":" + ((minute > 10) ? minute : "0" + minute));
                                }
                            }, 10, 00, true);
                            tpd.show();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });

        String iday = String.valueOf(mDay);
        String imonth = String.valueOf(mMonth);
        if (mDay < 10 && mDay > 1)
            iday = "0" + String.valueOf(mDay);
        if (mMonth < 10 && mMonth > 1)
            imonth = "0" + String.valueOf(mMonth);

        date.setText(iday + "." + imonth + "." + mYear);
        date_from.setText(date.getText());
        date_till.setText(iday + ".0" + (mMonth + 1) + "." + mYear);

        date_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final long date_arr = dateToTimestamp(stringToDate(date.getText().toString(), "dd.MM.yyyy", Locale.getDefault()));
                    DatePickerDialog dpd = new DatePickerDialog(MakeOrderActivityView.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            try {
                                long date_t = dateToTimestamp(stringToDate((dayOfMonth + "." + (month + 1) + "." + year), "dd.MM.yyyy", Locale.getDefault()));
                                if (date_t - 60 * 60 * 24 >= date_arr) {
                                    String iday = String.valueOf(dayOfMonth);
                                    String imonth = String.valueOf(month + 1);
                                    if (month + 1 < 10 && month + 1 > 1)
                                        imonth = "0" + String.valueOf(month + 1);
                                    if (dayOfMonth < 10 && dayOfMonth > 1)
                                        iday = "0" + String.valueOf(dayOfMonth);


                                    date_till.setText(iday + "." + imonth + "." + mYear);
                                    presenter.setTill(date_from.getText().toString(), date_till.getText().toString());
                                    presenter.countAll(orderModel.getCategoryNumberModel().createSnapshot(), presenter.countDays());
                                } else {
                                    Toast.makeText(MakeOrderActivityView.this, "Минимальный период хранения 1 день", Toast.LENGTH_SHORT).show();
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, mYear, mMonth, mDay);
                    dpd.show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        select_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(recyclerView.getContext(), AddressActivityView.class);
                intent.putExtra("TYPE", "SELECT");
                MakeOrderActivityView.this.startActivityForResult(intent, 1);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        AddressModel addressModel = realm.where(AddressModel.class).equalTo("useAsDefault", true).findFirst();
        if (addressModel != null) {
            addressStreet.setText(addressModel.getAddress());
            addressFloor.setText(addressModel.getFloor());
            addressFlat.setText(addressModel.getFlat());
            addressAccess.setText(addressModel.getAccess());
        }

        if (type.equals("TO")) {
            orderModel = realm.where(OrderModelTo.class).equalTo("uuid_inner", uuid).findFirst();
            presenter.setTill(date_from.getText().toString(), date_till.getText().toString());
            categoriesOrderToRealmAdapter = new CategoriesOrderToRealmAdapter(orderModel, presenter, false);
            presenter.countAll(orderModel.getCategoryNumberModel().createSnapshot(), presenter.countDays());
            recyclerView.setAdapter(categoriesOrderToRealmAdapter);
        } else if (type.equals("FROM")) {
            price_periods.setVisibility(View.GONE);
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

        Set<String> stuffUuid = getApplicationContext().getSharedPreferences("order_info", MODE_PRIVATE)
                .getStringSet("set", null);
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

                        orderModel.setPaid_till(dateToTimestamp(stringToDate(
                                date_till.getText().toString(),
                                "dd.MM.yyyy",
                                Locale.getDefault())));
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
