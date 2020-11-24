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


public class FamilyFragment extends Fragment {
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

    public FamilyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list,container,false);




        mAudioManager = (AudioManager) getActivity().getSystemService(FamilyActivity.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("Father", "पिता", R.drawable.family_father, R.raw.peeta));
        words.add(new Word("Mother", "माँ/माता ", R.drawable.family_mother, R.raw.maa_maata));
        words.add(new Word("Son", "बेटा", R.drawable.family_son, R.raw.beta));
        words.add(new Word("Daughter", "बेटी", R.drawable.family_daughter, R.raw.beti));
        words.add(new Word("Older Brother", "बड़ा भाई", R.drawable.family_older_brother, R.raw.bda_bhai));
        words.add(new Word("Older Sister", "बड़ी बहन", R.drawable.family_older_sister, R.raw.bdi_behen));
        words.add(new Word("Younger Brother", "छोटा भाई", R.drawable.family_younger_brother, R.raw.chota_bhai));
        words.add(new Word("Younger Sister", "छोटी बहन", R.drawable.family_younger_sister, R.raw.choti_behen));
        words.add(new Word("GrandMother", "दादी ", R.drawable.family_grandmother, R.raw.dadi));
        words.add(new Word("GrandFather", "दादा", R.drawable.family_grandfather, R.raw.dadi));


        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_family);


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