package com.example.android.WhyNotHindi;

public class Word {
    private String mDefaultTransition;
    private String mHindiTransition;
    private int mImageResource = NO_IMAGE_PROVIDED;
    private int mAudioResource;
    private static final int NO_IMAGE_PROVIDED = -1;

    public Word(String defaultTransition, String hindiTransition,int imageResource,int audioResource) {
        mDefaultTransition = defaultTransition;
        mHindiTransition = hindiTransition;
        mImageResource = imageResource;
        mAudioResource = audioResource;
    }



    public Word(String defaultTransition, String hindiTransition,int audioResource) {
        mDefaultTransition = defaultTransition;
        mHindiTransition = hindiTransition;
        mAudioResource = audioResource;
    }



    public String getmDefaultTransition() {
        return mDefaultTransition;
    }



    public String getHindiTransition(){return mHindiTransition;}

    public int getmImageResource() {
        return mImageResource;
    }
    public boolean hasImage(){
        return mImageResource != NO_IMAGE_PROVIDED;
    }

    public int getAudioResourceId() {
        return mAudioResource;
    }


    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTransition='" + mDefaultTransition + '\'' +
                ", mHindiTransition='" + mHindiTransition + '\'' +
                ", mImageResource=" + mImageResource +
                ", mAudioResource=" + mAudioResource +
                '}';
    }
}


