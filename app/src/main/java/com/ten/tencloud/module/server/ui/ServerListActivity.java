package com.ten.tencloud.module.server.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ProviderBean;
import com.ten.tencloud.bean.ServerBean;
import com.ten.tencloud.module.server.adapter.RvServerAdapter;
import com.ten.tencloud.module.server.contract.ServerListContract;
import com.ten.tencloud.module.server.presenter.ServerListPresenter;
import com.ten.tencloud.widget.dialog.ServerFilterDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ServerListActivity extends BaseActivity implements ServerListContract.View {

    @BindView(R.id.rv_server)
    RecyclerView mRvServer;
    @BindView(R.id.et_search)
    EditText mEtSearch;

    private ServerListPresenter mPresenter;
    private RvServerAdapter mServerAdapter;
    private ServerFilterDialog mServerFilterDialog;

    private List<ProviderBean> providers;
    private Map<String, Map<String, Boolean>> select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_list);
        initTitleBar(true, "服务器列表", R.menu.menu_server_list, new OnMenuItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_add_server:
                        startActivityNoValue(mContext, ServerAddActivity.class);
                        break;
                }
            }
        });
        initView();
        mPresenter = new ServerListPresenter();
        mPresenter.attachView(this);
        //默认集群 1
        mPresenter.getServerList(1);
    }

    @OnClick({R.id.tv_refresh, R.id.tv_filter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_refresh:
                mPresenter.getServerList(1);
                break;
            case R.id.tv_filter:
                mServerFilterDialog.show();
                break;
        }
    }

    private void initView() {
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                }
                return false;
            }
        });
        mServerFilterDialog = new ServerFilterDialog(this);
        mServerFilterDialog.setServerFilterListener(new ServerFilterDialog.ServerFilterListener() {
            @Override
            public void getFilterData() {
                if (providers == null) {
                    mPresenter.getProvidersByCluster("1");
                }
            }

            @Override
            public void onOkClick(Map<String, Map<String, Boolean>> select) {
                ServerListActivity.this.select = select;
                search();
            }
        });
        mRvServer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mServerAdapter = new RvServerAdapter(this);
        mServerAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<ServerBean>() {
            @Override
            public void onObjectItemClicked(ServerBean serverBean, int position) {
                Intent intent = new Intent(mContext, ServerDetailActivity.class);
                intent.putExtra("name", serverBean.getName());
                intent.putExtra("id", serverBean.getId());
                startActivity(intent);
            }
        });
        mRvServer.setAdapter(mServerAdapter);
    }

    /**
     * 搜索
     */
    private void search() {
        String serverName = mEtSearch.getText().toString().trim();
        if (select == null) {
            mPresenter.search("1", serverName, null, null);
            return;
        }
        List<String> areas = new ArrayList<>();
        for (String key : select.keySet()) {
            Map<String, Boolean> map = select.get(key);
            for (String area : map.keySet()) {
                if (map.get(area)) {
                    areas.add(area);
                }
            }
        }
        mPresenter.search("1", serverName, areas, select.keySet());
    }

    @Override
    public void showServerList(List<ServerBean> servers) {
        mServerAdapter.setDatas(servers);
    }

    @Override
    public void showProviders(List<ProviderBean> providers) {
        this.providers = providers;
        mServerFilterDialog.setData(providers);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
