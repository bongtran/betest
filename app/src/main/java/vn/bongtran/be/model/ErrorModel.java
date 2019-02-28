package vn.bongtran.be.model;

import com.google.gson.annotations.SerializedName;

public class ErrorModel {
    public boolean unAuthentication;
    public boolean serverError=false;
    @SerializedName("code")
    public int code = 1;

    @SerializedName("message")
    public String message = "";
}
