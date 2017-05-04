package cn.itcast.takeout.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.takeout.model.dao.bean.Seller;
import cn.itcast.takeout.ui.fragment.BaseFragment;
import cn.itcast.takeout.ui.fragment.GoodsFragmenet;
import cn.itcast.takeout.ui.fragment.SellerFragmetn;
import cn.itcast.takeout.ui.fragment.SuggestFragment;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private Seller seller;
    private String[] data;
    /**
     * 创建集合存储Fragment
     */
    private List<BaseFragment> fragmentList = new ArrayList<>();

    public MyFragmentPagerAdapter(FragmentManager fm, Seller seller) {
        super(fm);
        this.seller = seller;
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment baseFragment = null;
        switch (position) {
            case 0:
                baseFragment = new GoodsFragmenet();
                break;
            case 1:
                baseFragment = new SuggestFragment();
                break;
            case 2:
                baseFragment = new SellerFragmetn();
                break;

            default:
                break;

        }
        /**创建Bundle将商家seller传递到GoodsFragment*/
        Bundle bundle = new Bundle();
        bundle.putSerializable("seller",seller);
        baseFragment.setArguments(bundle);

        /**添加之前要做判断,之前是否已经添加过了*/
        if (!fragmentList.contains(baseFragment)) {
            fragmentList.add(baseFragment);
        }
        return baseFragment;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    /**
     * tab栏的索引position显示的内容
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return data[position];
    }
}
