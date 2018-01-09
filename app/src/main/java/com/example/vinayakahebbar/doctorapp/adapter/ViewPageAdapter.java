package com.example.vinayakahebbar.doctorapp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vinayakahebbar.doctorapp.R;

public class ViewPageAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;
    String array[]={"The six best Doctors:Sunshine,water,Rest,Air,Exercise and Diet",
                    "The best Doctor gives the least Medicines",
                     "Medicine is a science of Uncertainity and an art of Probability",
                     "An apple a day keeps the doctor away but if the Doctor is cute forget the fruit"};

    public ViewPageAdapter(Context context, int[] resources) {
        this.context = context;
        this.resources = resources;
        inflater = LayoutInflater.from(context);
    }

    int[] resources;

    @Override
    public int getCount() {
        return resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int res = resources[position];
        View view = inflater.inflate(R.layout.main_view_pager,null);
        ImageView imageView = (ImageView)view.findViewById(R.id.img_view_pager);
        imageView.setImageResource(res);
        TextView textView=(TextView)view.findViewById(R.id.tv_view_pager);
        textView.setText(array[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View)object;
        container.removeView(view);
    }
}
