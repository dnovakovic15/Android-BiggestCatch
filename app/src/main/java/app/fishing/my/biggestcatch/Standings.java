package app.fishing.my.biggestcatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


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
        List<Fish> fishCaught = new ArrayList<>();

        API_Fish fishCatcher = new API_Fish();

        try {
            fishCaught = fishCatcher.execute(fishType, TokenSaver.getToken(getActivity())).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        LinearLayout rootView = (LinearLayout) getActivity().findViewById(R.id.AdvancedCatalogContainer);
        LinearLayout ppg = (LinearLayout) getActivity().findViewById(R.id.ppg);
        fillLayout(rootView, fishCaught);
        fillppg(ppg, fishCaught);
    }


    //Creates the square buttons for each Players playing today so that the user would be taken to the Playerstats activity.
    public void fillLayout(LinearLayout root, final List players) {
        for (int i = 0; i < players.size(); i++) {
            TextView entry = new TextView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            entry.setText(players.get(i).toString());
            entry.setHeight(240);
            entry.setTextSize(18);
            params.setMargins(0,5,0,0);
            entry.setLayoutParams(params);
            entry.setId(i);
            entry.setTextColor(Color.BLACK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                entry.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                entry.setPadding(0, 75, 0, 0);
                entry.setBackgroundColor(getActivity().getColor(R.color.spinnerItem));
            }
            root.addView(entry);
        }
    }

    //Fills in each Players's button with their Draftkings fantasy points per game.
    public void fillppg(LinearLayout ppg, List players) {
        for (int i = 0; i < players.size(); i++) {
            TextView myView = new TextView(getActivity());
            myView.setTextSize(18);
            myView.setHeight(240);
            myView.setText("46 in.");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,5,0,0);
            myView.setLayoutParams(params);
            myView.setId(i + 100);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                myView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                myView.setPadding(0, 75, 0, 0);
                myView.setBackgroundColor(getActivity().getColor(R.color.spinnerItem));
            }
            ppg.addView(myView);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
