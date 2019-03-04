package vn.bongtran.be.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.bongtran.be.R;
import vn.bongtran.be.adapter.CardAdapter;
import vn.bongtran.be.data.DataManager;
import vn.bongtran.be.interfaces.CardDetailRepo;
import vn.bongtran.be.interfaces.CardRepo;
import vn.bongtran.be.interfaces.OnGetCardListCallBack;
import vn.bongtran.be.interfaces.RecyclerViewClickListener;
import vn.bongtran.be.model.CardDetailModel;
import vn.bongtran.be.model.CardLiteModel;
import vn.bongtran.be.model.CardModel;
import vn.bongtran.be.model.CardResult;
import vn.bongtran.be.model.ErrorModel;
import vn.bongtran.be.network.HttpRequest;
import vn.bongtran.be.utils.CardBuilder;
import vn.bongtran.be.utils.LocalStore;
import vn.bongtran.be.utils.Statics;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener, SearchView.OnQueryTextListener {
    private final int PAGE_SIZE = 50;
    private boolean isLoading, isLastPage;
    private int currentPage = 1;
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.pBar)
    ProgressBar progressBar;
    @BindView(R.id.txt_error)
    TextView tvError;
    CardAdapter cardAdapter;
    LinearLayoutManager layoutManager;
//    ArrayList<CardModel> cards;
    ArrayList<CardDetailModel> cardDetails;
    ArrayList<CardLiteModel> cardLites;
    ArrayList<CardLiteModel> cardLitesLocal;

    //    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        int id = searchView.getContext()
                .getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        TextView textView = searchView.findViewById(id);
        textView.setTextColor(getResources().getColor(R.color.app_black));
        searchView.setQueryHint(Html.fromHtml("<font color = #CCCCD9>" +
                getResources().getString(R.string.txt_search) + "</font>"));
        searchView.setOnQueryTextListener(this);
        recyclerView = findViewById(R.id.rv);

        cardDetails = new ArrayList<>();
        cardLites = new ArrayList<>();
        cardLitesLocal = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        cardAdapter = new CardAdapter(this, cardLites, this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cardAdapter);

        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        getCards();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(LocalStore.getInstance().justAddCard()){
            CardLiteModel cardLiteModel = DataManager.sharedInstance().getCard();

            searchView.setQuery("", false);
            cardLites.add(0, cardLiteModel);
            cardAdapter.addCard(0, cardLiteModel);
            LocalStore.getInstance().putJustAddCard(false);
        }
        searchView.clearFocus();
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//            Log.d(">>>", "" + visibleItemCount + " " + totalItemCount + " " + firstVisibleItemPosition);
            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE + cardLitesLocal.size()) {
                    loadMoreItems();
                }
            }
        }
    };

    private void loadItems() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setEnabled(false);
        isLoading = true;
        HttpRequest.getInstance().getCardListPaging(new OnGetCardListCallBack() {
            @Override
            public void onGetCardListSuccess(final ArrayList<CardModel> cardModels) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setEnabled(false);
                        isLoading = false;
                        if (cardModels != null) {
                            cardAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }

            @Override
            public void onGetCardListFail(ErrorModel errorDto) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setEnabled(true);
                        isLoading = false;
                    }
                });
            }
        }, currentPage);
    }

    private void loadMoreItems() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setEnabled(false);
        currentPage++;
        isLoading = true;
        HttpRequest.getInstance().getCardListPaging(new OnGetCardListCallBack() {
            @Override
            public void onGetCardListSuccess(final ArrayList<CardModel> cardModels) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setEnabled(false);
                        isLoading = false;
                        if (cardModels == null || cardModels.size() == 0) {
                            isLastPage = true;
                        } else {
                            ArrayList<CardLiteModel> newCards = CardBuilder.convertToCardLite(cardModels, cardDetails);

                            cardAdapter.addCards(newCards);
                        }
                    }
                });

            }

            @Override
            public void onGetCardListFail(ErrorModel errorDto) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        isLoading = false;
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setEnabled(false);
                        currentPage--;
                    }
                });
            }
        }, currentPage);
    }

    @OnClick(R.id.fab)
    public void addCard() {
        Intent intent = new Intent(this, AddCardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        CardLiteModel cardModel = cardLites.get(position);

        Intent intend = new Intent(this, CardDetailActivity.class);
        intend.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intend.putExtra(Statics.CARD_EXTRA_NAME, new Gson().toJson(cardModel));
        startActivity(intend);
    }
//    @OnItemSelected(R.id.rv)
//    void onItemSelected(int position) {
//        CardModel cardModel = cards.get(position);
//        Intent intend = new Intent(this, CardDetailActivity.class);
//        intend.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intend.putExtra(Statics.CARD_EXTRA_NAME, new Gson().toJson(cardModel));
//        startActivity(intend);
//    }

    private void getCards() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setEnabled(false);
        isLoading = true;
        Retrofit repo = new Retrofit.Builder()
                .baseUrl("http://demo7527907.mockable.io")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        Retrofit repo2 = new Retrofit.Builder()
                .baseUrl("http://demo8104666.mockable.io")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        Observable<JsonArray> cardObservable = repo
                .create(CardRepo.class)
                .listCardDetails(currentPage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<JsonArray> cardDetailObservable = repo2
                .create(CardDetailRepo.class)
                .listCardDetails()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


        Observable.zip(cardObservable, cardDetailObservable, new BiFunction<JsonArray, JsonArray, CardResult>() {
            @Override
            public CardResult apply(JsonArray jsonElements, JsonArray jsonElements2) throws Exception {
                return new CardResult(jsonElements, jsonElements2);
            }

        }).subscribe(new Observer<CardResult>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(CardResult cardResult) {
                ArrayList<CardModel> cardModelList = new ArrayList<>();
                cardDetails = new ArrayList<>();
                try {
                    Type listType = new TypeToken<ArrayList<CardModel>>() {
                    }.getType();

                    cardModelList = new Gson().fromJson(cardResult.cards, listType);


                    listType = new TypeToken<List<CardDetailModel>>() {
                    }.getType();
                    cardDetails = new Gson().fromJson(cardResult.cardDetails, listType);
                    if (cardModelList != null && cardModelList.size() > 0 && cardDetails != null && cardDetails.size() > 0) {
                        cardLites = CardBuilder.convertToCardLite(cardModelList, cardDetails);
                    }

                    cardLitesLocal = DataManager.sharedInstance().getCards();

                    refresh();
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setEnabled(false);
                            isLoading = false;
                        }
                    });
                }
            }

            @Override
            public void onError(Throwable e) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setEnabled(false);
                isLoading = false;
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void refresh(){
        progressBar.setVisibility(View.GONE);
        recyclerView.setEnabled(false);
        isLoading = false;
        if (cardLites != null) {
            for (CardLiteModel c : cardLitesLocal) {
                cardLites.add(0, c);
            }
        }
        cardAdapter.addCards(cardLites);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        cardAdapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        cardAdapter.getFilter().filter(newText);
        return false;
    }
}
