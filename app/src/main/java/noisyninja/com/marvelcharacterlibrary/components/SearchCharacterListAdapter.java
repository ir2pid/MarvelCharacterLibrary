package noisyninja.com.marvelcharacterlibrary.components;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import noisyninja.com.marvelcharacterlibrary.R;
import noisyninja.com.marvelcharacterlibrary.models.CharacterDataWrapper;
import noisyninja.com.marvelcharacterlibrary.utils.NoisyUtils;

/**
 * Created by ir2pid on 13/03/16.
 */
public class SearchCharacterListAdapter extends RecyclerView.Adapter<SearchCharacterListAdapter.ViewHolder> {
    private CharacterDataWrapper mCharacterDataWrapper;
    View.OnClickListener mOnClickListener;

    public SearchCharacterListAdapter(CharacterDataWrapper characterDataWrapper, View.OnClickListener onClickListener) {
        mCharacterDataWrapper = characterDataWrapper;
        mOnClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_search, parent, false);
        view.setOnClickListener(mOnClickListener);
        ViewHolder viewHolder = new ViewHolder(view, mCharacterDataWrapper);

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = mCharacterDataWrapper.getData().getResults().get(position).getName();

        String url = NoisyUtils.getImageName(mCharacterDataWrapper.getData().getResults().get(position).getThumbnail());

        holder.mTextview.setText(name);
        NoisyUtils.glideLoad(holder.mImageView.getContext(), holder.mImageView, url);

        NoisyUtils.makeAnimation(holder.mImageView, Techniques.BounceIn);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mCharacterDataWrapper.getData().getResults().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public interface OnItemClickListener {
            void onItemClick(noisyninja.com.marvelcharacterlibrary.models.Character item);
        }

        private OnItemClickListener listener;
        private final TextView mTextview;
        private final ImageView mImageView;
        private CharacterDataWrapper mCharacterDataWrapper;

        public ViewHolder(View view, CharacterDataWrapper characterDataWrapper) {
            super(view);
            mTextview = (TextView) itemView
                    .findViewById(R.id.textView);
            mImageView = (ImageView) itemView
                    .findViewById(R.id.imageView);
            mCharacterDataWrapper = characterDataWrapper;
        }

    }
}