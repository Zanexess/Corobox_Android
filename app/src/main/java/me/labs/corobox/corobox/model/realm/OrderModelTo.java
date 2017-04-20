package me.labs.corobox.corobox.model.realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class OrderModelTo extends RealmObject {

    private String UUID;
    private RealmList<Category> list = new RealmList<Category>();
    private AddressModel addressModel;
    private CardModel cardModel;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public RealmList<Category> getList() {
        return list;
    }

    public void setList(RealmList<Category> list) {
        this.list = list;
    }

    public AddressModel getAddressModel() {
        return addressModel;
    }

    public void setAddressModel(AddressModel addressModel) {
        this.addressModel = addressModel;
    }

    public CardModel getCardModel() {
        return cardModel;
    }

    public void setCardModel(CardModel cardModel) {
        this.cardModel = cardModel;
    }
}
