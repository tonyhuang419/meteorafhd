package file;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


class ToolDel implements FilenameFilter{
	public void del(String filepath) throws IOException{
		File f = new File(filepath);//定义文件路径   
		File delFile[]=f.listFiles();
		int i = delFile.length;
		for(int j=0;j<i;j++){
			delFile[j].delete();//删除文件
		}
	}
	public boolean accept(File f, String name) {
		return  name.endsWith("txt");
	}

//	public void intervalDel() {
//		int delay = 1000;
//		int period = 3000;
//		Timer timer = new Timer();
//		timer.scheduleAtFixedRate(new TimerTask() {
//			public void run() {
//				ToolDel t = new ToolDel();
//				try {
//					//System.out.println("1");
//					t.del("C://Documents and Settings/fhd/Cookies");
//				} catch (IOException e) {
//					e.printStackTrace();
//				}				
//			}
//		},delay, period);
//	}
}
public class DelFiles{
	public static void main(String[] args)  throws Exception{
		ToolDel t = new ToolDel();
		//t.intervalDel();
		t.del("C://Documents and Settings/fhd/Cookies");
		
	}
}