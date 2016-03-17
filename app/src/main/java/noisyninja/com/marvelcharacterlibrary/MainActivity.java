package noisyninja.com.marvelcharacterlibrary;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import noisyninja.com.marvelcharacterlibrary.NoisyConstants.Requests;
import noisyninja.com.marvelcharacterlibrary.components.CharacterListAdapter;
import noisyninja.com.marvelcharacterlibrary.interfaces.INetworkCallback;
import noisyninja.com.marvelcharacterlibrary.models.*;
import noisyninja.com.marvelcharacterlibrary.models.Character;
import noisyninja.com.marvelcharacterlibrary.utils.NoisyNetwork;
import noisyninja.com.marvelcharacterlibrary.utils.NoisyUtils;

public class MainActivity extends Activity implements INetworkCallback, View.OnClickListener {

    RecyclerView mRecyclerView;
    Context mContext;
    CharacterDataWrapper mCharacterDataWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        //appBarColor();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        init();
    }


    private void init() {

        NoisyNetwork.get(mContext, NoisyUtils.getCharacterURI(0), Requests.GET_CHARACTERS, this);
    }

    @Override
    public void response(String o, String requestTag) {
        switch (NoisyConstants.Requests.valueOf(requestTag.toUpperCase())) {
            case GET_CHARACTERS: {

                mCharacterDataWrapper = (CharacterDataWrapper) NoisyUtils.getFromJson(o, CharacterDataWrapper.class);
                mRecyclerView.setAdapter(new CharacterListAdapter(mCharacterDataWrapper, this));
                mRecyclerView.invalidate();
                syncAll();
                break;
            }
            case GET_ALL_CHARACTERS: {

                CharacterDataWrapper tCharacterDataWrapper = (CharacterDataWrapper) NoisyUtils.getFromJson(o, CharacterDataWrapper.class);
                merge(tCharacterDataWrapper);
                //mRecyclerView.setAdapter(new CharacterListAdapter(mCharacterDataWrapper, this));
                mRecyclerView.invalidate();
                syncAll();
                break;
            }
            default: {
                NoisyUtils.showDialog(this, NoisyConstants.INVALID_NETWORK_REQUEST, NoisyConstants.INVALID_NETWORK_REQUEST, true);
            }
        }
    }

    @Override
    public void onClick(View view) {

        NoisyUtils.logD("click");
        int itemPosition = mRecyclerView.getChildPosition(view);
        noisyninja.com.marvelcharacterlibrary.models.Character character = mCharacterDataWrapper.getData().getResults().get(itemPosition);
        Toast.makeText(mContext, character.getName(), Toast.LENGTH_LONG).show();
        Intent intent = DetailAcitvity.getIntentInstance(mContext, mCharacterDataWrapper.getData().getResults().get(itemPosition));
        startActivity(intent);
    }

    private void syncAll() {
        int total = mCharacterDataWrapper.getData().getTotal();
        int size = mCharacterDataWrapper.getData().getResults().size();
        int offset = mCharacterDataWrapper.getData().getOffset();
        if (total > size) {
            getActionBar().setSubtitle("fetched:" + size + " of " + total + " offset: " + offset);
            NoisyNetwork.getBackground(mContext, NoisyUtils.getCharacterURI(mCharacterDataWrapper.getData().getResults().size()+1), Requests.GET_ALL_CHARACTERS, this);
        } else {
            getActionBar().setSubtitle("Synced all characters");
        }
    }

    private void merge(CharacterDataWrapper characterDataWrapper) {
        mCharacterDataWrapper.getData().getResults().addAll(characterDataWrapper.getData().getResults());
        mCharacterDataWrapper.getData().setOffset(characterDataWrapper.getData().getOffset());
        mCharacterDataWrapper.getData().setCount(mCharacterDataWrapper.getData().getCount() + characterDataWrapper.getData().getCount());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                NoisyUtils.startActivity(this, SearchActivity.class);
                break;
            default:
                Toast.makeText(this, "Action not defined", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
