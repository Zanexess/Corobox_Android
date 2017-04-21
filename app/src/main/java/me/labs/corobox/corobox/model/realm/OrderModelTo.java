package me.labs.corobox.corobox.model.realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import me.labs.corobox.corobox.model.realm.common.IntegerWrap;

@RealmClass
public class OrderModelTo extends RealmObject {

    @PrimaryKey
    private String UUID;
    private RealmList<Category> list = new RealmList<>();
    private RealmList<IntegerWrap> count = new RealmList<>();
    private AddressModel addressModel;
    private CardModel cardModel;
    private String status;
    private String type;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RealmList<IntegerWrap> getCount() {
        return count;
    }

    public void setCount(RealmList<IntegerWrap> count) {
        this.count = count;
    }

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
