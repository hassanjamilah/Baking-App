package com.andalus.bakingapp.Fragments;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andalus.bakingapp.Activities.RecipeDetailsActivity;
import com.andalus.bakingapp.MyClasses.Step;
import com.andalus.bakingapp.R;
import com.andalus.bakingapp.databinding.FragmentStepBinding;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;


public class StepFragment extends Fragment implements ExoPlayer.EventListener {
    public static final String SAVE_INSTANCE_STATE_BUNDLE_STEP = "saveInstanceStep";
    public static final String SAVE_INSTANCE_STATE_BUNDLE_STEP_SIZE = "saveInstanceStepSize";
    private static final String SAVE_INSTANCE_STATE_BUNDLE_CURRENT_POSTION = "currentPosition";
    private Handler mainHandler;

    private BandwidthMeter bandwidthMeter;


    private static MediaSessionCompat mMediaSession;

    private int currentStep;

    private int stepsSize;

    private int sdkVersion;


    public static long currentPlayPos;

    FragmentStepBinding binding;

    private SimpleExoPlayer player;

    private Step mStep;

    onNextStepClickListener mNextCallBack;
    onPreviousStepClickListener mPrevCallBack;

    private PlaybackStateCompat.Builder mStateBuilder;

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, player.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, player.getCurrentPosition(), 1f);
        }

        mMediaSession.setPlaybackState(mStateBuilder.build());


    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    public interface onNextStepClickListener {
        void onNextClicked(int position);
    }

    public interface onPreviousStepClickListener {
        void onPrevious(int position);
    }


    public StepFragment() {

    }


    public static StepFragment newInstance(String param1, String param2) {
        StepFragment fragment = new StepFragment();
        Bundle args = new Bundle();

        // fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void initializePlayer() {


        if (TextUtils.isEmpty(mStep.getVideoURL()) || mStep == null) {
            if (mStep == null) {

            }

            binding.StepFragmentExoPlayer.setVisibility(View.INVISIBLE);
            return;
        }
        String url = mStep.getVideoURL();
        // Create a default TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        //Initialize the player
        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, new DefaultLoadControl());

        //Initialize simpleExoPlayerView
        binding.StepFragmentExoPlayer.setPlayer(player);
        String userAgent = Util.getUserAgent(getContext(), "ClassicalMusicQuiz");
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getContext(), userAgent);

        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        // This is the MediaSource representing the media to be played.
        Uri videoUri = Uri.parse(url.toString());
        MediaSource videoSource = new ExtractorMediaSource(videoUri,
                dataSourceFactory, extractorsFactory, null, null);

        // Prepare the player with the source.
        player.prepare(videoSource);
        player.setPlayWhenReady(true);

        player.seekTo(currentPlayPos);

    }


    public void initializeMediSession() {

        mMediaSession = new MediaSessionCompat(getContext(), RecipeDetailsActivity.class.getSimpleName());
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mMediaSession.setMediaButtonReceiver(null);


        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PAUSE);


        mMediaSession.setPlaybackState(mStateBuilder.build());


        mMediaSession.setCallback(new MySessionCallBack());

        mMediaSession.setActive(true);


    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {


        outState.putParcelable(SAVE_INSTANCE_STATE_BUNDLE_STEP, mStep);

        outState.putInt(SAVE_INSTANCE_STATE_BUNDLE_STEP_SIZE, stepsSize);
        outState.putLong(SAVE_INSTANCE_STATE_BUNDLE_CURRENT_POSTION, currentPlayPos);

        RecipeDetailsActivity.stepFragmentState = outState;


        super.onSaveInstanceState(outState);
    }


    /**
     * This method is used to release the player
     */
    public void releasePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.stop();
            player.release();
            player = null;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step, container, false);


        if (savedInstanceState != null
                && savedInstanceState.containsKey(SAVE_INSTANCE_STATE_BUNDLE_STEP)
                && savedInstanceState.containsKey(SAVE_INSTANCE_STATE_BUNDLE_STEP_SIZE)
        ) {

            mStep = savedInstanceState.getParcelable(SAVE_INSTANCE_STATE_BUNDLE_STEP);
            stepsSize = savedInstanceState.getInt(SAVE_INSTANCE_STATE_BUNDLE_STEP_SIZE);
            currentPlayPos = savedInstanceState.getLong(SAVE_INSTANCE_STATE_BUNDLE_CURRENT_POSTION);


        } else {
            Bundle bundle = getArguments();
            if (bundle != null) {
                mStep = bundle.getParcelable(RecipeDetailsActivity.INGREDIENTS_STEP_BUNDLE_KEY);
                stepsSize = bundle.getInt(RecipeDetailsActivity.INGREDIENTS_STEPS_SIZE_BUNDLE_KEY);

            } else {

            }
        }


        if (TextUtils.isEmpty(mStep.getThumbailURL())) {
            binding.thumbaiImageview.setVisibility(View.GONE);
        } else {
            Uri uri = Uri.parse(mStep.getThumbailURL()).buildUpon().build();

            Picasso.with(getContext()).load(uri).into(binding.thumbaiImageview);
        }


        binding.StepFragmentTextview.setText(mStep.getShortDescription());
        binding.fragmentStepDetailsTextView.setText(mStep.getDescription());
        binding.fragmentStepCurrentStepButton.setText(" ".concat(String.valueOf(mStep.getStepId() + 1)).concat(" / ").concat(String.valueOf(stepsSize)));


        binding.fragmentStepNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNextCallBack.onNextClicked(mStep.getStepId());
            }
        });

        binding.fragmentStepPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPrevCallBack.onPrevious(mStep.getStepId());
            }
        });

        return binding.getRoot();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mNextCallBack = (onNextStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onNextStepClickListener");
        }

        try {
            mPrevCallBack = (onPreviousStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onPreviousStepClickListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onStart() {
        super.onStart();

        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23) {
            initializePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (Util.SDK_INT > 23) {
            releasePlayer();
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            currentPlayPos = player.getCurrentPosition();


        } else {

            currentPlayPos = 0;
        }


        if (mMediaSession != null) {

            mMediaSession.setActive(false);
        }
    }


    private class MySessionCallBack extends MediaSessionCompat.Callback {

        @Override
        public void onPlay() {
            super.onPlay();
            player.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            super.onPause();
            player.setPlayWhenReady(false);
        }


    }
}
