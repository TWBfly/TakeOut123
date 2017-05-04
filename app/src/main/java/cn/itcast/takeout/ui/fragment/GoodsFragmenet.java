package cn.itcast.takeout.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.itcast.takeout.R;
import cn.itcast.takeout.model.dao.bean.GoodsInfo;
import cn.itcast.takeout.model.dao.bean.GoodsTypeInfo;
import cn.itcast.takeout.model.dao.bean.Seller;
import cn.itcast.takeout.persenter.GoodsPersent;
import cn.itcast.takeout.ui.adapter.LeftGoodsRecyclerAdapter;
import cn.itcast.takeout.ui.adapter.RightGoodsListAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
/**商品*/
public class GoodsFragmenet extends BaseFragment {
    @BindView(R.id.rv_goods_type)
    RecyclerView rvGoodsType;
    @BindView(R.id.slhlv)
    StickyListHeadersListView slhlv;
    private Seller seller;
    private RightGoodsListAdapter rightGoodsListAdapter;
    private int id = -1;
    private LeftGoodsRecyclerAdapter leftGoodsRecyclerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        seller = (Seller) getArguments().getSerializable("seller");
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(container.getContext(), R.layout.fragment_goods, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        leftGoodsRecyclerAdapter = new LeftGoodsRecyclerAdapter(this);
        rvGoodsType.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvGoodsType.setAdapter(leftGoodsRecyclerAdapter);
        rightGoodsListAdapter = new RightGoodsListAdapter();
        slhlv.setAdapter(rightGoodsListAdapter);
        setOnItemClickRight();
        GoodsPersent goodsPersent = new GoodsPersent(leftGoodsRecyclerAdapter, seller, rightGoodsListAdapter);
        goodsPersent.getGoodsData((int) seller.getId());
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 点击左侧传过来的id
     */
    public void setOnItemClickLeft(int id) {
        this.id = id;
        //将左侧的id与右侧的typeid进行比较
        //goodsInfoList.get(position).getTypeId();
        //如果id不为-1,则说明点中的类型有效
        if (id != -1) {
            //获取右侧数据
            List<GoodsInfo> goodsInfoList = rightGoodsListAdapter.getData();
            for (int i = 0; i < goodsInfoList.size(); i++) {
                int typeId = goodsInfoList.get(i).getTypeId();
                if (id == typeId) {
                    //匹配到了id一致的条目,则让右侧的listView滚动至指定位置
                    slhlv.setSelection(i);
                    break;
                }
            }
        }
    }

    public void setOnItemClickRight() {
        slhlv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //获取左侧数据
                List<GoodsTypeInfo> leftList = leftGoodsRecyclerAdapter.getData();
                //获取右侧数据
                List<GoodsInfo> goodsInfoList = rightGoodsListAdapter.getData();
                if (leftList != null && leftList.size() > 0 && goodsInfoList != null && goodsInfoList.size() > 0) {
                    int rigthId = goodsInfoList.get(firstVisibleItem).getTypeId();//获取右侧id
                    int leftId = leftList.get(leftGoodsRecyclerAdapter.currentPosition).getId();//获取左侧当前position的id
                    /**左侧id与右侧id不相等*/
                    if (leftId != rigthId) {
                        /**遍历左侧数据*/
                        for (int i = 0; i < leftList.size(); i++) {
                            //int id = leftList.get(i).getId(); //左侧id
                            if (rigthId == leftList.get(i).getId()) {
                                //左侧id与右侧id相等
                                leftGoodsRecyclerAdapter.currentPosition = i;
                                //如果2个id一致,则滚动到此,i就是索引值
                                rvGoodsType.smoothScrollToPosition(i);
                                leftGoodsRecyclerAdapter.notifyDataSetChanged();//刷新
                                break;

                            }
                        }
                    }

                }
            }
        });
    }


}
