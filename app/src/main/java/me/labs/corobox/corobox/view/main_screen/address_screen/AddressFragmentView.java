package me.labs.corobox.corobox.view.main_screen.address_screen;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.braintreepayments.cardform.view.ErrorEditText;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.BaseFragment;
import me.labs.corobox.corobox.common.adapters.AddressRealmAdapter;
import me.labs.corobox.corobox.di.components.activities.IAddressActivityComponent;
import me.labs.corobox.corobox.model.realm.AddressModel;
import me.labs.corobox.corobox.presenter.main_screen.address_screen.IAddressFragmentPresenter;

public class AddressFragmentView extends BaseFragment implements IAddressFragmentView {

    @Inject
    IAddressFragmentPresenter presenter;

    private View view;
    @BindView(R.id.address_floor) ErrorEditText addressFloor;
    @BindView(R.id.address_access) EditText addressAccess;
    @BindView(R.id.address_flat) EditText addressFlat;
    @BindView(R.id.button) Button saveButton;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    SupportPlaceAutocompleteFragment autocompleteFragment;

    private AddressRealmAdapter addressAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_address, container, false);
            ButterKnife.bind(this, view);
            initComponents();
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return view;
    }

    private void initComponents() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).showLastDivider().build());

        autocompleteFragment = (SupportPlaceAutocompleteFragment)
                getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        ((View)view.findViewById(R.id.place_autocomplete_search_button)).setVisibility(View.GONE);
        final EditText editText = ((EditText)view.findViewById(R.id.place_autocomplete_search_input));
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0f);

        autocompleteFragment.setHint("Введите улицу");
        autocompleteFragment.setFilter(new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build());
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                autocompleteFragment.setText("Москва, " + place.getName());
            }

            @Override
            public void onError(Status status) {

            }
        });

        addressFlat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    saveButton.setVisibility(View.VISIBLE);
                } else {
                    saveButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressModel addressModel = new AddressModel();

                addressModel.setCity("Москва");
                addressModel.setAddress(editText.getText().toString().replace("Москва, ", ""));
                addressModel.setFlat(addressFlat.getText().toString());
                addressModel.setAccess(addressAccess.getText().toString());
                addressModel.setFloor(addressFloor.getText().toString());

                presenter.putAddress(addressModel);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IAddressActivityComponent.class).inject(this);
        presenter.init(this);
        presenter.fetchData();
    }

    @Override
    public Activity provideActivity() {
        return getActivity();
    }

    @Override
    public void setDefaultAddress(final Integer id) {
        presenter.fetchData();
    }

    @Override
    public void deleteAddress(final Integer id) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<AddressModel> results = realm.where(AddressModel.class).equalTo("id", id).findAll();
                results.deleteAllFromRealm();
                RealmResults<AddressModel> address = realm.where(AddressModel.class).findAll();
                addressAdapter = new AddressRealmAdapter(address.createSnapshot(), false, presenter);
                recyclerView.setAdapter(addressAdapter);
                addressAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void showData(List<AddressModel> body) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<AddressModel> addresses = realm.where(AddressModel.class).findAll();
        addressAdapter = new AddressRealmAdapter(addresses.createSnapshot(), false, presenter);
        recyclerView.setAdapter(addressAdapter);
        addressAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyData() {
        Toast.makeText(getContext(), "Нет данных", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void putSuccess() {
        addressFlat.setText("");
        addressFloor.setText("");
        addressAccess.setText("");
        autocompleteFragment.setText("");

        addressAdapter.notifyDataSetChanged();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Toast.makeText(getContext(), "Адрес сохранен!", Toast.LENGTH_SHORT).show();
    }
}
