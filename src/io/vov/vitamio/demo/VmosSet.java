package io.vov.vitamio.demo;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.widget.VideoView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class VmosSet extends Activity{
	
	private Button  makesure;
	private Button  mVmosHPD;
	private Button  mVmosHLS;
	private Button  mVmosHLS2;
	private Button  mVmosHLS3;
    private EditText mUrledit;
	
	
	  public void onCreate(Bundle icicle) {
		    super.onCreate(icicle);
		    if (!LibsChecker.checkVitamioLibs(this))
		      return;
		    setContentView(R.layout.vmosset);
		    mVmosHPD = (Button) findViewById(R.id.button_hpd);
		    mVmosHLS = (Button) findViewById(R.id.button_hls);
		    makesure = (Button) findViewById(R.id.urlsure);
		    mUrledit = (EditText)findViewById(R.id.urledit);
		    mVmosHLS2 = (Button) findViewById(R.id.button_hls2);
		    mVmosHLS3 = (Button) findViewById(R.id.button_hls3);
		    
		    mVmosHPD.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v){
					VmosCount.setvideotype(1);
				    Toast.makeText(
				    	VmosSet.this,
				             "HPD SET OK ", Toast.LENGTH_LONG).show();
				}});
		    
		    mVmosHLS.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v){
					VmosCount.setvideotype(2);
				    Toast.makeText(
					    	VmosSet.this,
					             "HLS SET OK ", Toast.LENGTH_LONG).show();
				}});
		    
		    mVmosHLS2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v){
					VmosCount.setvideotype(4);
				    Toast.makeText(
					    	VmosSet.this,
					             "HLS SET OK ", Toast.LENGTH_LONG).show();
				}});
		    
		    mVmosHLS3.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v){
					VmosCount.setvideotype(5);
				    Toast.makeText(
					    	VmosSet.this,
					             "HLS SET OK ", Toast.LENGTH_LONG).show();
				}});
		    
		    makesure.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v){
					VmosCount.setvideotype(3);
					String url = mUrledit.getText().toString();
					if(url.indexOf("http://") < 0){
					    Toast.makeText(
						    	VmosSet.this,
						             "URL SET WRONG ", Toast.LENGTH_LONG).show();
						return;
					}
					VmosCount.setvideopath(url);
				    Toast.makeText(
					    	VmosSet.this,
					             "URL SET OK ", Toast.LENGTH_LONG).show();
				    
				    
				}});
		    
	  }
	

}
