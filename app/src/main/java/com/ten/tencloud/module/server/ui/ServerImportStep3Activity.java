package com.ten.tencloud.module.server.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ServerBatchBean;
import com.ten.tencloud.module.server.adapter.RvServerSelectServerAdapter;
import com.ten.tencloud.module.server.contract.ServerAddBatchStep3Contract;
import com.ten.tencloud.module.server.presenter.ServerAddBatchStep3Presenter;
import com.ten.tencloud.widget.dialog.ServerImportProgressDialog;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class ServerImportStep3Activity extends BaseActivity implements ServerAddBatchStep3Contract.View {


    @BindView(R.id.rv_server)
    RecyclerView mRvServer;

    private List<ServerBatchBean> mServers;
    private RvServerSelectServerAdapter mServerAdapter;
    private ServerAddBatchStep3Presenter mStep3Presenter;
    private ServerImportProgressDialog mServerImportProgressDialog;
    private List<ServerBatchBean> mSelects;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_add_batch_step3);
        initTitleBar(true, "批量添加云主机", "导入", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importServers();
            }
        });
        String data = getIntent().getStringExtra("data");
        mServers = TenApp.getInstance().getGsonInstance().fromJson(data, new TypeToken<List<ServerBatchBean>>() {
        }.getType());
        mStep3Presenter = new ServerAddBatchStep3Presenter();
        mStep3Presenter.attachView(this);
        initView();
        initData();
    }

    private void initView() {
        mRvServer.setLayoutManager(new LinearLayoutManager(this));
        mServerAdapter = new RvServerSelectServerAdapter(mContext);
        mRvServer.setAdapter(mServerAdapter);
    }

    private void initData() {
        mServerAdapter.setDatas(mServers);
    }

    private void importServers() {
        mSelects = mServerAdapter.getSelects();
        if (mSelects.size() == 0) {
            showMessage("请选择需要导入的服务器");
            return;
        }
        mServerImportProgressDialog = new ServerImportProgressDialog(mContext);
        mServerImportProgressDialog.show();
        mStep3Presenter.importServers(mSelects);
    }


    int count = 0;

    @Override
    public void importServersSuccess(Object o) {
        count = 0;
        Observable.interval(500, 2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long aLong) {
                        count++;
                        int progress = (int) (((float) count / mSelects.size()) * 100);
                        mServerImportProgressDialog.setProgress(progress);
                        mServerImportProgressDialog.setCount(count, mSelects.size());
                        return progress == 100;
                    }
                })
                .subscribe(new Action1<Boolean>() {

                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            mServerImportProgressDialog.dismiss();
                        }
                    }
                });
    }
}
