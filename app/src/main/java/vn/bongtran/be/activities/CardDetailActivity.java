package vn.bongtran.be.activities;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import butterknife.ButterKnife;
import vn.bongtran.be.R;
import vn.bongtran.be.databinding.ActivityCardDetailBinding;
import vn.bongtran.be.model.CardLiteModel;
import vn.bongtran.be.utils.Statics;

public class CardDetailActivity extends AppCompatActivity {
    ActivityCardDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_card_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);

//        toolbar.setNavigationIcon(R.drawable.icon_left_arrow);
//        toolbar.setTitle("CARD DETAIL");
//        toolbar.setSubtitle("Sub");
//        toolbar.setLogo(R.drawable.ic_launcher);
        String cardString = getIntent().getStringExtra(Statics.CARD_EXTRA_NAME);
        CardLiteModel cardModel = new Gson().fromJson(cardString, CardLiteModel.class);
        binding.setCard(cardModel);
        binding.basic.setCard(cardModel);
        ButterKnife.bind(this);

        findViewById(R.id.btn_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
