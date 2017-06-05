package app.fishing.my.biggestcatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by darko on 6/4/17.
 */

public class ImageDialog extends Activity {

    private ImageView mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_dialog);

        Intent myIntent = getIntent();
        mDialog = (ImageView)findViewById(R.id.your_image);
        mDialog.setImageBitmap(myIntent.getExtras("image"));

//        mDialog.setClickable(true);
//
//
//        //finish the activity (dismiss the image dialog) if the user clicks
//        //anywhere on the image
//        mDialog.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

    }
}


