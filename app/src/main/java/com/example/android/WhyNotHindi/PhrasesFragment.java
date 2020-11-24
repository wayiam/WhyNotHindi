package com.example.android.WhyNotHindi;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class PhrasesFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;


    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    private AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                        // Pause playback because your Audio Focus was
                        // temporarily stolen, but will be back soon.
                        // i.e. for a phone call
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        // Stop playback, because you lost the Audio Focus.
                        // i.e. the user started some other playback app
                        // Remember to unregister your controls/buttons here.
                        // And release the kra — Audio Focus!
                        // You’re done.
                        //
                        //.abandonAudioFocus(afChangeListener);
                    } else if (focusChange ==
                            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                        // Lower the volume, because something else is also
                        // playing audio over you.
                        // i.e. for notifications or navigation directions
                        // Depending on your audio playback, you may prefer to
                        // pause playback here instead. You do you.
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        mediaPlayer.start();
                        // Resume playback, because you hold the Audio Focus
                        // again!
                        // i.e. the phone call ended or the nav directions
                        // are finished
                        // If you implement ducking and lower the volume, be
                        // sure to return it to normal here, as well.
                    }
                }
            };

    public PhrasesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list,container,false);



        mAudioManager = (AudioManager) getActivity().getSystemService(PhrasesActivity.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word(" How are you?","आप कैसे हैं ",R.raw.ph1));
        words.add(new Word("What is your name? ","आपका नाम क्या है?",R.raw.ph2));
        words.add(new Word("Where are you from? ","आप कहाँ से हैं?",R.raw.ph3));
        words.add(new Word(" I don’t understand","मैं नहीं समझा (m.) / मैं नहीं समझी (f.)",R.raw.ph4));
        words.add(new Word(" Do you speak English?","क्या आप अंग्रेज़ी बोलते हैं?",R.raw.ph5));
        words.add(new Word("I don’t know","मुझको मालूम नहीं",R.raw.ph6));
        words.add(new Word("Can I help you?","क्या मैं आपकी मदद कर सकता /सकती हुँ ।",R.raw.ph7));
        words.add(new Word("Can you help me?","क्या आप मेरी मदद कर सकते हैं ।",R.raw.ph8));
        //       words.add(new Word("Ten","दस"));
        words.add(new Word("Excuse me! ( to pass by)","  क्षमा कीजिए । ",R.raw.ph9));
        words.add(new Word("Come with me!","  मेरे साथ आइए ।",R.raw.ph10));

        WordAdapter adapter = new WordAdapter(getActivity(), words,R.color.category_colors);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();
                Word word = words.get(i);

                int result = mAudioManager.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });


        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();

        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        //If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            //Regardless of the current state of the media player,release its resources
            //because we no longer need it.
            mediaPlayer.release();
            //set the media player to null. For our code, we've decided that
            //setting the media player to null is an easy way to tell that the media player
            //is not configured to play an audio file at the moment
            mediaPlayer = null;
            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }
}