/*
 * Copyright (C) 2013 yixia.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.vov.vitamio.demo;

import java.io.IOException;

import io.vov.vitamio.demo.VmosCount;


import android.app.Activity;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaMetadataRetriever;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.MediaPlayer.OnVideoSizeChangedListener;

public class MediaPlayerDemo_Video extends Activity implements OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener, OnVideoSizeChangedListener, SurfaceHolder.Callback {

	private static final String TAG = "MediaPlayerDemo";
	private int mVideoWidth;
	private int mVideoHeight;
	private MediaPlayer mMediaPlayer;
	private SurfaceView mPreview;
	private SurfaceHolder holder;
	private String path;
	private Bundle extras;
	private static final String MEDIA = "media";
	private static final int LOCAL_AUDIO = 1;
	private static final int STREAM_AUDIO = 2;
	private static final int RESOURCES_AUDIO = 3;
	private static final int LOCAL_VIDEO = 4;
	private static final int STREAM_VIDEO = 5;
	private boolean mIsVideoSizeKnown = false;
	private boolean mIsVideoReadyToBePlayed = false;
	
	
	// add by lw
	private TextView mIniTime;
	private TextView mKadun;
	private TextView mKaduntime;
	private Button  mVmos;
	private TextView mIniTime_v;
	private TextView mKadun_v;
	private TextView mKaduntime_v;
	private TextView mVmos_v;
	private TextView bit_rate;
	private TextView bit_rate_v;
	private OnInfoListener mOnInfoListener;
	private String mBitrate;
	/**
	 * 
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		if (!LibsChecker.checkVitamioLibs(this))
			return;

		setContentView(R.layout.mediaplayer_2);
		mPreview = (SurfaceView) findViewById(R.id.surface);
		
		// add by lw
		path = "http://pl.youku.com/playlist/m3u8?ctype=12&ep=cCaXHEmNVswI7SPWjD8bYiSxJnRbXP8M9BiFgdBlBtQgSOq2%0D%0A&ev=1&keyframe=1&oip=459980061&sid=24360002880941258cb3d&token=4029&type=hd2&vid=162700276";
		//path = "http://103.41.141.174/youku/677682CE8744D7E24AC3C6B19/0300010100522ED4C8110D1339CB5A4D7C55D2-6EB3-51F1-D7A0-ACDAD82AFCAF.flv";
		io.vov.vitamio.MediaMetadataRetriever retriever = new io.vov.vitamio.MediaMetadataRetriever(this);
		try {
			
			if (path == "") {
				// Tell the user to provide an audio file URL.
				Toast.makeText(MediaPlayerDemo_Video.this, "Please edit MediaMetadataRetrieverDemo Activity, " + "and set the path variable to your audio file path." + " Your audio file must be stored on sdcard.", Toast.LENGTH_LONG).show();
				return;
			}
			retriever.setDataSource(path);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mBitrate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VARIANT_BITRATE);
		VmosCount.setBitrate(mBitrate);
		
		mIniTime = (TextView) findViewById(R.id.initial_time);
		mKadun = (TextView) findViewById(R.id.kadun_num);
		mKaduntime = (TextView) findViewById(R.id.kadun_time);
		mVmos = (Button) findViewById(R.id.vmos);
		mIniTime_v = (TextView) findViewById(R.id.initial_time_v);
		mKadun_v = (TextView) findViewById(R.id.kadun_num_v);
		mKaduntime_v = (TextView) findViewById(R.id.kadun_time_v);
		mVmos_v = (TextView) findViewById(R.id.vmos_v);
		
		bit_rate = (TextView) findViewById(R.id.bit_rate);
		bit_rate_v = (TextView) findViewById(R.id.bit_rate_v);
		//add by lw
		mVmos.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v){
				VmosShow();
			}
		});
		
		
		holder = mPreview.getHolder();
		holder.addCallback(this);
		holder.setFormat(PixelFormat.RGBA_8888); 
		extras = getIntent().getExtras();
		

		
	}

	private void playVideo(Integer Media) {
		doCleanUp();
		try {

			switch (Media) {
			case LOCAL_VIDEO:
				/*
				 * TODO: Set the path variable to a local media file path.
				 */
				path = "";
				if (path == "") {
					// Tell the user to provide a media file URL.
					Toast.makeText(MediaPlayerDemo_Video.this, "Please edit MediaPlayerDemo_Video Activity, " + "and set the path variable to your media file path." + " Your media file must be stored on sdcard.", Toast.LENGTH_LONG).show();
					return;
				}
				break;
			case STREAM_VIDEO:
				/*
				 * TODO: Set path variable to progressive streamable mp4 or
				 * 3gpp format URL. Http protocol should be used.
				 * Mediaplayer can only play "progressive streamable
				 * contents" which basically means: 1. the movie atom has to
				 * precede all the media data atoms. 2. The clip has to be
				 * reasonably interleaved.
				 * 
				 */
				
				if (path == "") {
					// Tell the user to provide a media file URL.
					Toast.makeText(MediaPlayerDemo_Video.this, "Please edit MediaPlayerDemo_Video Activity," + " and set the path variable to your media file URL.", Toast.LENGTH_LONG).show();
					return;
				}

				break;

			}
			//add by lw
			 VmosUpdate(0);
			 
			// Create a new media player and set the listeners
			mMediaPlayer = new MediaPlayer(this);
			mMediaPlayer.setDataSource(path);
			mMediaPlayer.setDisplay(holder);
			mMediaPlayer.prepareAsync();
			mMediaPlayer.setOnBufferingUpdateListener(this);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setOnPreparedListener(this);
			mMediaPlayer.setOnVideoSizeChangedListener(this);
			
			// add lw
			mMediaPlayer.setOnInfoListener(mInfoListener);
			
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			 
		
			
			
		} catch (Exception e) {
			Log.e(TAG, "error: " + e.getMessage(), e);
		}
	}

	public void onBufferingUpdate(MediaPlayer arg0, int percent) {
		 Log.d(TAG, "onBufferingUpdate percent:" + percent);
	}

	public void onCompletion(MediaPlayer arg0) {
		Log.d(TAG, "onCompletion called");
	}

	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		Log.v(TAG, "onVideoSizeChanged called");
		if (width == 0 || height == 0) {
			Log.e(TAG, "invalid video width(" + width + ") or height(" + height + ")");
			return;
		}
		mIsVideoSizeKnown = true;
		mVideoWidth = width;
		mVideoHeight = height;
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
		}
	}

	public void onPrepared(MediaPlayer mediaplayer) {
		Log.d(TAG, "onPrepared called");
		mIsVideoReadyToBePlayed = true;
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
			
			//add by lw
			VmosUpdate(1);
		}
	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
		Log.d(TAG, "surfaceChanged called");

	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder) {
		Log.d(TAG, "surfaceDestroyed called");
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated called");
		playVideo(extras.getInt(MEDIA));

	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseMediaPlayer();
		doCleanUp();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		releaseMediaPlayer();
		doCleanUp();
	}

	private void releaseMediaPlayer() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

	private void doCleanUp() {
		mVideoWidth = 0;
		mVideoHeight = 0;
		mIsVideoReadyToBePlayed = false;
		mIsVideoSizeKnown = false;
	}

	private void startVideoPlayback() {
		Log.v(TAG, "startVideoPlayback");
		holder.setFixedSize(mVideoWidth, mVideoHeight);
		mMediaPlayer.start();
	}
	
	//add by lw
	private void VmosShow(){
		bit_rate_v.setText(VmosCount.getBitrate().toString());
		mIniTime_v.setText(VmosCount.getInitime().toString()+"  ms");
		mKadun_v.setText(VmosCount.getKadunnum().toString()+"  ´Î");
		mKaduntime_v.setText(VmosCount.getKaduntime().toString()+"  ms");
		mVmos_v.setText(VmosCount.getVmos_num().toString());
	}
	
	//add by lw
	private void VmosUpdate(int state){
	     Long currunt_time;
	     Long time_minus;
	     currunt_time = System.currentTimeMillis();
	     VmosShow();
	     switch(state){
	     case 0: //Initial
	    	 VmosCount.setPretime(currunt_time);
	    	 break;
	    	 
	     case 1: //Play
	    	 if(VmosCount.getKadunnum() == -1){
	    		 VmosCount.setPlaytime(currunt_time);
	    		 time_minus = VmosCount.getPlaytime() - VmosCount.getPretime();
		    	 VmosCount.setInitime(time_minus);	 
	    	 }else{
	    		 //VmosCount.setPlaytime(currunt_time);
	    		 //time_minus = VmosCount.getPlaytime() - VmosCount.getBuffertime();
	    		 //VmosCount.setKaduntime(time_minus + VmosCount.getKaduntime());
	    	 }
    		 break; 
	    	 
	     case 2: //Buffering
	    	 if(VmosCount.getPlaytime() > 0){
		    	 VmosCount.setBuffertime(currunt_time);
		    	 VmosCount.setKadunnum(VmosCount.getKadunnum() + 1);
	    	 }
	    	 break; 
	    	 
	     case 3: //Buffer end Play
	    	 if(VmosCount.getKadunnum() > 0){
	    		 VmosCount.setBufferPlaytime(currunt_time);
	    		 time_minus = VmosCount.getBufferPlaytime() - VmosCount.getBuffertime();
	    		 VmosCount.setKaduntime(time_minus + VmosCount.getKaduntime());
	    	 }
    		 break; 
	    	 
	     default:
	    		 ;
	     }
	
	}
	  private OnInfoListener mInfoListener = new OnInfoListener() {
		    @Override
		    public boolean onInfo(MediaPlayer mp, int what, int extra) {
		      //Log.d("onInfo: (%d, %d)", what, extra);
		      if (mOnInfoListener != null) {
		        //mOnInfoListener.onInfo(mp, what, extra);
		      } else if (mMediaPlayer != null) {
		        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {       
		            mMediaPlayer.pause();
		            VmosUpdate(2);
		        } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
		        	mMediaPlayer.start();
		        	VmosUpdate(3);
		        }
		      }
		      return true;
		    }
		  };

}
