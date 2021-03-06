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

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import io.vov.vitamio.demo.VmosCount;
import io.vov.vitamio.demo.HttpGetContentLength;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VideoViewBuffer extends Activity implements OnInfoListener, OnBufferingUpdateListener {

  /**
   * TODO: Set the path variable to a streaming video URL or a local media file
   * path.
   */
  private String path;
  private Uri uri;
  private VideoView mVideoView;
  private ProgressBar pb;
  private TextView downloadRateView, loadRateView;
  
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
	private TextView resolution;
	private TextView resolution_v;
	
	private Integer mHeight;
	private Integer mWeight;
	private String mBitrate;
	
  @SuppressLint("NewApi") @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    if (!LibsChecker.checkVitamioLibs(this))
      return;
    setContentView(R.layout.videobuffer);
    mVideoView = (VideoView) findViewById(R.id.buffer);
    pb = (ProgressBar) findViewById(R.id.probar);

    downloadRateView = (TextView) findViewById(R.id.download_rate);
    loadRateView = (TextView) findViewById(R.id.load_rate);
    
	mIniTime = (TextView) findViewById(R.id.veiw_initial_time);
	mKadun = (TextView) findViewById(R.id.veiw_kadun_num);
	mKaduntime = (TextView) findViewById(R.id.veiw_kadun_time);
	mVmos = (Button) findViewById(R.id.veiw_vmos);
	mIniTime_v = (TextView) findViewById(R.id.veiw_initial_time_v);
	mKadun_v = (TextView) findViewById(R.id.veiw_kadun_num_v);
	mKaduntime_v = (TextView) findViewById(R.id.veiw_kadun_time_v);
	mVmos_v = (TextView) findViewById(R.id.veiw_vmos_v);
	bit_rate = (TextView) findViewById(R.id.veiw_bit_rate);
	bit_rate_v = (TextView) findViewById(R.id.veiw_bit_rate_v);
	resolution = (TextView) findViewById(R.id.veiw_resolution);
	resolution_v = (TextView) findViewById(R.id.veiw_resolution_v);
	
	switch(VmosCount.getvideotype()){
		case 1: //hpd
			path = "http://183.60.145.50/youku/67739A92D9E478123A0AC66274/030008010051001884037C05A3C1C42389B547-5747-5466-4525-602B43670F71.mp4";
			//path = "http://113.107.113.145/youku/69783228D1D4C82143108631EF/030002010051EB3E723C2D061A18663030D906-BF2B-733E-C25F-8B18F1B1518A.flv";
			break;
		case 2: // hls
			path = "http://pl.youku.com/playlist/m3u8?ctype=12&ep=cCaXHEmNVswI7SPWjD8bYiSxJnRbXP8M9BiFgdBlBtQgSOq2%0D%0A&ev=1&keyframe=1&oip=459980061&sid=24360002880941258cb3d&token=4029&type=hd2&vid=162700276";
			break;
		case 3:
			path = VmosCount.getvideopath();
			break;
		case 4:
			path = "http://pl.youku.com/playlist/m3u8?ctype=12&ep=cSaXHECJX8sB7CLciT8bYn6wIHQKXP8M9hqAhdtjAdQjTuy%2F%0D%0A&ev=1&keyframe=1&oip=3658602415&sid=3436949519131125bbd35&token=7640&type=mp4&vid=160554911";
			break;
		case 5:
			path = "http://pl.youku.com/playlist/m3u8?ctype=12&ep=dCaXHECJX8YJ4yPZiD8bY33idnAGXP8O%2Fh%2BGh9NiBNQnTOq7%0D%0A&ev=1&keyframe=1&oip=3658602415&sid=6436949896060124a0279&token=3424&type=flv&vid=148036104";
			break;
		default:
				path ="";
	}
	
    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
    StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
	//add by lw
    VmosCount.VmosInitial();
	VmosCount.VmosUpdate(0); // Video Begin
	
    if (path == "") {
      // Tell the user to provide a media file URL/path.
      Toast.makeText(
          VideoViewBuffer.this,
          "Please edit VideoBuffer Activity, and set path"
              + " variable to your media file URL/path", Toast.LENGTH_LONG).show();
      return;
    } else {
      /*
       * Alternatively,for streaming media you can use
       * mVideoView.setVideoURI(Uri.parse(URLstring));
       */
      uri = Uri.parse(path);
      mVideoView.setVideoURI(uri);
      mVideoView.setMediaController(new MediaController(this));
      mVideoView.requestFocus();
      mVideoView.setOnInfoListener(this);
      mVideoView.setOnBufferingUpdateListener(this);
      mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
      @Override
      public void onPrepared(MediaPlayer mediaPlayer) {
          // optional need Vitamio 4.0
          mediaPlayer.setPlaybackSpeed(1.0f);
          
      	  //add by lw
	      mHeight = mVideoView.getVideoHeight();
	      mWeight = mVideoView.getVideoWidth();
	      String resolution =mWeight.toString()+ "*" + mHeight.toString();
	      VmosCount.setvideoWidth(mWeight);
	      VmosCount.setvideoHeight(mHeight);
	      Long duration = mVideoView.getDuration();
	      VmosCount.setDurarionn(duration.doubleValue());//
	      VmosCount.setResolution(resolution);
          VmosShow();
        }
      });
      
      
		//add by lw
		mVmos.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v){
				
				String mM3u8Str = "m3u8";
				int indexM3U8 = path.indexOf(mM3u8Str);
				if(indexM3U8 > 0){ // HLS
					
					M3U8Parser mM3u8 = new M3U8Parser();
					Map<String,String> parseUrlMap = M3U8Parser.parseStringFromUrl(path);
					Set set = parseUrlMap.entrySet();         
					Iterator i = set.iterator();
					float durationSum = 0;
					float contentSum = 0;
					while(i.hasNext()){
					     Map.Entry<String, String> entry1=(Map.Entry<String, String>)i.next();    
					  
					     String timeExif = entry1.getKey();
					     HttpGetContentLength httpentity = new HttpGetContentLength(entry1.getValue());
					     Long contenlength = httpentity.getHttpContentLength();
					     if( contenlength < 0){
					        Toast.makeText(
					        		VideoViewBuffer.this,
					        	    "Content length: " + contenlength.toString(), Toast.LENGTH_LONG).show();
					        	    mBitrate = "NULL";
					     }else{

					        String timeString = timeExif.substring(8, timeExif.length());
					        Float timeStrtoFloat = Float.parseFloat(timeString.substring(0,timeString.length()-1));
					        Toast.makeText(
					        		VideoViewBuffer.this,
					        	    "Content length: " + contenlength.toString() +" Dutation:"+ timeStrtoFloat.toString(), Toast.LENGTH_LONG).show();
					            
					        Float bitrate = (contenlength/timeStrtoFloat)*8/1000;
					        mBitrate = bitrate.toString();
					        durationSum = durationSum + timeStrtoFloat;
					        contentSum = contentSum + contenlength;
					     }

					        VmosCount.setBitrate(mBitrate);
					      	VmosShow();
					}
					
			     	 Float avrBitrate = (contentSum/durationSum)*8/1000 ;
			     	 mBitrate = avrBitrate.toString();
					 VmosCount.setBitrate(mBitrate);
					 VmosCount.setDurarionn((double)durationSum*1000);
				     VmosShow();
				}else{// HPD
					
				    Long mDuration;
				    Long mHPDcontentlength;
					mDuration = mVideoView.getDuration();
					HttpGetContentLength mHPDcontent = new HttpGetContentLength(path);
					mHPDcontentlength = mHPDcontent.getHttpContentLength();
					Float bitrate = (mHPDcontentlength/mDuration.floatValue())*8; // here time is ms
					
					Toast.makeText(
			        		VideoViewBuffer.this,
			        	    "Content length: " + mHPDcontentlength.toString() +" Dutation:"+ mDuration.toString(), Toast.LENGTH_LONG).show();
					mBitrate = bitrate.toString();
					VmosCount.setBitrate(mBitrate);
					VmosCount.setDurarionn(mDuration.doubleValue());
					VmosShow();
				}	
			}
		});
		
      
    }

  }

  @Override
  public boolean onInfo(MediaPlayer mp, int what, int extra) {
    switch (what) {
    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
      if (mVideoView.isPlaying()) {
        mVideoView.pause();
        pb.setVisibility(View.VISIBLE);
        downloadRateView.setText("");
        loadRateView.setText("");
        downloadRateView.setVisibility(View.VISIBLE);
        loadRateView.setVisibility(View.VISIBLE);
        
		VmosCount.VmosUpdate(2); // Vedio Stalling
		VmosShow();
      }
      break;
    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
      mVideoView.start();
      pb.setVisibility(View.GONE);
      downloadRateView.setVisibility(View.GONE);
      loadRateView.setVisibility(View.GONE);
      
      if(VmosCount.getPreendtime() == 0){
    	  VmosCount.VmosUpdate(1); // Video Begin
      }else{
    	  VmosCount.VmosUpdate(3); // Vedio Stalling
      }
	  VmosShow();
      break;
    case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
      downloadRateView.setText("" + extra + "kb/s" + "  ");
      break;
    }
    return true;
  }

  @Override
  public void onBufferingUpdate(MediaPlayer mp, int percent) {
    loadRateView.setText(percent + "%");
  }
  
	//add by lw
	private void VmosShow(){

		resolution_v.setText(VmosCount.getResolution());
		bit_rate_v.setText(VmosCount.getBitrate());
		mIniTime_v.setText(VmosCount.getInitime().toString()+"  ms");
		mKadun_v.setText(VmosCount.getKadunnum().toString()+"  ��");
		mKaduntime_v.setText(VmosCount.getKaduntime().toString()+"  ms");
		mVmos_v.setText(VmosCount.getVmos_num().toString());
	    Toast.makeText(
	            VideoViewBuffer.this,
	             " Q:"+VmosCount.getsQuality().toString()+" L:"+VmosCount.getsLoading().toString()+" S:"+VmosCount.getsStaling().toString(), Toast.LENGTH_LONG).show();
	}
	

	

}
