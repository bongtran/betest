package vn.bongtran.be.model;

import android.widget.ImageView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Transformation;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import vn.bongtran.be.R;
import vn.bongtran.be.network.PicassoTrustAll;

public class CardLiteModel extends BaseObservable {
    @SerializedName("id")
    @Expose
    @Bindable
    private int id;
    @SerializedName("name")
    @Expose
    @Bindable
    private String name;
    @SerializedName("address")
    @Expose
    @Bindable
    private String address;
    @SerializedName("position")
    @Expose
    @Bindable
    private String position;
    @SerializedName("gender")
    @Expose
    @Bindable
    private String gender;
    @SerializedName("about")
    @Expose
    @Bindable
    private String about;
    @SerializedName("dob")
    @Expose
    @Bindable
    private String dob;
    @SerializedName("company")
    @Expose
    @Bindable
    private String company;
    @SerializedName("mobile")
    @Expose
    @Bindable
    private String mobile;
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
        Transformation transformation = new RoundedTransformationBuilder()
                .oval(true)
                .build();
        PicassoTrustAll.getInstance(view.getContext())
                .load(imageUrl)
                .fit()
                .transform(transformation)
                .error(R.id.img_avatar)
                .into(view);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
