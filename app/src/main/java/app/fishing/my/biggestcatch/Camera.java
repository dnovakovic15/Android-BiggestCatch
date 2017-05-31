package app.fishing.my.biggestcatch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Camera.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Camera#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Camera extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private OnFragmentInteractionListener mListener;

    public Camera() {
        // Required empty public constructor
    }

    public static Camera newInstance(String email) {
        Camera fragment = new Camera();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        dispatchTakePictureIntent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_camera, container, false);

        final Button submit = (Button) view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submit(view);
            }});

        final Button discard = (Button) view.findViewById(R.id.discard);
        discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                discard(view);
            }});

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    //The following  methods and their variables are for the Camera tab portion of the bottom navigation view.

    static final int REQUEST_TAKE_PHOTO = 1;

    //Camera Tab View: Starts the user's device camera and saves the image into a path specified in the Android Manifest provider portion under meta-data:resources.
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this.getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    //Camera Tab View: Fetches the image from the intent started with dispatchTakePictureIntent().
    Bitmap myBitmap;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            File imgFile = new  File(mCurrentPhotoPath);

            if(imgFile.exists()){

                myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ImageView myImage = (ImageView) getView().findViewById(R.id.imageView);
                myImage.setImageBitmap(myBitmap);
                System.out.println("Image: ");
            }
        }
    }

    String mCurrentPhotoPath;

    //Camera tab View: Creates the picture file.
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //Behavior for the submit button.
    //Creates a pop-up that asks user for fish type and calls the alertBuilderFishSize method when fish type submitted.
    public void submit(View view){
        final EditText et = new EditText(this.getContext());
        et.setGravity(Gravity.CENTER);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());

        final TextView title = new TextView(this.getContext());
        title.setText("Enter the Type of Fish");
        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);

        alertDialogBuilder.setView(et);

        alertDialogBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alertBuilderFishSize();
            }
        });

        alertDialogBuilder.setCustomTitle(title);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    //Creates a pop-up that asks the user to input fish size. Then calls the API_SendFish class to send fish to database.
    private void alertBuilderFishSize(){
        final EditText et = new EditText(this.getContext());
        et.setGravity(Gravity.CENTER);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());

        final TextView title = new TextView(this.getContext());
        title.setText("Enter the Size of the Fish in Inches");
        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);

        alertDialogBuilder.setView(et);

        alertDialogBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                API_SendFish myFish = new API_SendFish();

                try{
                    myFish.execute(myBitmap).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        alertDialogBuilder.setCustomTitle(title);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    //Discards the image and sends the user back to their camera.
    public void discard(View view){
        dispatchTakePictureIntent();
    }
}
