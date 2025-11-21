package com.example.exercise3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private Context context;
    private List<SongModel> songList;

    public SongAdapter(Context context, List<SongModel> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        SongModel song = songList.get(position);

        holder.tvTitle.setText(song.getmTitle());
        holder.tvLyric.setText(song.getmLyric());
        holder.tvArtist.setText(song.getmArtist());
        holder.tvCode.setText(song.getmCode());
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    // THÊM – XÓA – UPDATE item (hỗ trợ animation)
    public void addItem(SongModel song) {
        songList.add(song);
        notifyItemInserted(songList.size() - 1);
    }

    public void removeItem(int position) {
        songList.remove(position);
        notifyItemRemoved(position);
    }

    public void updateItem(int position, SongModel song) {
        songList.set(position, song);
        notifyItemChanged(position);
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvLyric, tvArtist, tvCode;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvLyric = itemView.findViewById(R.id.tv_lyric);
            tvArtist = itemView.findViewById(R.id.tv_artist);
            tvCode = itemView.findViewById(R.id.tv_code);
        }
    }
}
