package vn.bongtran.be.interfaces;

import java.util.List;

import vn.bongtran.be.model.CardDetailModel;
import vn.bongtran.be.model.ErrorModel;

public interface OnGetCardListCallBack {
    void onGetCardListSuccess(List<CardDetailModel> cardModels);
    void onGetCardListFail(ErrorModel errorDto);
}
