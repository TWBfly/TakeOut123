package cn.itcast.takeout.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.itcast.takeout.R;
import cn.itcast.takeout.model.dao.bean.GoodsInfo;
import cn.itcast.takeout.ui.activity.BusinessActivity;
import cn.itcast.takeout.uitl.MyApplication;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class RightGoodsListAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private List<GoodsInfo> goodsInfoList;
    ViewHolder viewHolder;

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        //头布局
        TextView view = (TextView) View.inflate(parent.getContext(), R.layout.item_type_header, null);
        view.setText(goodsInfoList.get(position).getTypeName());
        return view;
    }

    @Override
    public long getHeaderId(int position) {
        return goodsInfoList.get(position).getTypeId();
    }

    @Override
    public int getCount() {
        if (goodsInfoList != null && goodsInfoList.size() > 0) {
            return goodsInfoList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return goodsInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /**1.convertView的复用
         * 2.ViewHolder减少findviewbyid
         * 3.减少字节码的加载次数,使用static
         * */

        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_goods, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvName.setText(goodsInfoList.get(position).getName());
        viewHolder.tvZucheng.setText(goodsInfoList.get(position).getForm());
        viewHolder.tvYueshaoshounum.setText(goodsInfoList.get(position).getMonthSaleNum() + "");
        viewHolder.tvNewprice.setText(goodsInfoList.get(position).getNewPrice() + "");
        viewHolder.tvOldprice.setText(goodsInfoList.get(position).getOldPrice() + "");
        Picasso.with(parent.getContext()).load(goodsInfoList.get(position).getIcon()).into(viewHolder.ivIcon);
        return convertView;
    }

    public void setData(List<GoodsInfo> goodsInfoList) {
        this.goodsInfoList = goodsInfoList;
        notifyDataSetChanged();
    }

    public List<GoodsInfo> getData() {
        return goodsInfoList;
    }

    public void setAnimation(ViewHolder holder, final View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e("被单击....");
            }
        });
        holder.ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**点击加号,实现减号由右向左滚动  平移--旋转--透明*/
                addGoods(view,v);
            }
        });
        holder.ibMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    class ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_zucheng)
        TextView tvZucheng;
        @BindView(R.id.tv_yueshaoshounum)
        TextView tvYueshaoshounum;
        @BindView(R.id.tv_newprice)
        TextView tvNewprice;
        @BindView(R.id.tv_oldprice)
        TextView tvOldprice;
        @BindView(R.id.ib_minus)
        ImageButton ibMinus;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.ib_add)
        ImageButton ibAdd;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            setAnimation(this, view);
        }

        //        @OnClick({R.id.ib_add, R.id.ib_minus})
        //        public void onClick(View v) {
        //            switch (v.getId()) {
        //                case R.id.ib_add://加号
        //                    /**点击加号,实现减号由右向左滚动  平移--旋转--透明*/
        //                    addGoods(v);
        //                    Logger.e("被点击啦,为什么不能被点击");
        //                    break;
        //                case R.id.ib_minus://减号
        //
        //                    break;
        //
        //                default:
        //                    break;
        //
        //            }
        //        }


    }


    private void addGoods(View view,View v) {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 2, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        AnimationSet set = new AnimationSet(true);//true 所有动画公用一个插值器
        set.addAnimation(translateAnimation);
        set.addAnimation(rotateAnimation);
        set.addAnimation(alphaAnimation);
        set.setDuration(500);
        viewHolder = new ViewHolder(view);
        viewHolder.ibMinus.startAnimation(set);
        viewHolder.ibMinus.setVisibility(View.VISIBLE);
        viewHolder.tvCount.setVisibility(View.VISIBLE);

        //定义一个二维数组
        int[] viewLocation = new int[2];
        v.getLocationInWindow(viewLocation);
        //2张图片,创建另外一张,一张显示,一张飞
        ImageView imageView = new ImageView(view.getContext());
        imageView.setBackgroundResource(R.drawable.button_add);
        //指定位置
        imageView.setX(viewLocation[0]);
        imageView.setY(viewLocation[1]- MyApplication.statusBarHeight);
        ((BusinessActivity) view.getContext()).addImageViewToBusinessActivity(imageView,v.getWidth(), v.getHeight());
        //起始位置 结束位置 飞一飞

        int[] sourceLocation = new int[2];
        imageView.getLocationInWindow(sourceLocation);

        int[] imageCartLocation = ((BusinessActivity) view.getContext()).getImageCartLocation();

        fly(imageView,sourceLocation,imageCartLocation);
    }

    private void fly(final ImageView imageView, int[] sourceLocation, int[] imageCartLocation) {
        int startX = sourceLocation[0];
        int startY = sourceLocation[1];

        int endX = imageCartLocation[0];
        int endY = imageCartLocation[1];

        //从起始位置到结束位置执行抛物线动画
        TranslateAnimation translateAnimationX = new TranslateAnimation(Animation.ABSOLUTE,0, Animation.ABSOLUTE,endX - startX, Animation.ABSOLUTE,0,Animation.ABSOLUTE, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());//线性 匀速
        TranslateAnimation translateAnimationY = new TranslateAnimation(Animation.ABSOLUTE,0, Animation.ABSOLUTE,0, Animation.ABSOLUTE,0,Animation.ABSOLUTE, endY-startY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());//加速
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(translateAnimationX);
        animationSet.addAnimation(translateAnimationY);
        animationSet.setDuration(500);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageView.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.setEnabled(true);
                imageView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(animationSet);
    }
}
