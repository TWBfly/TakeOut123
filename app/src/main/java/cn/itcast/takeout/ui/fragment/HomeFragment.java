package cn.itcast.takeout.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.ArgbEvaluator;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.itcast.takeout.R;
import cn.itcast.takeout.persenter.HomePersent;
import cn.itcast.takeout.ui.adapter.HomeAdapter;
/**首页*/
public class HomeFragment extends BaseFragment {
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.home_tv_address)
    TextView homeTvAddress;
    @BindView(R.id.ll_title_search)
    LinearLayout llTitleSearch;
    @BindView(R.id.ll_title_container)
    LinearLayout llTitleContainer;
    private HomeAdapter homeAdapter;
    private int sumY;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private float duration = 300f;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        sumY = 0;
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(container.getContext(), R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rvHome.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        homeAdapter = new HomeAdapter();
        rvHome.setAdapter(homeAdapter);
        rvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //如果是向上滚动，则顶部title的颜色要伴随滚动过程的变化而变化
                sumY += dy;
                //开始位置颜色
                int bgColor = 0X553190E8;
                if (sumY <= 0) {
                    //开始位置颜色
                    bgColor = 0X553190E8;
                } else if (sumY > 300) {
                    //终点颜色
                    bgColor = 0XFF3190E8;
                } else {
                    //设置颜色渐变效果,渐变颜色赋值给背景值
                    bgColor = (int) argbEvaluator.evaluate(sumY / duration, 0X553190E8, 0XFF3190E8);
                }
                llTitleContainer.setBackgroundColor(bgColor);
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        HomePersent homePersent = new HomePersent(this);
        homePersent.getHomeData();

        super.onViewCreated(view, savedInstanceState);
    }

    public HomeAdapter getHomeAdapter() {
        return homeAdapter;
    }
}
