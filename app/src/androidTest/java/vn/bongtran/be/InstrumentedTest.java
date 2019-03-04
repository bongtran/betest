package vn.bongtran.be;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import vn.bongtran.be.activities.CardDetailActivity;
import vn.bongtran.be.data.DataManager;
import vn.bongtran.be.model.CardLiteModel;
import vn.bongtran.be.utils.LocalStore;
import vn.bongtran.be.utils.Statics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("vn.bongtran.be", appContext.getPackageName());
    }

    @Test
    public void testCardDetailIntent()  throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        CardLiteModel cardLite = new CardLiteModel();
        Intent intent = new Intent(appContext, CardDetailActivity.class);
        intent.putExtra(Statics.CARD_EXTRA_NAME, new Gson().toJson(cardLite));
        assertNotNull(intent);
        Bundle extras = intent.getExtras();
        assertNotNull(extras);
        assertEquals(new Gson().toJson(cardLite), extras.getString(Statics.CARD_EXTRA_NAME));
        assertNotNull(new Gson().fromJson(extras.getString(Statics.CARD_EXTRA_NAME), CardLiteModel.class));
    }

    @Test
    public void testLocalStore()  throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        LocalStore.getInstance().putJustAddCard(true);

        assertTrue(LocalStore.getInstance().justAddCard());

        LocalStore.getInstance().putJustAddCard(false);

        assertFalse(LocalStore.getInstance().justAddCard());
    }


    @Test
    public void getCardsLocal_isCorrect() {
        assertNotNull(LocalStore.getInstance().getCardLites());
    }

    @Test
    public void getCardsLocal() {
        assertNotNull(DataManager.sharedInstance().getCards());
    }
}
