package cn.itcast.takeout.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.itcast.takeout.R;
import cn.itcast.takeout.model.dao.bean.HomeInfo;
import cn.itcast.takeout.model.dao.bean.Promotion;
import cn.itcast.takeout.model.dao.bean.Seller;
import cn.itcast.takeout.ui.activity.BusinessActivity;

public class HomeAdapter extends RecyclerView.Adapter {
    private static final int HEAD_ITEM = 0;
    private static final int SELLER_ITEM = 1;
    private static final int RECOMMEND_ITEM = 2;
    private HomeInfo mHomeInfo;

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            //头,轮播图
            return HEAD_ITEM;
        } else if (mHomeInfo.getBody().get(position - 1).type == 0) {
            //一般条目
            return SELLER_ITEM;
        } else {
            //分割线条目
            return RECOMMEND_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //布局
        switch (viewType) {
            case HEAD_ITEM:
                View view = View.inflate(parent.getContext(), R.layout.item_title, null);
                TitleHolder titleHolder = new TitleHolder(view);
                return titleHolder;
            case SELLER_ITEM:
                View sellerView = View.inflate(parent.getContext(), R.layout.item_seller, null);
                SellerHolder sellerHolder = new SellerHolder(sellerView);
                return sellerHolder;
            case RECOMMEND_ITEM:
                View divisionView = View.inflate(parent.getContext(), R.layout.item_division, null);
                RecommendHolder recommendHolder = new RecommendHolder(divisionView);
                return recommendHolder;

            default:
                break;
        }
        return null;
    }


    class TitleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.slider)
        SliderLayout slider;

        public TitleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setTitleData(this, itemView);
        }
    }

    class SellerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvCount)
        TextView tvCount;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;

        public SellerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class RecommendHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_division_title)
        TextView tvDivisionTitle;
        @BindView(R.id.tv1)
        TextView tv1;
        @BindView(R.id.tv2)
        TextView tv2;
        @BindView(R.id.tv3)
        TextView tv3;
        @BindView(R.id.tv4)
        TextView tv4;
        @BindView(R.id.tv5)
        TextView tv5;
        @BindView(R.id.tv6)
        TextView tv6;

        public RecommendHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case HEAD_ITEM:
                break;
            case SELLER_ITEM:
                //设置一般条目
                setSellerData(holder, position - 1);
                break;
            case RECOMMEND_ITEM:
                //设置分割线条目
                setRecommendData(holder, position - 1);
                break;

            default:
                break;

        }

    }

    private void setTitleData(RecyclerView.ViewHolder holder, View itemView) {
        //获取轮播图的地址
        ArrayList<Promotion> promotionList = mHomeInfo.getHead().getPromotionList();
        for (int i = 0; i < promotionList.size(); i++) {
            Promotion promotion = promotionList.get(i);
            //创建可以显示图片和链接地址的控件
            TextSliderView textSliderView = new TextSliderView(itemView.getContext());
            //设置描述和图片链接地址
            textSliderView.description(promotion.getInfo()).image(promotion.getPic());
            ((TitleHolder) holder).slider.addSlider(textSliderView);
        }
    }

    private void setRecommendData(RecyclerView.ViewHolder holder, int position) {
//        ((RecommendHolder) holder).tv1.setText(mHomeInfo.getBody().get(position).recommendInfos.get(0));
//        ((RecommendHolder) holder).tv2.setText(mHomeInfo.getBody().get(position).recommendInfos.get(1));
//        ((RecommendHolder) holder).tv3.setText(mHomeInfo.getBody().get(position).recommendInfos.get(2));
//        ((RecommendHolder) holder).tv4.setText(mHomeInfo.getBody().get(position).recommendInfos.get(3));
//        ((RecommendHolder) holder).tv5.setText(mHomeInfo.getBody().get(position).recommendInfos.get(4));
//        ((RecommendHolder) holder).tv6.setText(mHomeInfo.getBody().get(position).recommendInfos.get(5));
    }

    private void setSellerData(RecyclerView.ViewHolder holder, final int position) {
//        ((SellerHolder) holder).tvTitle.setText(mHomeInfo.getBody().get(position).getSeller().getName());
        ((SellerHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seller seller = mHomeInfo.getBody().get(position).getSeller();
                Intent intent = new Intent(v.getContext(), BusinessActivity.class);
                intent.putExtra("seller",seller);
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        //健壮性检查
        if (mHomeInfo != null && mHomeInfo.getBody() != null && mHomeInfo.getBody().size() > 0) {
            //加1是给0的轮播图使用
            return mHomeInfo.getBody().size() + 1;
        }
        return 0;
    }

    public void setData(HomeInfo homeInfo) {
        this.mHomeInfo = homeInfo;
        notifyDataSetChanged();
    }
}
