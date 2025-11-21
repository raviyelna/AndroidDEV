package com.example.exercise3;

import java.io.Serializable;

public class SongModel implements Serializable {
    private String mCode;
    private String mTitle;
    private String mLyric;
    private String mArtist;

    public SongModel(String mCode, String mTitle, String mLyric, String mArtist) {
        this.mCode = mCode;
        this.mTitle = mTitle;
        this.mLyric = mLyric;
        this.mArtist = mArtist;
    }

    public String getmCode() { return mCode; }
    public String getmTitle() { return mTitle; }
    public String getmLyric() { return mLyric; }
    public String getmArtist() { return mArtist; }
}
