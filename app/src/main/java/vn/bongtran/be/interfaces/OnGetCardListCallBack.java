package vn.bongtran.be.interfaces;

import java.util.ArrayList;

import vn.bongtran.be.model.CardModel;
import vn.bongtran.be.model.ErrorModel;

public interface OnGetCardListCallBack {
    void onGetCardListSuccess(ArrayList<CardModel> cardModels);
    void onGetCardListFail(ErrorModel errorDto);
}
