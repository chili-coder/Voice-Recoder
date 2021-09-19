package com.sohaghlab.voicerecoder.adaper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sohaghlab.voicerecoder.R;
import com.sohaghlab.voicerecoder.TimeAgo;

import java.io.File;

public class AudioAdaper extends RecyclerView.Adapter<AudioAdaper.AudioViewHolder> {

    private File[] allFile;
    private TimeAgo timeAgo;

    public AudioAdaper(File[] allFile){
        this.allFile = allFile;
    }

    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_item,parent,false);
        timeAgo = new TimeAgo();
        return new AudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, int position) {

        holder.audioListTitle.setText(allFile[position].getName());
        holder.audioListDate.setText(timeAgo.getTimeAge(allFile[position].lastModified()));
    }

    @Override
    public int getItemCount() {
        return allFile.length;
    }

    public class AudioViewHolder extends RecyclerView.ViewHolder  {



        private ImageView audioListImage;
        private TextView audioListTitle, audioListDate;

        public AudioViewHolder(@NonNull View itemView) {
            super(itemView);

            audioListImage = itemView.findViewById(R.id.audiolistImage);
            audioListTitle=itemView.findViewById(R.id.audioListTitle);
            audioListDate=itemView.findViewById(R.id.audioListDate);


        }
    }
}
