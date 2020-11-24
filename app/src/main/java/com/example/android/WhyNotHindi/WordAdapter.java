package com.example.android.WhyNotHindi;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;


import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    private int mColorResourceId;

    private static final String LOG_TAG = WordAdapter.class.getSimpleName();
    public WordAdapter(Context context, ArrayList<Word> word,int colorResoureceID) {
        super(context,0, word);
        mColorResourceId = colorResoureceID;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {


        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Word currentWord = getItem(position);

        TextView defaultTextview = (TextView) listItemView.findViewById(R.id.text1);

        defaultTextview.setText(currentWord.getmDefaultTransition());


        TextView hindiTextView = (TextView) listItemView.findViewById(R.id.text2);
        hindiTextView.setText(currentWord.getHindiTransition());



        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        if(currentWord.hasImage()){
        imageView.setImageResource(currentWord.getmImageResource());
        imageView.setVisibility(View.VISIBLE);}
        else {
            imageView.setVisibility(View.GONE);
        }
        View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        textContainer.setBackgroundColor(color);


        return listItemView;

    }


}
