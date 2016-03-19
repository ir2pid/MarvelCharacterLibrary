package noisyninja.com.marvelcharacterlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import noisyninja.com.marvelcharacterlibrary.components.SearchCharacterListAdapter;
import noisyninja.com.marvelcharacterlibrary.interfaces.INetworkCallback;
import noisyninja.com.marvelcharacterlibrary.models.CharacterDataWrapper;
import noisyninja.com.marvelcharacterlibrary.utils.NoisyNetwork;
import noisyninja.com.marvelcharacterlibrary.utils.NoisyUtils;

/**
 * To lookup characters based on alphabet search
 * Created by ir2pid on 17/03/16.
 */
public class SearchActivity extends Activity implements INetworkCallback, View.OnClickListener {

    Context mContext;
    Activity mActivity;
    CharacterDataWrapper mCharacterDataWrapper;

    Button back;
    EditText editText;
    RecyclerView mRecyclerView;
    String mSearchText;
    Button clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        mContext = this;
        mActivity = this;
        clear = (Button) findViewById(R.id.clear);
        back = (Button) findViewById(R.id.back);
        editText = (EditText) findViewById(R.id.editText);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoisyUtils.backPress(mActivity);
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                mSearchText = s.toString();
                if (mSearchText.length() > 0) {
                    clear.setVisibility(View.VISIBLE);
                    search();
                } else {
                    clear.setVisibility(View.INVISIBLE);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

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
            case GET_CHARACTERS: {

                mCharacterDataWrapper = (CharacterDataWrapper) NoisyUtils.getFromJson(o, CharacterDataWrapper.class);
                mRecyclerView.setAdapter(new SearchCharacterListAdapter(mCharacterDataWrapper, this));
                mRecyclerView.invalidate();
                syncAll();
                break;
            }
            case GET_ALL_CHARACTERS: {

                CharacterDataWrapper tCharacterDataWrapper = (CharacterDataWrapper) NoisyUtils.getFromJson(o, CharacterDataWrapper.class);
                merge(tCharacterDataWrapper);
                mRecyclerView.invalidate();
                syncAll();
                break;
            }
            default: {
                NoisyUtils.showDialog(this, NoisyConstants.INVALID_NETWORK_REQUEST, NoisyConstants.INVALID_NETWORK_REQUEST);
            }
        }
    }

    /**
     * to initiate character search with keyword
     */
    private void search() {
        NoisyNetwork.get(mContext, NoisyUtils.getSearchCharacterURI(0, mSearchText), NoisyConstants.Requests.GET_CHARACTERS, this);
    }


    /**
     * fetches subsequent characters in background
     */
    private void syncAll() {

        int total = mCharacterDataWrapper.getData().getTotal();
        int size = mCharacterDataWrapper.getData().getResults().size();
        if (total > size) {
            NoisyNetwork.getBackground(mContext, NoisyUtils.getSearchCharacterURI(mCharacterDataWrapper.getData().getResults().size() + 1, mSearchText), NoisyConstants.Requests.GET_ALL_CHARACTERS, this);
        }
    }

    /**
     * merges already recieved characters with new fetched ones
     *
     * @param characterDataWrapper new fetched characters
     */
    private void merge(CharacterDataWrapper characterDataWrapper) {
        mCharacterDataWrapper.getData().getResults().addAll(characterDataWrapper.getData().getResults());
        mCharacterDataWrapper.getData().setOffset(characterDataWrapper.getData().getOffset());
        mCharacterDataWrapper.getData().setCount(mCharacterDataWrapper.getData().getCount() + characterDataWrapper.getData().getCount());
    }

    /**
     * handles recycler view item clicks
     * @param view
     */
    @Override
    public void onClick(View view) {

        NoisyUtils.logD("click");
        int itemPosition = mRecyclerView.getChildPosition(view);
        noisyninja.com.marvelcharacterlibrary.models.Character character = mCharacterDataWrapper.getData().getResults().get(itemPosition);
        Toast.makeText(mContext, character.getName(), Toast.LENGTH_LONG).show();
        Intent intent = DetailAcitvity.getIntentInstance(mContext, mCharacterDataWrapper.getData().getResults().get(itemPosition));
        startActivity(intent);
    }

}