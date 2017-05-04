package cn.itcast.takeout.persenter;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.takeout.model.dao.bean.BusinessInfo;
import cn.itcast.takeout.model.dao.bean.GoodsInfo;
import cn.itcast.takeout.model.dao.bean.GoodsTypeInfo;
import cn.itcast.takeout.model.dao.bean.ResponseInfo;
import cn.itcast.takeout.model.dao.bean.Seller;
import cn.itcast.takeout.ui.adapter.LeftGoodsRecyclerAdapter;
import cn.itcast.takeout.ui.adapter.RightGoodsListAdapter;
import retrofit2.Call;

public class GoodsPersent extends BasePersenter {
    private RightGoodsListAdapter rightGoodsListAdapter;
    private Seller seller;
    private LeftGoodsRecyclerAdapter leftGoodsRecyclerAdapter;
    private BusinessInfo businessInfo;
    private List<GoodsInfo> goodsInfoList;

    public GoodsPersent(LeftGoodsRecyclerAdapter leftGoodsRecyclerAdapter, Seller seller, RightGoodsListAdapter rightGoodsListAdapter) {
        this.leftGoodsRecyclerAdapter = leftGoodsRecyclerAdapter;
        this.seller = seller;
        this.rightGoodsListAdapter = rightGoodsListAdapter;
    }

    @Override
    protected void showError(String message) {
        //错误信息
    }

    @Override
    protected void parseJson(String data) {
        //商品json数据,进行解析
        Gson gson = new Gson();
        businessInfo = gson.fromJson(data, BusinessInfo.class);
        //左侧RecycleView展示数据
        leftGoodsRecyclerAdapter.setData(businessInfo.getList());
        //展示右侧自定义ListView数据
        prepareData();
        rightGoodsListAdapter.setData(goodsInfoList);

    }

    private void prepareData() {
        goodsInfoList = new ArrayList<>();
        //获取head数据
        for (int i = 0; i < businessInfo.getList().size(); i++) {
            //获取每个head所对应的数据
            GoodsTypeInfo goodsTypeInfo = businessInfo.getList().get(i);
            for (int j = 0; j < goodsTypeInfo.getList().size(); j++) {
                //获取每个数据
                GoodsInfo goodsInfo = goodsTypeInfo.getList().get(j);

                goodsInfo.setId((int) seller.getId());//获取商户id
                goodsInfo.setTypeName(goodsTypeInfo.getName());//需要指定产品分类名称
                goodsInfo.setTypeId(goodsTypeInfo.getId()); //需要指定产品分类id
                //将所有数据放入集合,这个集合就是要展示Adapter数据
                goodsInfoList.add(goodsInfo);
            }
        }
    }

    public void getGoodsData(int sellerId) {
        Call<ResponseInfo> goodsInfo = responseInfoAPI.getGoodsInfo(sellerId);
        goodsInfo.enqueue(new CallBackAdapter());
    }
}
