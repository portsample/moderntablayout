package net.sample.moderntablayout;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.Timer;
import java.util.TimerTask;

public class FirstFragment extends Fragment {
    String szFavoriteColor = "null";
    TextView tvFavoriteColor;
    static int iPopupWindowDismissDelay = 30000;
    Button btnFavoriteColorAlertDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
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
        szFavoriteColor = "blue";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        btnFavoriteColorAlertDialog = (Button) view.findViewById(R.id.btnfavoritecolorxml);
        populateFirstFragment(view);
        fetchButtonClicks();

        return view;
    }
    public void populateFirstFragment(View view) {
        tvFavoriteColor = (TextView) view.findViewById(R.id.tvfavoritecolorxml);
        tvFavoriteColor.setText(szFavoriteColor);
    }

    private void fetchButtonClicks() {
        btnFavoriteColorAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFavoriteColorAlertDialog();
            }
        });
    }

    private void openFavoriteColorAlertDialog() {
        final AlertDialog.Builder openFavoriteColorAlertDialog = new AlertDialog.Builder(getActivity());
        View view = this.getLayoutInflater().inflate(R.layout.favoritecolor_alertdialog, null);
        openFavoriteColorAlertDialog.setView(view);
        final AlertDialog alert = openFavoriteColorAlertDialog.create();

        EditText  edFavoriteColor = (EditText) view.findViewById(R.id.edFavoriteColorxml);
        edFavoriteColor.setTypeface(null, Typeface.BOLD);
        edFavoriteColor.setText(null);

        Button btnSave = (Button) view.findViewById(R.id.btnCloseCommentWindow);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String szFavoriteColor = edFavoriteColor.getText().toString();
                tvFavoriteColor.setText(szFavoriteColor);
                alert.dismiss();
            }
        });
        alert.show();
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                alert.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, iPopupWindowDismissDelay); // after 10 seconds (or 10000 miliseconds), run() will be active.
    }//end openFavoriteColorAlertDialog()
    }


