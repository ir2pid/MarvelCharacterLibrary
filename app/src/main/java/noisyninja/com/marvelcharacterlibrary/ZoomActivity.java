package noisyninja.com.marvelcharacterlibrary;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import noisyninja.com.marvelcharacterlibrary.utils.NoisyUtils;

/**
 * Created by ir2pid on 17/03/16.
 */
public class ZoomActivity extends Activity {

    Context mContext;
    Activity mActivity;
    ImageButton close;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zoom);
        mContext = this;
        mActivity = this;

        close = (ImageButton)findViewById(R.id.button);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoisyUtils.backPress(mActivity);
            }
        });
        imageView = (ImageView)findViewById(R.id.imageView);

        String url = getIntent().getExtras().getString(NoisyConstants.URL_KEY);

        NoisyUtils.glideLoad(mContext, imageView,url);

    }

}
