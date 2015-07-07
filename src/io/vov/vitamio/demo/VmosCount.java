package io.vov.vitamio.demo;

public class VmosCount {
	 	private static Long ini_time = 0L;  
	    private static Long kadun_num = 0L;  
	    private static Long kadun_time = 0L;  
	    private static Double vmos_num = 0.0;  
	    
	    private static Long pre_time = 0L;
	    private static Long play_time = 0L;
	    private static Long buffer_time = 0L;
	    private static Long buffer_time_play = 0L;
	    
	    private static String bitrate = "";
	    private static String resolution = "";
	    
	    public static Long getPretime() {  
	        return pre_time;  
	    }  
	      
	    public static void setPretime(Long a) {  
	    	VmosCount.pre_time = a;  
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
	    
	    public static Double getVmos_num() {
	    	vmos_num = 5*(0.092*ini_time.doubleValue() + 0.108*kadun_time.doubleValue())/1000 ;
	        return vmos_num;  
	    }  
	      
	    public static void setVmosnum(Double a) {  
	    	VmosCount.vmos_num = a;  
	    } 
	    
	    
	    public static void VmosUpdate(int state){
		     Long currunt_time;
		     Long time_minus;
		     currunt_time = System.currentTimeMillis();
		     
		     switch(state){
		     case 0: //Initial
		    	 VmosCount.setPretime(currunt_time);
		    	 break;
		    	 
		     case 1: //Play
		    	 if(VmosCount.getKadunnum() == 0){
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
	    
	    
}
