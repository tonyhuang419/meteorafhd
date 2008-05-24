package file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


class Tool{
	private String dirName = "G://";
	private List<String> nameList = new ArrayList<String>();
	
	private Date setDate = new Date(107,11,24,0,0,0);//107,11,3,0,0,0 2007.12.3
	private Date fileDate;
	private long ftime;
	public void getSubFile(String parent)
	{			
		if(parent.equals("G:\\\\RECYCLER")||parent.equals("G:\\\\Kugoo")||
				parent.equals("G:\\\\System Volume Information")||parent.equals("G:\\\\langStu"))
			return;
		
		File parentF = new File(parent);
		if(parentF.isFile()){
			ftime = parentF.lastModified();
			//System.out.println(setTime);
			//System.out.println(ftime+"aaaaaa");
			fileDate = new Date(ftime);
			if(fileDate.after(setDate)){
				nameList.add(parent);
				return;
			}
			else
				return;
		}
		String[] subFiles = parentF.list();
		//String path = null;

		for (int i = 0; i < subFiles.length; i++){
			getSubFile(parentF.getAbsolutePath() + System.getProperty("file.separator") + subFiles[i]);
		}
	}

	public List<String> getNameList()
	{
		return nameList;
	}

	public boolean dirExist(String dirName){
		File srcDir = new File(dirName);
		if(!srcDir.exists()){
			System.out.println("Dir not exists!");
			return false;
		}
		return true;
	}

	public void writeFile() throws Exception{	
		Tool toolx = new Tool();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("c:/Catalog.txt"));
		
		toolx.getSubFile(dirName);
		for (Iterator iter = toolx.getNameList().iterator(); iter.hasNext();)
		{
			String element = (String) iter.next();
			//System.out.println(element);
			bw.write(element+"\r\n");
		}
		bw.flush();
		bw.close();
	}

	public String getDirName() {
		return dirName;
	}
}


public class ReadName{
	public static void main(String[] args)  throws Exception
	{
		Tool toolx = new Tool();
		String dirNamex = toolx.getDirName();
		if(toolx.dirExist(dirNamex) == true){
			toolx.writeFile();
		}
		else{
			System.out.println("Error");
		}
	}
}