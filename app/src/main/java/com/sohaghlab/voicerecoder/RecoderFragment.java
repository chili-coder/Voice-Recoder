package com.sohaghlab.voicerecoder;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class RecoderFragment extends Fragment implements View.OnClickListener {

    private AdView mAdView;
    private NavController navController;
    private ImageView audioListBtn;
    private ImageView recoding;
    private TextView audioTitle;
    private Chronometer chronometer;
    private Boolean isRecoding = false;

    private int PERMISSION_CODE = 21;
    private String recodFile;
    private MediaRecorder mediaRecorder;
    private String recordPermission = Manifest.permission.RECORD_AUDIO;

    public RecoderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recoder, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        audioListBtn = view.findViewById(R.id.backpage_btn);
        audioTitle = view.findViewById(R.id.audio_file_title);
        chronometer = view.findViewById(R.id.chronometer1);
        recoding = view.findViewById(R.id.recoard_btn);
        audioListBtn.setOnClickListener(this);
        recoding.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.backpage_btn:

                navController.navigate(R.id.action_recoderFragment_to_listFragment);
                break;

            case R.id.recoard_btn:
                if (isRecoding) {

                    stopRecoding();


                    recoding.setImageDrawable(getResources().getDrawable(R.drawable.record_off, null));
                    isRecoding = false;
                } else {
                    if (checkPermission()) {
                        startRecoding();
                        isRecoding = true;
                        recoding.setImageDrawable(getResources().getDrawable(R.drawable.record_on, null));
                    }

                }
                break;

        }
    }

    private void startRecoding() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        String recordPath = getActivity().getExternalFilesDir("/").getAbsolutePath();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss", Locale.CANADA);
        Date now = new Date();

        recodFile = "Recording " + simpleDateFormat.format(now)+ ".3gp";
        audioTitle.setText("Recoding Start, Audio Name: "+"\n" + recodFile);

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(recordPath + "/" + recodFile);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.start();
    }



    private void stopRecoding() {
        chronometer.stop();
        audioTitle.setText("Recoding Stoped, Audio Save: "+"\n" + recodFile);

        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }
    private boolean checkPermission() {

        if (ActivityCompat.checkSelfPermission(getContext(), recordPermission)== PackageManager.PERMISSION_GRANTED){
            return  true;
        }else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{recordPermission}, PERMISSION_CODE);
            return  false;
        }
    }

   
}
