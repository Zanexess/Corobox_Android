package me.labs.corobox.corobox.view.main_screen.card_screen;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.cardform.OnCardFormValidListener;
import com.braintreepayments.cardform.view.CardForm;

import java.util.UUID;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.BaseFragment;
import me.labs.corobox.corobox.common.adapters.CardRealmAdapter;
import me.labs.corobox.corobox.di.components.activities.IMainActivityComponent;
import me.labs.corobox.corobox.model.realm.CardModel;
import me.labs.corobox.corobox.presenter.main_screen.address_fragment.IAddressFragmentPresenter;
import me.labs.corobox.corobox.presenter.main_screen.card_fragment.ICardFragmentPresenter;
import me.labs.corobox.corobox.view.main_screen.address_fragment.IAddressFragmentView;

public class CardFragmentView extends BaseFragment implements ICardFragmentView {

    @Inject
    ICardFragmentPresenter presenter;

    private View view;
    private CardForm cardForm;
    private Button button;
    private RecyclerView recyclerView;
    private CardRealmAdapter cardsAdapter;

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
        cardForm = (CardForm) view.findViewById(R.id.card_form);
        button = (Button) view.findViewById(R.id.button_ok);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardModel cardModel = new CardModel();
                cardModel.setUuid(UUID.randomUUID().toString());
                cardModel.setCardNumber(cardForm.getCardNumber());
                cardModel.setExpirationMonth(cardForm.getExpirationMonth());
                cardModel.setExpirationYear(cardForm.getExpirationYear());
                cardModel.setCvv(cardForm.getCvv());
                cardModel.setMobileNumber(cardForm.getMobileNumber());

                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealm(cardModel);
                realm.commitTransaction();

                setDefaultCard(cardModel.getUuid());

                RealmResults<CardModel> cards = realm.where(CardModel.class).findAll();
                cardsAdapter = new CardRealmAdapter(cards.createSnapshot(), false, presenter);
                recyclerView.setAdapter(cardsAdapter);
                cardsAdapter.notifyDataSetChanged();

                cardForm.closeSoftKeyboard();
                cardForm.getCardEditText().setText("");
                cardForm.getCvvEditText().setText("");
                cardForm.getMobileNumberEditText().setText("");
                cardForm.getCountryCodeEditText().setText("");
                cardForm.getExpirationDateEditText().setText("");

                Toast.makeText(getContext(), "Карта сохранена!", Toast.LENGTH_SHORT).show();
            }
        });

        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .mobileNumberRequired(true)
                .actionLabel("Добавить карту")
                .mobileNumberExplanation("Например 8926 193-24-21")
                .setup(provideActivity());

        cardForm.setOnCardFormValidListener(new OnCardFormValidListener() {
            @Override
            public void onCardFormValid(boolean valid) {
                if (valid) {
                    button.setVisibility(View.VISIBLE);
                } else {
                    button.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
        presenter.init(this);

        RealmResults<CardModel> cards = Realm.getDefaultInstance().where(CardModel.class).findAll();
        cardsAdapter = new CardRealmAdapter(cards.createSnapshot(), false, presenter);
        recyclerView.setAdapter(cardsAdapter);
    }

    @Override
    public Activity provideActivity() {
        return getActivity();
    }

    @Override
    public void deleteCard(final String uuid) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<CardModel> results = realm.where(CardModel.class).equalTo("uuid", uuid).findAll();
                results.deleteAllFromRealm();
                RealmResults<CardModel> cards = realm.where(CardModel.class).findAll();
                cardsAdapter = new CardRealmAdapter(cards.createSnapshot(), false, presenter);
                recyclerView.setAdapter(cardsAdapter);
                cardsAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setDefaultCard(final String uuid) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<CardModel> results = realm.where(CardModel.class).findAll();
                for (CardModel card : results)
                    card.setUseAsDefault(false);

                RealmResults<CardModel> resultsuuid = realm.where(CardModel.class).equalTo("uuid", uuid).findAll();
                resultsuuid.get(0).setUseAsDefault(true);

                RealmResults<CardModel> cards = realm.where(CardModel.class).findAll();
                cardsAdapter = new CardRealmAdapter(cards.createSnapshot(), false, presenter);
                recyclerView.setAdapter(cardsAdapter);
                cardsAdapter.notifyDataSetChanged();
            }
        });
    }

}
