package noisyninja.com.marvelcharacterlibrary.components;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;

import noisyninja.com.marvelcharacterlibrary.NoisyConstants;
import noisyninja.com.marvelcharacterlibrary.R;
import noisyninja.com.marvelcharacterlibrary.ZoomActivity;
import noisyninja.com.marvelcharacterlibrary.models.ComicDataWrapper;
import noisyninja.com.marvelcharacterlibrary.models.ComicList;
import noisyninja.com.marvelcharacterlibrary.utils.NoisyUtils;

/**
 * Created by ir2pid on 16/03/16.
 */
public class ComicsAdapter extends PagerAdapter {
    private Context mContext;
    private int mItemBackground;
    private ComicDataWrapper mComicDataWrapper;

    public ComicsAdapter(Context context, ComicDataWrapper comicDataWrapper) {
        mContext = context;
        mComicDataWrapper = comicDataWrapper;
    }

    public int getCount() {
        return mComicDataWrapper.getData().getCount();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

   /* public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        //imageView.setLayoutParams(new Gallery.LayoutParams(300, 300));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new Gallery.LayoutParams(120, 150));
        String url = NoisyUtils.getImageName(mComicDataWrapper.getData().getResults().get(position).getThumbnail());
        NoisyUtils.glideLoad(mContext, imageView, url);
        return imageView;
    }*/

    @Override public float getPageWidth(final int position) { return(0.25f); }

    @Override
    public Object instantiateItem(View view, final int position) {
        final ImageView imageView = new ImageView(mContext);

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
       // imageView.setLayoutParams(new ViewPager.LayoutParams(150, 120));

        //imageView.setLayoutParams(ImageView.LayoutParams(120, 150));
        String url = NoisyUtils.getImageName(mComicDataWrapper.getData().getResults().get(position).getThumbnail());
        NoisyUtils.glideLoad(mContext, imageView, url);

        ((ViewPager) view).addView(imageView);
        imageView.setTag(position);

        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Log.i(ComicsAdapter.class.getSimpleName(), "clicked: " + position);
                showPopup(imageView.getContext(), position);
            }
        });

        return imageView;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {

        return view == ((ImageView) o);
    }

    @Override
    public void destroyItem(View collection, int position, Object view)
    {
        ((ViewPager)collection).removeView((ImageView)view);

    }


    private void showPopup(Context context, int pos) {
        Intent intent = new Intent(context, ZoomActivity.class);
        String url = NoisyUtils.getImageName(mComicDataWrapper.getData().getResults().get(pos).getThumbnail());
        intent.putExtra(NoisyConstants.URL_KEY, url);
        context.startActivity(intent);
    }
}