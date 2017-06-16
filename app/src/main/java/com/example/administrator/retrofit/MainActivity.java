package com.example.administrator.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.retrofit.pull.SwipyRefreshLayout;

import java.util.ArrayList;

import api.Api;
import bean.DataInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.administrator.retrofit.R.id.recyerview;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnRecyclerViewItemClickListener, SwipyRefreshLayout.OnRefreshListener {
    private ArrayList<DataInfo.Info> arrayList;
    private RecyclerView recyclerView;

    private MyAdapter adapter;

    private SwipyRefreshLayout refreshLayout;

    private LinearLayoutManager linearLayoutManager;

    private int pages = 1;

    private final int TOP_REFRESH = 1;
    private final int BOTTOM_REFRESH = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(recyerview);
        refreshLayout = (SwipyRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        arrayList = new ArrayList();
        initData(1);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyAdapter(this, arrayList);//实例化适配器
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);//适配数据
    }

    /**
     * 网络请求初始化数据
     *
     * @param pages
     */
    private void initData(int pages) {
        //使用retrofit配置api
        Retrofit retrofit = new Retrofit.Builder()//引入Retrofit网请框架
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())//引入Gson网析框架
                .build();
        Api api = retrofit.create(Api.class);
        Call<DataInfo> call = api.getData(5, pages);
        call.enqueue(new Callback<DataInfo>() {
            @Override
            public void onResponse(Call<DataInfo> call, Response<DataInfo> response) {
                arrayList.addAll(response.body().results);
                adapter.notifyDataSetChanged();
                Log.i("aaaa", arrayList.size() + "");
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<DataInfo> call, Throwable t) {
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onItemClick(View view, DataInfo.Info data) {
        Toast.makeText(MainActivity.this, "click item " + data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh(int index) {
        dataOption(TOP_REFRESH);
        Toast.makeText(this, "已经是最新数据", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoad(int index) {
        dataOption(BOTTOM_REFRESH);
        Toast.makeText(this, "加载完成", Toast.LENGTH_SHORT).show();
    }

    private void dataOption(int option) {
        switch (option) {
            case TOP_REFRESH:
                //下拉刷新
                arrayList.clear();
                initData(1);
                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
                pages++;
                initData(pages);
                break;
        }
        // adapter.notifyDataSetChanged();
    }

}
