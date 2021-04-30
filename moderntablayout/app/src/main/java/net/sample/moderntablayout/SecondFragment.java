package net.sample.moderntablayout;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Timer;
import java.util.TimerTask;

public class SecondFragment extends Fragment {
    String szFavoriteColor = "null";
    TextView tvFavoriteColor;
    static int iPopupWindowDismissDelay = 30000;
    Button btnFavoriteColorAlertDialog;
    boolean bRefreshSecondFragmentFlag = false;
    Handler handlerDialogTimer, handlerEventTimer;
    Runnable runnableDialogDismissCountdown, runnableEventTimer;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
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
        handlerEventTimer = new Handler();
        //szFavoriteColor = "blue";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_second, container, false);
        btnFavoriteColorAlertDialog = (Button) v.findViewById(R.id.btnfavoritecolorxml);
        szFavoriteColor = " dark blue";
        populateSecondFragment(v);
        fetchButtonClicks();
        startEventTimer(v);
        return v;
    }

    public void populateSecondFragment(View v) {
        tvFavoriteColor = (TextView) v.findViewById(R.id.tvfavoritecolorxml);
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

    synchronized private void startEventTimer(View v) {
        handlerEventTimer.postDelayed(eventTimer(v), 333);
    }
/**************************************************************************************
 * This is an event timer set to loop 3x per second. Checks boolean flags and initiates
 * a specified activity if one is set, ("TRUE"). This one refreshes the TextEdit
 * field (edFavoriteColor) on the second tab if a flag is set in the AlertDialog popup.
 *************************************************************************************/
    private Runnable eventTimer(View v) {
        Runnable task = new Runnable() {
            @Override
            public void run() {//refresh screen
                if (bRefreshSecondFragmentFlag == true) {
                    populateSecondFragment(v);
                           // Toast.makeText(getActivity(), "Runnable triggered - TRUE.", Toast.LENGTH_LONG).show();
                } else if (bRefreshSecondFragmentFlag != true) {//don't refresh screen
                    //do nothing as screen does not need refresh
                    //Toast.makeText(getActivity(), "Runnable triggered - FALSE.", Toast.LENGTH_LONG).show();
                }
                bRefreshSecondFragmentFlag = false;
                /* and here comes the "trick" */
                handlerEventTimer.postDelayed(this, 333);//repeat every n-milliseconds...was 1000
            }
        };
        runnableEventTimer = task;
        return (task);
    }//end eventTimer()

    private void openFavoriteColorAlertDialog() {
        final AlertDialog.Builder openFavoriteColorAlertDialog = new AlertDialog.Builder(getActivity());
        View v = this.getLayoutInflater().inflate(R.layout.favoritecolor_alertdialog, null);
        openFavoriteColorAlertDialog.setView(v);
        final AlertDialog alert = openFavoriteColorAlertDialog.create();

        //converts iTracklogInterval from ms to sec, loads into appropriate field
        EditText edFavoriteColor = (EditText) v.findViewById(R.id.edFavoriteColorxml);
        edFavoriteColor.setTypeface(null, Typeface.BOLD);
        edFavoriteColor.setText(null);

        Button btnSave = (Button) v.findViewById(R.id.btnCloseCommentWindow);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                szFavoriteColor = edFavoriteColor.getText().toString();
                bRefreshSecondFragmentFlag = true;
                alert.dismiss();
            }
        });
        alert.show();
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                alert.dismiss();
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, iPopupWindowDismissDelay); // after 10 seconds (or 10000 miliseconds), run() will be active.
    }//end openFavoriteColorAlertDialog()
}
