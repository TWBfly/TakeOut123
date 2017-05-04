package cn.itcast.takeout.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.itcast.takeout.R;
import cn.itcast.takeout.ui.fragment.HomeFragment;
import cn.itcast.takeout.ui.fragment.MoreFragment;
import cn.itcast.takeout.ui.fragment.OrderFragment;
import cn.itcast.takeout.ui.fragment.UserFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.main_fragment_container)
    FrameLayout mainFragmentContainer;
    @BindView(R.id.main_bottome_switcher_container)
    LinearLayout mainBottomeSwitcherContainer;
    private ArrayList<Fragment> fragmentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initFragment();
        initTab();
        onClick(mainBottomeSwitcherContainer.getChildAt(0));//初始化第0个页面
    }

    private void initTab() {
        int childCount = mainBottomeSwitcherContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            //遍历所有孩子节点,设置点击事件
            FrameLayout childAt = (FrameLayout) mainBottomeSwitcherContainer.getChildAt(i);
            childAt.setOnClickListener(this);
        }
    }

    private void initFragment() {
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new HomeFragment());
        fragmentArrayList.add(new OrderFragment());
        fragmentArrayList.add(new UserFragment());
        fragmentArrayList.add(new MoreFragment());
    }

    /**
     * 底部Tab栏的点击事件
     */
    @Override
    public void onClick(View v) {
        int indexOfChild = mainBottomeSwitcherContainer.indexOfChild(v);//获取孩子的索引
        //根据索引改变UI
        changUi(indexOfChild);
        //根据索引改变Fragment
        changFragment(indexOfChild);

    }

    private void changFragment(int indexOfChild) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragmentArrayList.get(indexOfChild)).commit();
    }

    private void changUi(int indexOfChild) {
        int childCount = mainBottomeSwitcherContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = mainBottomeSwitcherContainer.getChildAt(i);//通过索引得到每个孩子的布局
            if (i == indexOfChild) {
                setEnable(childAt, false);
            } else {
                setEnable(childAt, true);
            }
        }
    }

    private void setEnable(View childAt, boolean isEnable) {
        childAt.setEnabled(isEnable);
        if (childAt instanceof ViewGroup) {
            int childCount = ((ViewGroup) childAt).getChildCount();
            for (int i = 0; i < childCount; i++) {
                setEnable(((ViewGroup) childAt).getChildAt(i), isEnable);
            }
        }
    }
}
