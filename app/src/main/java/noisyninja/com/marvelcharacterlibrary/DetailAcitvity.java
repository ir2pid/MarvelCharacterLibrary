package noisyninja.com.marvelcharacterlibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import noisyninja.com.marvelcharacterlibrary.components.ComicsAdapter;
import noisyninja.com.marvelcharacterlibrary.interfaces.INetworkCallback;
import noisyninja.com.marvelcharacterlibrary.models.Character;
import noisyninja.com.marvelcharacterlibrary.models.CharacterDataWrapper;
import noisyninja.com.marvelcharacterlibrary.models.ComicDataWrapper;
import noisyninja.com.marvelcharacterlibrary.utils.NoisyNetwork;
import noisyninja.com.marvelcharacterlibrary.utils.NoisyUtils;

/**
 * Activity to show character detail
 * Created by ir2pid on 16/03/16.
 */
public class DetailAcitvity extends Activity implements INetworkCallback {

    static Character mCharacter;
    Context mContext;
    ComicDataWrapper mComicDataWrapper;
    TextView name;
    TextView description;
    TextView notAvailable;
    ImageView imageView;
    ViewPager gallery;
    String comicsURI;

    /**
     * returns a new Intent to call the activity
     *
     * @param context   context of calling activity
     * @param character character data to be shown
     * @return Intent to show this class
     */
    public static Intent getIntentInstance(Context context, Character character) {
        mCharacter = character;
        NoisyUtils.logD(NoisyUtils.getToJson(mCharacter));
        return new Intent(context, DetailAcitvity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getActionBar().hide();
        mContext = this;
        gallery = (ViewPager) findViewById(R.id.gallery1);
        name = (TextView) findViewById(R.id.textView5);
        notAvailable = (TextView) findViewById(R.id.textView6);
        description = (TextView) findViewById(R.id.textView4);
        imageView = (ImageView) findViewById(R.id.imageView);

        name.setText(mCharacter.getName());
        String desc = mCharacter.getDescription();
        if (desc.length() < 1) {
            description.setText(NoisyUtils.getStringResource(mContext, R.string.not_available));
        } else {
            description.setText(mCharacter.getDescription());
        }
        if (mCharacter.getComics().getItems().size() < 1) {
            notAvailable.setVisibility(View.VISIBLE);
        } else {
            notAvailable.setVisibility(View.INVISIBLE);
            comicsURI = mCharacter.getComics().getCollectionURI();
            init(comicsURI);
        }
        description.setMovementMethod(new ScrollingMovementMethod());
        NoisyUtils.glideLoad(mContext, imageView, NoisyUtils.getImageName(mCharacter.getThumbnail()));

    }

    /**
     * fetches first batch of comics in foreground, and passes control to background for subsequent fetching
     *
     * @param resURI uri of comic resources
     */
    private void init(String resURI) {

        NoisyNetwork.get(mContext, NoisyUtils.getComicsURI(resURI, 0), NoisyConstants.Requests.GET_COMICS, this);

    }

    /**
     * fetches subsequent comics in background
     *
     * @param resURI uri of comic resources
     * @param offset offset of next set of comics
     */
    private void syncAll(String resURI, int offset) {

        int total = mComicDataWrapper.getData().getTotal();
        int size = mComicDataWrapper.getData().getResults().size();
        if (total > size) {
            NoisyNetwork.getBackground(mContext, NoisyUtils.getComicsURI(resURI, offset), NoisyConstants.Requests.GET_ALL_COMICS, this);
        }
    }

    /**
     * merges already recieved comics with new fetched ones
     *
     * @param comicDataWrapper new fetched comics
     */
    private void merge(ComicDataWrapper comicDataWrapper) {
        mComicDataWrapper.getData().getResults().addAll(comicDataWrapper.getData().getResults());
        mComicDataWrapper.getData().setOffset(comicDataWrapper.getData().getOffset());
        mComicDataWrapper.getData().setCount(mComicDataWrapper.getData().getCount() + comicDataWrapper.getData().getCount());
    }

    /**
     * handles network callbacks, both background and foreground calls
     *
     * @param o          response
     * @param requestTag tag of network call to cast response
     */
    @Override
    public void response(String o, String requestTag) {
        switch (NoisyConstants.Requests.valueOf(requestTag.toUpperCase())) {
            case GET_COMICS: {

                mComicDataWrapper = (ComicDataWrapper) NoisyUtils.getFromJson(o, ComicDataWrapper.class);

                if(mComicDataWrapper.getData().getResults().size() == 0){
                    notAvailable.setVisibility(View.VISIBLE);
                }else {
                    gallery.setAdapter(new ComicsAdapter(this, mComicDataWrapper));
                    gallery.invalidate();
                    syncAll(comicsURI, mComicDataWrapper.getData().getResults().size() + 1);
                }
                break;
            }
            case GET_ALL_COMICS: {

                ComicDataWrapper tComicDataWrapper = (ComicDataWrapper) NoisyUtils.getFromJson(o, ComicDataWrapper.class);
                merge(tComicDataWrapper);
                gallery.invalidate();
                syncAll(comicsURI, mComicDataWrapper.getData().getResults().size() + 1);
                break;
            }
            default: {
                NoisyUtils.showDialog(this, NoisyConstants.INVALID_NETWORK_REQUEST, NoisyConstants.INVALID_NETWORK_REQUEST);
            }
        }
    }

}
