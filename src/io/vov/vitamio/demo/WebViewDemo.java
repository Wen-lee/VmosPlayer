package io.vov.vitamio.demo;


import io.vov.vitamio.LibsChecker;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WebViewDemo extends Activity{
	
	private WebView mWebView;
    private EditText mUrledit;
	private Button  mMakesure;
	private TextView mLoadTime;
	private TextView mLoadTimeV;
	private TextView mContent;
	private TextView mContentV;
	private String mWebUrl;
	//private DataAdapter mAdapter;
	private String mTitle;
	private Long loadTime;
	private Long mWebcontentlength;
	
	@SuppressLint("NewApi") @Override
	public void onCreate(Bundle icicle) {
	    super.onCreate(icicle);
	    if (!LibsChecker.checkVitamioLibs(this))
	      return;
	    
	    setContentView(R.layout.web_view_demo);
	    mWebView = (WebView) findViewById(R.id.webview);
	    
	    mUrledit = (EditText) findViewById(R.id.weburledit);
	    mMakesure = (Button) findViewById(R.id.weburlsure);
	    mLoadTime = (TextView) findViewById(R.id.web_load_time);
	    mLoadTimeV = (TextView)findViewById(R.id.web_load_time_v);
	    mContent = (TextView) findViewById(R.id.web_content);
	    mContentV = (TextView) findViewById(R.id.web_content_v);
		
		mWebView.setWebChromeClient(new WebChromeClient());
		mWebView.setWebViewClient(new WebViewClient());

		
		mMakesure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v){
				mWebUrl = mUrledit.getText().toString();
				if(mWebUrl == ""){
					mWebUrl = "http://www.baidu.com";
				}
				
				if(mWebUrl.indexOf("www") == 0){
					mWebUrl = "http://" + mWebUrl;
				}
				
				if(mWebUrl.indexOf("http://") < 0){
				    Toast.makeText(
				    		WebViewDemo.this,
					             "URL SET WRONG ", Toast.LENGTH_LONG).show();
					return;
				}
				VmosCount.setvideopath(mWebUrl);
			    Toast.makeText(
			    		WebViewDemo.this,
				             "URL SET OK ", Toast.LENGTH_LONG).show();
			    
			    mWebView.loadUrl(mWebUrl);
			}});
		
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
	    StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		
		
		initWebView(); 
		
	}
	
	
	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" }) 
	private void initWebView() {
		mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
		mWebView.getSettings().setJavaScriptEnabled(true);	
		//mWebView.getSettings().setPluginState(PluginState.ON);  
		mWebView.getSettings().setAllowFileAccess(true);  
		mWebView.getSettings().setLoadWithOverviewMode(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setDomStorageEnabled(true);
		mWebView.getSettings().setDatabaseEnabled(true);
		mWebView.getSettings().setLoadWithOverviewMode(true);
		mWebView.getSettings().setUseWideViewPort(true);
		  
		mWebView.setWebViewClient(new WebViewClient() {

			/** 页面开始加载 */
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				loadTime = System.currentTimeMillis();
				//mUrl.setText(url);
				//mUrl.setVisibility(View.VISIBLE);
				
			}

			/** 页面加载完成 */
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				//mLoading.setVisibility(View.GONE);
				mWebView.setVisibility(View.VISIBLE);
				//if (!mHistory.contains(url))
				//	mHistory.add(0, url);
				//mUrl.setVisibility(View.GONE);
				
				mTitle = view.getTitle();
				loadTime = System.currentTimeMillis() - loadTime;
				
				HttpGetContentLength mHPDcontent = new HttpGetContentLength(mWebUrl);
				mWebcontentlength = mHPDcontent.getHttpContentLength();
				
				mLoadTimeV.setText(loadTime.toString());
				mContentV.setText(mWebcontentlength.toString());
			
			}
		});
	}

}
