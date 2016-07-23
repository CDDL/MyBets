package project.catalin.mybets.vistas.utils;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.PageIndicator;

import java.util.LinkedList;
import java.util.List;

import project.catalin.mybets.R;

/**
 * Created by CDD on 12/05/2016.
 */
public abstract class CustomPagerRecargable<Tipo> extends PagerAdapter implements IconPagerAdapter, ViewPager.OnPageChangeListener{
    List<Tipo> mListElementos;

    public CustomPagerRecargable() {
        mListElementos = new LinkedList<>();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public abstract Object instantiateItem(ViewGroup container, int position);

    @Override
    public abstract int getIconResId(int index);

    public void recargarDatos(List<Tipo> datos) {
        mListElementos = datos;
        notifyDataSetChanged();
    }

    public Tipo getItemPosition(int position) {
        return mListElementos.get(position);
    }

    @Override
    public int getCount() {
        return mListElementos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }



}
