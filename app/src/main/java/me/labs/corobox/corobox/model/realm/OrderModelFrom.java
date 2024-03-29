package me.labs.corobox.corobox.model.realm;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class OrderModelFrom extends RealmObject {

    @PrimaryKey
    @SerializedName("uuid")
    @Expose
    private String UUID;
    @SerializedName("order_id")
    @Expose
    private Integer order_id;
    @SerializedName("created")
    @Expose
    private Integer created;
    @SerializedName("till")
    @Expose
    private Long till;
    @SerializedName("address")
    @Expose
    private AddressModel addressModel;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("stuff")
    @Expose
    private RealmList<Box> categoryNumberModel = new RealmList<>();

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

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Long getTill() {
        return till;
    }

    public void setTill(Long till) {
        this.till = till;
    }

    public RealmList<Box> getCategoryNumberModel() {
        return categoryNumberModel;
    }

    public void setCategoryNumberModel(RealmList<Box> boxes) {
        this.categoryNumberModel = boxes;
    }

    public AddressModel getAddressModel() {
        return addressModel;
    }

    public void setAddressModel(AddressModel addressModel) {
        this.addressModel = addressModel;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }
}
