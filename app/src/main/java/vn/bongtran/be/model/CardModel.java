package vn.bongtran.be.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CardModel {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("guid")
    @Expose
    private String guid;
    @SerializedName("isActive")
    @Expose
    private boolean isActive;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("age")
    @Expose
    private int age;
    @SerializedName("eyeColor")
    @Expose
    private String eyeColor;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("registered")
    @Expose
    private String registered;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    @SerializedName("greeting")
    @Expose
    private String greeting;
    @SerializedName("favoriteFruit")
    @Expose
    private String favoriteFruit;
    @SerializedName("tags")
    @Expose
    private ArrayList<String> tags = null;
    @SerializedName("status")
    @Expose
    private ArrayList<FriendModel> friends = null;
}
