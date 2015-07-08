package io.vov.vitamio.demo;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.widget.VideoView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class VmosSet extends Activity{
	
	
	private Button  mVmosHPD;
	private Button  mVmosHLS;
	
	  public void onCreate(Bundle icicle) {
		    super.onCreate(icicle);
		    if (!LibsChecker.checkVitamioLibs(this))
		      return;
		    setContentView(R.layout.vmosset);
		    mVmosHPD = (Button) findViewById(R.id.button_hpd);
		    mVmosHLS = (Button) findViewById(R.id.button_hls);
		    
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
		    
	  }
	

}
