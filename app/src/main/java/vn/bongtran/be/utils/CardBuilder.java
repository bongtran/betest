package vn.bongtran.be.utils;

import java.util.ArrayList;
import java.util.Calendar;

import vn.bongtran.be.model.CardDetailModel;
import vn.bongtran.be.model.CardLiteModel;
import vn.bongtran.be.model.CardModel;

public class CardBuilder {
    public static ArrayList<CardLiteModel> convertToCardLite(ArrayList<CardModel> cardModels, ArrayList<CardDetailModel> cardDetails){
        ArrayList<CardLiteModel> result = new ArrayList<>();
        for (CardModel c : cardModels){
            result.add(build(c, cardDetails));
        }
        return result;
    }

    public static CardLiteModel build(CardModel cardModel, ArrayList<CardDetailModel> cardDetails) {
        CardLiteModel cardLiteModel = new CardLiteModel();
        cardLiteModel.setId(cardModel.getId());
        cardLiteModel.setAddress(cardModel.getAddress());
        cardLiteModel.setAvatar(cardModel.getAvatar());
        cardLiteModel.setMobile(cardModel.getMobile());
        cardLiteModel.setGender(cardModel.getGender());
        cardLiteModel.setName(cardModel.getFirst_name() + " " + cardModel.getLast_name());
        cardLiteModel.setPosition(BindingUtils.getCardPosition());
        CardDetailModel matchedCardDetail = matchCardDetail(cardModel, cardDetails);
        Calendar dob = Utils.convertStringToDate(matchedCardDetail.getRegistered());
        dob.add(Calendar.YEAR, 0 - matchedCardDetail.getAge());
        cardLiteModel.setAbout(matchedCardDetail.getAbout());
        cardLiteModel.setCompany(matchedCardDetail.getCompany());
        cardLiteModel.setDob(Utils.convertDateToString(dob));
        return cardLiteModel;
    }

    private static CardDetailModel matchCardDetail(CardModel cardModel, ArrayList<CardDetailModel> cardDetails) {
        ArrayList<CardDetailModel> tmp = new ArrayList<>();

        for (CardDetailModel c : cardDetails) {
            if (cardModel.getGender().toLowerCase().equals(c.getGender().toLowerCase()))
                tmp.add(c);
        }

        if (tmp.size() == 0) {
            return cardDetails.get(0);
        } else {
            int index = cardModel.getId() % tmp.size();
            return tmp.get(index);
        }
    }
}
