package com.oridway.www.uiframe.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.oridway.www.uiframe.R;
import com.oridway.www.uiframe.adpter.RoleCardAdapter;
import com.oridway.www.uiframe.bean.ClsNormalUser;
import com.oridway.www.uiframe.bean.ClsRole;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends Activity implements View.OnClickListener, RoleCardAdapter.Callback {

    private final static String TAG = "RegisterActivity";

    @BindView(R.id.tv_title_middle)
    TextView title;
    @BindView(R.id.title_left)
    ImageView backButton;
    @BindView(R.id.lv_user_role)
    ListView mListView;
    @BindView(R.id.sl_user_new)
    ScrollView mScrollView;
    @BindView(R.id.tv_add_role)
    TextView addRole;
    @BindView(R.id.clear_roles)
    TextView clearRole;


    private Context mContext;
    private RoleCardAdapter mAdapter;
    private List<ClsRole> mClsRoleList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        initView();
        initData();
        initListener();

        mClsRoleList = getOfflineData(10);
        mAdapter = new RoleCardAdapter(mContext, mClsRoleList);
        mAdapter.setmCallback(this);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    private void initView() {
        title.setText("主页");
    }

    private void initData() {
        mContext = this;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        backButton.setOnClickListener(this);
        addRole.setOnClickListener(this);
        clearRole.setOnClickListener(this);

        mListView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                mScrollView.requestDisallowInterceptTouchEvent(false);
            } else {
                mScrollView.requestDisallowInterceptTouchEvent(true);
            }
            return false;
        });
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.title_left) {
            setResult(111);
            finish();
        }

        if (v.getId() == R.id.tv_add_role) {
            Intent intent = new Intent(mContext, RoleSelectActivity.class);
            startActivityForResult(intent, 123);
        }

        if (v.getId() == R.id.clear_roles) {
            mClsRoleList.clear();
            //这里如果写出 mClsRoleList = getOfflineData(1)；无效
            mClsRoleList.addAll(getOfflineData(1));
            mAdapter.notifyDataSetChanged();
        }
    }


    public List<ClsRole> getOfflineData(int num) {

        List<ClsRole> clsNormalUsers = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            ClsRole clsRole = new ClsRole();
            clsRole.setRowID("RowID:" + i);
            clsRole.setSysRoleID("sysRoleID:" + i);
            clsRole.setSysRoleName("sysRoleName:" + i);
            clsRole.setSysRoleDesc("sysRoleDesc:" + i);
            clsRole.setSysRoleType("sysRoleType:" + i);
            clsRole.setSysRoleTypeName("sysRoleTypeName:" + i);
            clsRole.setOrgID("orgID:" + i);
            clsRole.setOrgName("orgName:" + i);
            clsRole.setIsOff("isOff:" + i);
            clsRole.setSetOffDate("setOffDate:" + i);
            clsRole.setCreaterID("createrID:" + i);
            clsRole.setCreater("creater:" + i);
            clsRole.setModiManID("modiManID:" + i);
            clsRole.setModiManName("modiManName:" + i);
            clsNormalUsers.add(clsRole);
        }

        return clsNormalUsers;
    }

    @Override
    public void onClick(View view, int position) {

        if (mClsRoleList.size() == 1) {
            Toast.makeText(mContext, "至少保留一个角色", Toast.LENGTH_LONG).show();
        } else {
            mClsRoleList.remove(position);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 123 && resultCode == 321) {
            assert data != null;
            mClsRoleList.addAll(data.getParcelableArrayListExtra("checkedList"));
            mAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
