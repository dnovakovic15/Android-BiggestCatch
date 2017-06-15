package app.fishing.my.biggestcatch;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AlignmentSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.R.attr.name;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Standings.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Standings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Standings extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String fishType;

    private OnFragmentInteractionListener mListener;

    public Standings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Standings.
     */
    // TODO: Rename and change types and number of parameters
    public static Standings newInstance(String param1, String param2) {
        Standings fragment = new Standings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.action_bar);
        TextView title = (TextView) getActivity().findViewById(R.id.action_bar_title);
        title.setText("Standings");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_standings, container, false);

        Spinner fishView = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.fish_types, R.layout.spinner);
        adapter.setDropDownViewResource(R.layout.spinner);
        fishView.setAdapter(adapter);
        fishView.setOnItemSelectedListener(this);

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        fishType = parent.getItemAtPosition(position).toString();
        System.out.println("Fish type: " + fishType);
        List<Fish> fishCaught = new ArrayList<>();

        API_GetCaughtFish fishCatcher = new API_GetCaughtFish();

        try {
            fishCaught = fishCatcher.execute(fishType, TokenSaver.getToken(getActivity())).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for(Fish element: fishCaught){
            System.out.println(element.getSize() + "   " + element.getFisherman() + element.getType());
        }
        LinearLayout rootView = (LinearLayout) getActivity().findViewById(R.id.AdvancedCatalogContainer);
        LinearLayout ppg = (LinearLayout) getActivity().findViewById(R.id.ppg);
        LinearLayout ranking = (LinearLayout) getActivity().findViewById(R.id.rank);
        LinearLayout picture = (LinearLayout) getActivity().findViewById(R.id.thumbnail);

        //Delete the previous views inserted.
        rootView.removeAllViews();
        ppg.removeAllViews();
        ranking.removeAllViews();
        picture.removeAllViews();

        //Add new views.
        fillLayout(rootView, fishCaught);
        fillSize(ppg, fishCaught);
        fillRanking(ranking, fishCaught);
        fillThumbnail(picture, fishCaught);
    }

    //Fills ranking
    public void fillRanking(LinearLayout ppg, List<Fish> fish) {
        for (int i = 0; i < fish.size(); i++) {
            Button myButton = new Button(getActivity());
            myButton.setTextSize(18);
            //myButton.setTextColor(Color.WHITE);
            myButton.setBackgroundColor(Color.WHITE);
            myButton.getBackground().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.DARKEN);
            myButton.setHeight(240);
            myButton.setText("#" + Integer.toString(i + 1));
            System.out.println("i: " + i);
            System.out.println("#" + Integer.toString(i + 1));
            myButton.setClickable(false);
            myButton.setId(i + 100);
            myButton.setTypeface(null, Typeface.BOLD);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(20, 10, 0, 10);
            myButton.setPadding(30, 0, 0, 0);
            myButton.setLayoutParams(params);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                myButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            ppg.addView(myButton);
        }
    }


        //Creates the square buttons for each Fish playing today so that the user would be taken to the Fishtats activity.
    public void fillLayout(LinearLayout root, final List<Fish> fish) {
        for (int i = 0; i < fish.size(); i++) {
            Button myButton = new Button(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //myButton.getBackground().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.DARKEN);
            myButton.setBackgroundColor(Color.WHITE);
            params.setMargins(0, 10, 0, 10);
            myButton.setHeight(240);
            myButton.setTextSize(18);
            myButton.setTypeface(Typeface.SERIF);
            myButton.setTransformationMethod(null);
            myButton.setPadding(0,0,0,0);
            String s= "Lake Bluff";
            SpannableString location =  new SpannableString(s);
            location.setSpan(new RelativeSizeSpan(0.7f), 0,location.length(), 0);
            myButton.setText(fish.get(i).getFisherman() + "\n");
            myButton.append(location);
            myButton.setLayoutParams(params);
            myButton.setId(i);
            //myButton.setTextColor(Color.WHITE);
            final int finalI = i;
            Button.OnClickListener myButtonClick = new Button.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(getActivity(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.image_dialog);
                    Button btnClose = (Button)dialog.findViewById(R.id.btnIvClose);
                    ImageView image = (ImageView)dialog.findViewById(R.id.iv_preview_image);
                    Glide.with(getActivity()).load("http://52.14.155.129/biggestCatch/"+ fish.get(finalI).getFisherman() + "_" + fish.get(finalI).getType() + "_" + fish.get(finalI).getSize() +".jpeg").into(image);
                    btnClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {

                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            };
            myButton.setOnClickListener(myButtonClick);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                myButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            root.addView(myButton);
        }
    }

    //Fills in each Fish's button with their Draftkings fantasy points per game.
    public void fillSize(LinearLayout ppg, List<Fish> fish) {
        for (int i = 0; i < fish.size(); i++) {
            Button myButton = new Button(getActivity());
            myButton.setTextSize(18);
            //myButton.setTextColor(Color.WHITE);
            myButton.setTypeface(Typeface.SERIF);
            myButton.setTransformationMethod(null);
            myButton.setBackgroundColor(Color.WHITE);
            //myButton.getBackground().setColorFilter(Color.parseColor("#6C0126FA"), PorterDuff.Mode.DARKEN);
            myButton.setHeight(240);
            myButton.setText(Double.toString(fish.get(i).getSize()) + " in.");
            myButton.setClickable(false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10, 15, 10);
            myButton.setPadding(0,0,0,0);
            myButton.setLayoutParams(params);
            myButton.setId(i + 100);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                myButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            ppg.addView(myButton);
        }
    }

    //Fills in each Fish's button with their Draftkings fantasy points per game.
    public void fillThumbnail(LinearLayout ppg, List<Fish> fish) {
        for (int i = 0; i < fish.size(); i++) {
            final int finalI = i;
            ImageView thumbnail = new ImageView(getActivity());
            Glide.with(getActivity()).load("http://52.14.155.129/biggestCatch/"+ fish.get(finalI).getFisherman() + "_" + fish.get(finalI).getType() + "_" + fish.get(finalI).getSize() +".jpeg").into(thumbnail);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,10,0,10);
            thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
            thumbnail.setMinimumHeight(240);
            thumbnail.setMaxHeight(240);
            thumbnail.setLayoutParams(params);
            thumbnail.setPadding(0,10,25,10);
            ppg.addView(thumbnail);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
