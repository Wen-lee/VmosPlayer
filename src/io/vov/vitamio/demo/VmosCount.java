package io.vov.vitamio.demo;

import java.text.DecimalFormat;

public class VmosCount {
	 	private static Long ini_time = 0L;
	    private static Long kadun_num = 0L;  
	    private static Long kadun_time = 0L;  
	    private static Double vmos_num = 0.0;  
	    
	    private static Long pre_time = 0L;
	    private static Long pre_endtime = 0L;  
	    private static Long play_time = 0L;
	    private static Long buffer_time = 0L;
	    private static Long buffer_time_play = 0L;
	    
	    private static Integer videoWidth = 0;
	    private static Integer videoHeight = 0;
	    
	    private static String bitrate = "";
	    private static String resolution = "";
	    
	    private static Double durarion = 0.0;
	    
	    private static int videotype = 0;  // 1 for hpd, 2 for hls
	    
	    public static Long getPretime() {  
	        return pre_time;  
	    }  
	      
	    public static void setPretime(Long a) {  
	    	VmosCount.pre_time = a;  
	    } 
	    
	    public static Long getPreendtime() {  
	        return pre_endtime;  
	    }  
	      
	    public static void setPreendtime(Long a) {  
	    	VmosCount.pre_endtime = a;  
	    } 
	    public static Long getPlaytime() {  
	        return play_time;  
	    }  
	      
	    public static void setPlaytime(Long a) {  
	    	VmosCount.play_time = a;  
	    } 
	    
	    public static Long getBuffertime() {  
	        return buffer_time;  
	    }  
	      
	    public static void setBuffertime(Long a) {  
	    	VmosCount.buffer_time = a;  
	    }
	    
	    public static Long getBufferPlaytime() {  
	        return buffer_time_play;  
	    }  
	      
	    public static void setBufferPlaytime(Long a) {  
	    	VmosCount.buffer_time_play = a;  
	    }  
	    
	    //
	    public static Long getInitime() {  
	        return ini_time;  
	    }  
	      
	    public static void setInitime(Long a) {  
	    	VmosCount.ini_time = a;  
	    } 
		
	    public static Long getKadunnum() {  
	        return kadun_num;  
	    }  
	      
	    public static void setKadunnum(Long a) {  
	    	VmosCount.kadun_num = a;  
	    } 
	    
	    public static Long getKaduntime() {  
	        return kadun_time;  
	    }  
	      
	    public static void setKaduntime(Long a) {  
	    	VmosCount.kadun_time = a;  
	    } 
	    
	    
	    public static String getBitrate() {  
	        return bitrate;  
	    }  
	      
	    public static void setBitrate(String a) {  
	    	VmosCount.bitrate = a;  
	    } 
	    
	    public static String getResolution() {  
	        return resolution;  
	    }  
	      
	    public static void setResolution(String a) {  
	    	VmosCount.resolution = a;  
	    } 
	    
	    public static Double getDurarion() {  
	        return durarion;  
	    }  
	      
	    public static void setDurarionn(Double a) {  
	    	VmosCount.durarion = a;  
	    } 
	    
	    public static Integer getvideoWidth() {  
	        return videoWidth;  
	    }  
	      
	    public static void setvideoWidth(Integer a) {  
	    	VmosCount.videoWidth = a;  
	    } 
	    
	    public static Integer getvideoHeight() {  
	        return videoHeight;  
	    }  
	      
	    public static void setvideoHeight(Integer a) {  
	    	VmosCount.videoHeight = a;  
	    } 
	    
	    
	    public static int getvideotype() {  
	        return videotype;  
	    }  
	      
	    public static void setvideotype(int a) {  
	    	VmosCount.videotype = a;  
	    } 
	    
/*    
	    public static Double getVmos_num() {
	    	vmos_num = 5*(0.092*ini_time.doubleValue() + 0.108*kadun_time.doubleValue())/1000;
	    	DecimalFormat  df   = new DecimalFormat("######0.00000");   
	    	df.format(vmos_num);
	        return vmos_num;  
	    }  
*/
	    public static Double getVmos_num() {
	    	
	    	Double  quality = getsQuality();
	    	Double  loading = getsLoading();
	    	Double  stalling = getsStaling();
	    	//loading = 5.0;
	    	//stalling = 5.0;
	    	double p1 = 0.23;
	    	double p2 = 0.27;
	    	
	    	vmos_num = quality*((1/(5*(p1 + p2)))*(p1*loading + p2*stalling));
	    	DecimalFormat  df   = new DecimalFormat("######0.000"); 
	    	df.format(vmos_num);
	        return vmos_num;  
	    }  
	    
	    public static void setVmosnum(Double a) {  
	    	VmosCount.vmos_num = a;  
	    } 
	    
	    public static Double getsQuality() {
	    	Double sQuality;
	    	Integer mWidth = getvideoHeight(); // height here
	    	if(mWidth > 2160){ // 5K or more  2880 
	    		sQuality = 5.0;
	    	}else if(mWidth > 1440 && mWidth <= 2160){ // 4k
	    		sQuality = 4.9;
	    	}else if(mWidth > 1080 && mWidth <= 1440){ // 2k
	    		sQuality = 4.8;
	    	}else if(mWidth > 720 && mWidth <= 1080){ // 1080P
	    		sQuality = 4.5;
	    	}else if(mWidth > 480 && mWidth <= 720){ // 720P
	    		sQuality = 4.0;
	    	}else if(mWidth > 360 && mWidth <= 480){ // 480 p
	    		sQuality = 3.6;
	    	}else{ // 360P
	    		sQuality = 2.8;
	    	}
	    	
	    	return sQuality;
	    }
	    
	    public static Double getsLoading() {
	    	Double sLoading;
	    	Long sLoadingTime = getInitime();
	    	if(sLoadingTime <= 100){ //
	    		sLoading = 5.0;
	    	}else if(sLoadingTime > 100 && sLoadingTime <= 1000){ // 
	    		sLoading = 4.0;
	    	}else if(sLoadingTime > 1000 && sLoadingTime <= 3000){ // 
	    		sLoading = 3.0;
	    	}else if(sLoadingTime > 3000 && sLoadingTime <= 5000){ // 
	    		sLoading = 2.0;
	    	}else if(sLoadingTime > 5000 && sLoadingTime <= 10000){ // 
	    		sLoading = 1.0;
	    	}else{ // add by myself
	    		sLoading = 0.5;
	    	}
	    	
	    	return sLoading;
	    }
	    
	    public static Double getsStaling() {
	    	Double sStaling;
	    	Double buffertime;
	    	Double duration= getDurarion();
	    	Double buff_percent;
	    	
	    	buffertime = getBuffertime().doubleValue();
	    	
	    	if(duration == 0.0){
	    		sStaling = 5.0;
	    		return sStaling;
	    	}
	    	buff_percent = buffertime/duration;
	    	
	    	if(buff_percent == 0.0){ //
	    		sStaling = 5.0;
	    	}else if(buff_percent > 0.0 && buff_percent <= 0.05){ // 
	    		sStaling = 4.0;
	    	}else if(buff_percent > 0.05 && buff_percent <= 0.1){ // 
	    		sStaling = 3.0;
	    	}else if(buff_percent > 0.1 && buff_percent <= 0.15){ // 
	    		sStaling = 2.0;
	    	}else if(buff_percent > 0.15 && buff_percent <= 0.3){ // 
	    		sStaling = 1.0;
	    	}else{ // add by myself
	    		sStaling = 0.5;
	    	}
	    	
	    	return sStaling;
	    }
	    public static void VmosUpdate(int state){
		     Long currunt_time;
		     Long time_minus;
		     currunt_time = System.currentTimeMillis();
		     
		     switch(state){
		     case 0: //Initial
		    	 VmosCount.setPretime(currunt_time);
		    	 break;
		    	 
		     case 1: //Initial end
		    	 if(VmosCount.getKadunnum() == 0){
		    		 VmosCount.setPreendtime(currunt_time);
		    		 time_minus = VmosCount.getPreendtime() - VmosCount.getPretime();
			    	 VmosCount.setInitime(time_minus);	 
		    	 }
	    		 break; 
		    	 
		     case 2: //Buffering
		    	 if(VmosCount.getPreendtime() > 0){
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
	    
	    public static void VmosInitial(){ 
		 	ini_time = 0L;  
		    kadun_num = 0L;  
		    kadun_time = 0L;  
		    vmos_num = 0.0;  
		    
		    pre_time = 0L;
		    play_time = 0L;
		    buffer_time = 0L;
		    buffer_time_play = 0L;
		    
		    bitrate = "";
		    resolution = "";
	    }
	    
	    
}
