package cn.itcast.takeout.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.itcast.takeout.R;
import cn.itcast.takeout.model.dao.bean.Seller;
import cn.itcast.takeout.ui.adapter.MyFragmentPagerAdapter;

public class BusinessActivity extends BaseActivity {

    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ib_menu)
    ImageButton ibMenu;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.bottomSheetLayout)
    BottomSheetLayout bottomSheetLayout;
    @BindView(R.id.imgCart)
    ImageView imgCart;
    @BindView(R.id.tvSelectNum)
    TextView tvSelectNum;
    @BindView(R.id.tvCountPrice)
    TextView tvCountPrice;
    @BindView(R.id.tvDeliveryFee)
    TextView tvDeliveryFee;
    @BindView(R.id.tvSendPrice)
    TextView tvSendPrice;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;
    @BindView(R.id.bottom)
    LinearLayout bottom;
    @BindView(R.id.fl_Container)
    FrameLayout flContainer;

    private String[] tabString ={"商品","评价","商家"};
    private Seller seller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        ButterKnife.bind(this);

        /**点击Item传递数据商家名称*/
        initData();
        /**初始化tab栏*/
        initTab();
        /**初始化viewpager*/
        initViewPager();
    }

    private void initViewPager() {
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),seller);
        myFragmentPagerAdapter.setData(tabString);
        vp.setAdapter(myFragmentPagerAdapter);


        /**tab栏与viewpager的绑定*/
        tabs.setupWithViewPager(vp);

    }
    private void initTab() {
        for (int i = 0; i < tabString.length; i++) {
            tabs.addTab(tabs.newTab().setText(tabString[i]));
        }
    }
    private void initData() {
        seller = (Seller) getIntent().getSerializableExtra("seller");
        tvTitle.setText("商品详情");
    }

    public void addImageViewToBusinessActivity(ImageView imageView, int width, int height) {
        flContainer.addView(imageView,width,height);
    }

    public int[] getImageCartLocation(){
        int[] imageCartLoacation = new int[2];
        imgCart.getLocationInWindow(imageCartLoacation);
        return imageCartLoacation;
    }
}
