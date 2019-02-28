package vn.bongtran.be.interfaces;

import java.util.List;

import vn.bongtran.be.model.CardModel;
import vn.bongtran.be.model.ErrorModel;

public interface OnGetCardListCallBack {
    void onGetCardListSuccess(List<CardModel> cardModels);
    void onGetCardListFail(ErrorModel errorDto);
}
