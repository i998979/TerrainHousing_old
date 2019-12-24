package to.epac.factorycraft.TerrainHousing.Utils;

import java.util.Date;

public class Utils {
	public static long getTimeInt() {
		java.util.Date now = new Date();
		long i = now.getTime();
		
		return i / 1000;
	}
	
	public static String getTime(Long timeLeft) {
        int seconds = (int) (timeLeft / 1000);
        long sec = seconds % 60;
        long minutes = seconds % 3600 / 60;
        long hours = seconds % 86400 / 3600;
        long days = seconds / 86400;
        
        return days + " days, " + hours + " hour(s), " + minutes + " minute(s) and " + sec + " second(s) ";
    }
}
