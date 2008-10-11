package helloworld;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class FileHelloStr{
	//protected static final Log log=LogFactory.getLog(FileHelloStr.class);
	private String profilename;
	public FileHelloStr(String _profilename){
		this.profilename = _profilename;

	}
	public String getContent(){
		String helloworld="";
		try{
			Properties properties=new Properties();
			
//			Properties p = new Properties();
//			p.load(this.getClass().getResourceAsStream(profilename));
//			String url = p.getProperty("helloworld");
			
			InputStream is = this.getClass().getResourceAsStream(profilename);
			properties.load(is);
			is.close();
			helloworld = properties.getProperty("helloworld");
		}catch(FileNotFoundException ex){
			//log.error(ex);
		}catch(IOException ex){
			// log.error(ex);
		}
		return  helloworld;
	}
}
