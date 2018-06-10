package megascripts.graphic;

import java.util.ArrayList;

public class Log {

	public static ArrayList<String> LOG_INFO = new ArrayList<String>();
	
	public static void info(String s) {
		System.out.println("[MegaDungeon]: " + s);
	}

	public static String makelog(int floor , int complex , String Bossname , String Time){
		String f = "" + floor;
		String c = "" + complex;
		return "Floor: " + f + " || Complex: " + c + " || " + Bossname + " || "  +  Time ;
	}
	public static void add(String e){
		if(LOG_INFO.size() >= 4){
			String one = LOG_INFO.get(1);
			String two = LOG_INFO.get(2);
			String three = LOG_INFO.get(3);
			LOG_INFO.clear();
			LOG_INFO.add(one);
			LOG_INFO.add(two);
			LOG_INFO.add(three);
			LOG_INFO.add(e);
		}else{
			LOG_INFO.add(e);
		}
	}
}
