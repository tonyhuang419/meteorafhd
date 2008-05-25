package file;


import java.io.File;
import java.io.FilenameFilter;

public   class   FilterDemo2 implements FilenameFilter {
	public   static   void   main(String   a[]){  
		File f  = new File(".");
		String[] listx ;
		listx = f.list(new FilterDemo());
		for(int i=0 ; i<listx.length; i++)
			System.out.println(listx[i]);
	}

	public boolean accept(File f, String name) {
			return  name.endsWith("as");
	}  
}
