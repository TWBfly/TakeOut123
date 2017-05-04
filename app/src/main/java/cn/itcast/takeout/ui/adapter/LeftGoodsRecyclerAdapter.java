package cn.itcast.takeout.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.itcast.takeout.R;
import cn.itcast.takeout.model.dao.bean.GoodsTypeInfo;
import cn.itcast.takeout.ui.fragment.GoodsFragmenet;

public class LeftGoodsRecyclerAdapter extends RecyclerView.Adapter {
    private GoodsFragmenet goodsFragmenet;

    public LeftGoodsRecyclerAdapter(GoodsFragmenet goodsFragmenet) {
        this.goodsFragmenet = goodsFragmenet;
    }

    private List<GoodsTypeInfo> list;
    public int currentPosition = 0;//默认是显示第0

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_type, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).type.setText(list.get(position).getName());
        /**判断有数据就显示*/
        if (list.get(position).getCount() > 0) {
            ((ViewHolder) holder).tvCount.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).tvCount.setText(list.get(position).getCount());
        } else {
            ((ViewHolder) holder).tvCount.setVisibility(View.INVISIBLE);
        }
        /*默认是第0*/
        if (position == currentPosition) {
            ((ViewHolder) holder).type.setTextColor(Color.RED);
            ((ViewHolder) holder).itemView.setBackgroundColor(Color.WHITE);
        } else {
            ((ViewHolder) holder).type.setTextColor(Color.BLACK);
            ((ViewHolder) holder).itemView.setBackgroundColor(Color.LTGRAY);
        }
        ((ViewHolder) holder).setPosition(position);

    }

    @Override
    public int getItemCount() {
        /**健壮性检查*/
        if (list != null && list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvCount)
        TextView tvCount;//分类数量
        @BindView(R.id.type)
        TextView type;//分类名称
        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**点击变背景和文字颜色*/
                    currentPosition = position;
                    notifyDataSetChanged();
                    /**点击左侧,右侧联动,滚动到对应的id*/
                    int id = list.get(position).getId();//获取左侧id
                    /**左侧与右侧有个共同的父类GoodsFragment*/
                    goodsFragmenet.setOnItemClickLeft(id);//点击左侧,将数据传给GoodsFragme
                }
            });
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    public void setData(List<GoodsTypeInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public List<GoodsTypeInfo> getData(){
        return list;
    }
}
