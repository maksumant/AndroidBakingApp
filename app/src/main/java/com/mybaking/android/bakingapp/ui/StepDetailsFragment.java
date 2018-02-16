package com.mybaking.android.bakingapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.mybaking.android.bakingapp.R;
import com.mybaking.android.bakingapp.domain.RecipeStep;
import com.mybaking.android.bakingapp.video.CustomLoadControl;

/**
 * Created by makrandsumant on 16/02/18.
 */

public class StepDetailsFragment extends Fragment {
    private static final String CURRENT_STEP_DATA_KEY = "currentStep";
    private static final String VIDEO_SEEK_POSITION_KEY = "videoSeekPosition";
    private static final String VIDEO_PLAYBACK_STATE_KEY = "playbackState";
    private static final String VIDEO_PLAY_WHEN_READY_KEY = "playWhenReady";
    private SimpleExoPlayerView mSimpleExoPlayerView;
    private SimpleExoPlayer mSimpleExoPlayer;
    private int mPlaybackState;
    private boolean mPlayWhenReady;

    public void setCurrentStep(RecipeStep currentStep) {
        this.currentStep = currentStep;
    }

    private RecipeStep currentStep;
    private long mSeekPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        TextView stepDescription = (TextView) rootView.findViewById(R.id.tv_step_instruction);
        mSimpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.ep_step_media_player);


        if (savedInstanceState != null) {
            if(savedInstanceState.containsKey(CURRENT_STEP_DATA_KEY)) {
                currentStep = (RecipeStep) savedInstanceState.get(CURRENT_STEP_DATA_KEY);
            }
            if(savedInstanceState.containsKey(VIDEO_SEEK_POSITION_KEY)) {

                mSeekPosition = savedInstanceState.getLong(VIDEO_SEEK_POSITION_KEY);
            }

        }
        stepDescription.setText(currentStep.getDescription());

        String videoURL = currentStep.getVideoURL();
        if(videoURL!= null && !videoURL.isEmpty() && mSimpleExoPlayer == null) {
            mPlayWhenReady = true;
            initializePlayer(Uri.parse(videoURL));
        }
        return rootView;
    }

    private void initializePlayer(Uri uri) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new CustomLoadControl();
            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this.getContext(), trackSelector,loadControl);
            mSimpleExoPlayerView.setPlayer(mSimpleExoPlayer);

            String userAgent = Util.getUserAgent(this.getContext(), "RecipeStep");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(this.getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mSimpleExoPlayer.prepare(mediaSource);
            if (mPlaybackState == 0 || ((mPlaybackState == ExoPlayer.STATE_READY) && mPlayWhenReady)) {
                mSimpleExoPlayer.setPlayWhenReady(true);
            } else if((mPlaybackState == ExoPlayer.STATE_READY)) {
                mSimpleExoPlayer.setPlayWhenReady(false);
            }
            mSimpleExoPlayer.seekTo(mSeekPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(CURRENT_STEP_DATA_KEY, currentStep);
        outState.putLong(VIDEO_SEEK_POSITION_KEY, mSimpleExoPlayer.getCurrentPosition());
        outState.putInt(VIDEO_PLAYBACK_STATE_KEY, mSimpleExoPlayer.getPlaybackState());
        outState.putBoolean(VIDEO_PLAY_WHEN_READY_KEY, mSimpleExoPlayer.getPlayWhenReady());
        mSimpleExoPlayer.stop();
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer() {
        if(mSimpleExoPlayer != null) {
            mSimpleExoPlayer.stop();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) {
            if(savedInstanceState.containsKey(CURRENT_STEP_DATA_KEY)) {
                currentStep = (RecipeStep) savedInstanceState.get(CURRENT_STEP_DATA_KEY);
            }
            if(savedInstanceState.containsKey(VIDEO_SEEK_POSITION_KEY)) {
                mSeekPosition = savedInstanceState.getLong(VIDEO_SEEK_POSITION_KEY);
            }
            if(savedInstanceState.containsKey(VIDEO_PLAYBACK_STATE_KEY)) {
                mPlaybackState = savedInstanceState.getInt(VIDEO_PLAYBACK_STATE_KEY);
            }
            if(savedInstanceState.containsKey(VIDEO_PLAY_WHEN_READY_KEY)) {
                mPlayWhenReady = savedInstanceState.getBoolean(VIDEO_PLAY_WHEN_READY_KEY);
            }

            String videoURL = currentStep.getVideoURL();
            if(videoURL!= null && !videoURL.isEmpty()) {
                initializePlayer(Uri.parse(videoURL));
            }
        }
    }
}
