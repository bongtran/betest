package vn.bongtran.be.model;

import com.google.gson.JsonArray;

public class CardResult {
    public CardResult(JsonArray card, JsonArray detail) {
        this.cards = card;
        this.cardDetails = detail;
    }

    public JsonArray cards;
    public JsonArray cardDetails;
}
