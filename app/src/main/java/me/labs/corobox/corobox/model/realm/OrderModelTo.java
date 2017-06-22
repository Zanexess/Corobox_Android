package me.labs.corobox.corobox.model.realm;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import me.labs.corobox.corobox.model.realm.common.IntegerWrap;

@RealmClass
public class OrderModelTo extends RealmObject {

    @SerializedName("uuid")
    @Expose
    private String UUID;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("created")
    @Expose
    private Integer created;
    @SerializedName("till")
    @Expose
    private Integer till;
    @SerializedName("address")
    @Expose
    private AddressModel addressModel;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("order")
    @Expose
    private RealmList<CategoryNumberModel> categoryNumberModel = new RealmList<>();

    private transient CardModel cardModel;
    private transient String type;
    private transient String date;

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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Integer getTill() {
        return till;
    }

    public void setTill(Integer till) {
        this.till = till;
    }

    public RealmList<CategoryNumberModel> getCategoryNumberModel() {
        return categoryNumberModel;
    }

    public void setCategoryNumberModel(RealmList<CategoryNumberModel> categoryNumberModel) {
        this.categoryNumberModel = categoryNumberModel;
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
