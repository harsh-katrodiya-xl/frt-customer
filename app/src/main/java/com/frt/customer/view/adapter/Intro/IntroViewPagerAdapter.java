package com.frt.customer.view.adapter.Intro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.frt.customer.R;


public class IntroViewPagerAdapter extends PagerAdapter {
    Context context;
    String[] titles;
    String[] descs;

    private int[] GalImages = new int[]{
            R.drawable.ic_onboarding1,
            R.drawable.ic_onboarding2,
            R.drawable.ic_onboarding3,
            R.drawable.ic_onboarding4
    };

    public IntroViewPagerAdapter(Context context, String[] titles, String[] descs) {
        this.context = context;
        this.titles = titles;
        this.descs = descs;
    }

    @Override
    public int getCount() {
        return  GalImages.length;
    }
    @Override

    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_intro, null);

        ImageView imgIntro = (ImageView) itemView.findViewById(R.id.imgIntro);
        TextView txt_intro_title = (TextView) itemView.findViewById(R.id.tvIntroTitle);
        TextView txt_intro_desc = (TextView) itemView.findViewById(R.id.tvIntroDesc);

        imgIntro.setImageResource(GalImages[position]);
        ((ViewPager) container).addView(itemView);
        txt_intro_title.setText(titles[position]);
        txt_intro_desc.setText(descs[position]);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
