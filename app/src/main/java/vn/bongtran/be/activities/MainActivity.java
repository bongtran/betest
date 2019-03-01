package vn.bongtran.be.activities;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import vn.bongtran.be.R;
import vn.bongtran.be.adapter.CardAdapter;
import vn.bongtran.be.interfaces.OnGetCardListCallBack;
import vn.bongtran.be.model.CardModel;
import vn.bongtran.be.model.ErrorModel;
import vn.bongtran.be.network.HttpRequest;

public class MainActivity extends AppCompatActivity {
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
    ArrayList<CardModel> cards;

    //    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        recyclerView = findViewById(R.id.rv);

        cards = new ArrayList<CardModel>();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        cardAdapter = new CardAdapter(this, cards);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cardAdapter);

// Pagination
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        loadItems();
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
            Log.d(">>>", "" + visibleItemCount + " " + totalItemCount + " " + firstVisibleItemPosition);
            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
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
            public void onGetCardListSuccess(List<CardModel> cardModels) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setEnabled(false);
                        isLoading = false;
                        if (cardModels != null) {
                            cards.addAll(cardModels);
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
            public void onGetCardListSuccess(List<CardModel> cardModels) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setEnabled(false);
                        isLoading = false;
                        if (cardModels == null || cardModels.size() == 0) {
                            isLastPage = true;
                        } else {
                            cards.addAll(cardModels);
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
                        isLoading = false;
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setEnabled(false);
                    }
                });
            }
        }, currentPage);
    }

    @OnClick(R.id.fab)
    public void addCard() {

    }

//    @OnItemSelected(R.id.rv)
//    void onItemSelected(int position) {
//        // TODO ...
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
