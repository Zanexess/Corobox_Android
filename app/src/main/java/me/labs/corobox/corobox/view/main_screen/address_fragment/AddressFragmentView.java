package me.labs.corobox.corobox.view.main_screen.address_fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.BaseFragment;
import me.labs.corobox.corobox.common.adapters.AddressRealmAdapter;
import me.labs.corobox.corobox.common.adapters.BoxesAdapter;
import me.labs.corobox.corobox.common.adapters.CardRealmAdapter;
import me.labs.corobox.corobox.di.components.activities.IAddressActivityComponent;
import me.labs.corobox.corobox.di.components.activities.IMainActivityComponent;
import me.labs.corobox.corobox.model.Box;
import me.labs.corobox.corobox.model.realm.AddressModel;
import me.labs.corobox.corobox.model.realm.CardModel;
import me.labs.corobox.corobox.presenter.main_screen.address_fragment.IAddressFragmentPresenter;
import me.labs.corobox.corobox.presenter.main_screen.boxes_fragment.IBoxesFragmentPresenter;
import me.labs.corobox.corobox.view.main_screen.boxes_fragment.IBoxesFragmentView;

public class AddressFragmentView extends BaseFragment implements IAddressFragmentView {

    @Inject
    IAddressFragmentPresenter presenter;

    private View view;
    private EditText addressStreet;
    private EditText addressFloor;
    private EditText addressAccess;
    private EditText addressFlat;
    private Button saveButton;
    private RecyclerView recyclerView;
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
            initComponents();
        }
        return view;
    }

    private void initComponents() {
        addressAccess = (EditText) view.findViewById(R.id.address_access);
        addressFlat = (EditText) view.findViewById(R.id.address_flat);
        addressFloor = (EditText) view.findViewById(R.id.address_floor);
        addressStreet = (EditText) view.findViewById(R.id.address_street);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

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

        saveButton = (Button) view.findViewById(R.id.button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressModel addressModel = new AddressModel();
                addressModel.setUuid(UUID.randomUUID().toString());
                addressModel.setStreet(addressStreet.getText().toString());
                addressModel.setFlat(addressFlat.getText().toString());
                addressModel.setAccess(addressAccess.getText().toString());
                addressModel.setFloor(addressFloor.getText().toString());

                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealm(addressModel);
                realm.commitTransaction();

                setDefaultAddress(addressModel.getUuid());

                RealmResults<AddressModel> addresses = realm.where(AddressModel.class).findAll();
                addressAdapter = new AddressRealmAdapter(addresses.createSnapshot(), false, presenter);
                recyclerView.setAdapter(addressAdapter);
                addressAdapter.notifyDataSetChanged();

                addressFlat.setText("");
                addressStreet.setText("");
                addressFloor.setText("");
                addressAccess.setText("");

                Toast.makeText(getContext(), "Адрес сохранен!", Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public void setDefaultAddress(final String uuid) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<AddressModel> results = realm.where(AddressModel.class).findAll();
                for (AddressModel addressModel : results)
                    addressModel.setUseAsDefault(false);

                RealmResults<AddressModel> resultsuuid = realm.where(AddressModel.class).equalTo("uuid", uuid).findAll();
                resultsuuid.get(0).setUseAsDefault(true);

                RealmResults<AddressModel> addressModels = realm.where(AddressModel.class).findAll();
                addressAdapter = new AddressRealmAdapter(addressModels.createSnapshot(), false, presenter);
                recyclerView.setAdapter(addressAdapter);
                addressAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void deleteAddress(final String uuid) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<AddressModel> results = realm.where(AddressModel.class).equalTo("uuid", uuid).findAll();
                results.deleteAllFromRealm();
                RealmResults<AddressModel> address = realm.where(AddressModel.class).findAll();
                addressAdapter = new AddressRealmAdapter(address.createSnapshot(), false, presenter);
                recyclerView.setAdapter(addressAdapter);
                addressAdapter.notifyDataSetChanged();
            }
        });
    }

}
