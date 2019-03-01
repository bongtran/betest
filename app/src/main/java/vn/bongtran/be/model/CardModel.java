package vn.bongtran.be.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import vn.bongtran.be.R;
import vn.bongtran.be.network.PicassoTrustAll;

import android.widget.ImageView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardModel extends BaseObservable {
    @SerializedName("id")
    @Expose
    @Bindable
    private int id;
    @SerializedName("first_name")
    @Expose
    @Bindable
    private String first_name;
    @SerializedName("last_name")
    @Expose
    @Bindable
    private String last_name;
    @SerializedName("email")
    @Expose
    @Bindable
    private String email;
    @SerializedName("gender")
    @Expose
    @Bindable
    private String gender;
    @SerializedName("mobile")
    @Expose
    @Bindable
    private String mobile;
    @SerializedName("address")
    @Expose
    @Bindable
    private String address;
    @SerializedName("avatar")
    @Expose
    @Bindable
    private String avatar;
    @BindingAdapter("avatar")
    public static void loadImage(ImageView view, String imageUrl) {
//        GlideApp.with(view.getContext())
//                .load(imageUrl)
//                .apply(RequestOptions.circleCropTransform())
//                .into(view);
        PicassoTrustAll.getInstance(view.getContext()).load(imageUrl).error(R.id.img_avatar).into(view);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
