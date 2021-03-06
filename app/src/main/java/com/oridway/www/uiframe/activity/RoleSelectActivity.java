package com.oridway.www.uiframe.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.oridway.www.uiframe.R;
import com.oridway.www.uiframe.adpter.RoleSelectAdapter;
import com.oridway.www.uiframe.bean.ClsRole;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 角色默认是多选的
 * 1.提交数据之后需要刷新列表
 */
public class RoleSelectActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "RoleSelectActivity";

    @BindView(R.id.tv_title_middle)
    TextView title;
    @BindView(R.id.title_left)
    ImageView back;
    @BindView(R.id.lv_role_list)
    ListView mListView;
    @BindView(R.id.edit_tv)
    TextView edit;

    private Context mContext;
    private RoleSelectAdapter mAdapter;
    private List<ClsRole> mClsRoleList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_list);
        ButterKnife.bind(this);

        initView();
        initData();
        initListener();
    }

    public void getOfflineData(int num) {

        List<ClsRole> clsNormalUsers = new ArrayList<>();

        for (int i = 20; i < num; i++) {
            ClsRole clsRole = new ClsRole();
            clsRole.setRowID("RowID:" + i);
            clsRole.setSysRoleID("sysRoleID:" + i);
            clsRole.setSysRoleName("sysRoleName:" + i);
            clsRole.setSysRoleDesc("sysRoleDesc:" + i);
            clsRole.setSysRoleType("sysRoleType:" + i);
            clsRole.setSysRoleTypeName("sysRoleTypeName:" + i);
            clsRole.setCreater("creater:" + i);
            clsNormalUsers.add(clsRole);
        }

        mClsRoleList.addAll(clsNormalUsers);
        mAdapter.notifyDataSetChanged();
    }

    private void initData() {

        mContext = this;
        mClsRoleList = new ArrayList<>();
        mAdapter = new RoleSelectAdapter(mContext, mClsRoleList);
        mListView.setAdapter(mAdapter);

        getOfflineData(40);
    }

    private void initView() {

        title.setText("选择角色");
        edit.setVisibility(View.VISIBLE);
        edit.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_black_24dp, 0);
    }

    private void initListener() {

        back.setOnClickListener(this);
        edit.setOnClickListener(this);

        mListView.setOnItemClickListener((parent, view, position, id) -> {
            ClsRole clsRole = mClsRoleList.get(position);
            clsRole.setIsChecked(!clsRole.getIsChecked());
            mAdapter.notifyDataSetChanged();
        });
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.title_left) {
            finish();
        }

        if (v.getId() == R.id.edit_tv) {
            ArrayList<ClsRole> roles = new ArrayList<>();
            for (ClsRole clsRole : mClsRoleList) {
                if (clsRole.getIsChecked()) {
                    roles.add(clsRole);
                }
            }
            Intent intent = new Intent(mContext, RegisterActivity.class);
            intent.putParcelableArrayListExtra("roles", roles);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
