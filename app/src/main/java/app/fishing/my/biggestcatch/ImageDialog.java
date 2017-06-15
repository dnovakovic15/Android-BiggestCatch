package app.fishing.my.biggestcatch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by darko on 6/4/17.
 */

//public class ImageDialog extends Activity {
//
//    private ImageView mDialog;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.image_dialog);
//
//        Intent myIntent = getIntent();
//        Fish fish = (Fish) myIntent.getSerializableExtra("fish");
//        mDialog = (ImageView)findViewById(R.id.your_image);
//        System.out.println("http://52.14.155.129/biggestCatch/"+ "darko@yahoo.com" + "_" + fish.getType() + "_" + fish.getSize() +".jpeg");
//        Glide.with(this).load("http://52.14.155.129/biggestCatch/"+ "darko@yahoo.com" + "_" + fish.getType() + "_" + fish.getSize() +".jpeg").into(mDialog);
//
////        AlertDialog.Builder alertadd = new AlertDialog.Builder(this);
////        LayoutInflater factory = LayoutInflater.from(this);
////        final View view = factory.inflate(R.layout.image_dialog, null);
////        alertadd.setView(view);
////        alertadd.setNeutralButton("Here!", new DialogInterface.OnClickListener() {
////            public void onClick(DialogInterface dlg, int sumthin) {
////
////            }
////        });
////
////        alertadd.show();
//    }
//}


