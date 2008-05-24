package chatServlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.ProcessingInstruction;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;



public class AboutRecord {

	private void BuildXMLDoc(String name,String info,String time) throws IOException, JDOMException {   
		Element root = new Element("reocrd");       
		//Document doc = new Document(root);     
		Document doc = new Document(); 
		ProcessingInstruction pi = new ProcessingInstruction
		("xml-stylesheet","href=\"recordLog.xsl\" type=\"text/xsl\"");	 
		doc.addContent(pi); 
		
		Element elements = new Element("allinfo");
		elements.addContent(new Element("name").setText(name));
		elements.addContent(new Element("info").setText(info));
		elements.addContent(new Element("time").setText(time));
		doc.addContent(root);
		root.addContent(elements);

		XMLOutputter XMLOut = new XMLOutputter();
		XMLOut.output(doc, new FileOutputStream("c:/record.xml"));
	}

	public boolean isExist(){
		File dir = new File("c:/record.xml");  
		if(dir.exists())
			return true;
		else
			return false;
	}   
	
	public void createXml(String name,String info,String time) {
		if(isExist()==true){
			return;
		}
		else{
			try {
				//System.out.println("生成 mxl 文件...");
				AboutRecord j2x = new AboutRecord();
				j2x.BuildXMLDoc(name,info,time);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void insertXml(String name,String info,String time){
		if(isExist()==false){
			createXml(name,info,time);
		}
		else{
			try{
				FileInputStream fi = new FileInputStream("c:/record.xml");
				SAXBuilder sb = new SAXBuilder();
				Document doc = sb.build(fi);
				Element root = doc.getRootElement();
				List list = root.getChildren();

				Element newinfo = new Element("allinfo");
				newinfo.addContent(new Element("name").setText(name));
				newinfo.addContent(new Element("info").setText(info));
				newinfo.addContent(new Element("time").setText(time));

				list.add(newinfo);

				XMLOutputter XMLOut = new XMLOutputter();
				XMLOut.output(doc, new FileOutputStream("c:/record.xml"));
				fi.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
	
	public  Iterator<Info> readXml(){
		Iterator<Info> it = null;
		try{
			FileInputStream fi = new FileInputStream("c:/record.xml");
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(fi);
			Element root = doc.getRootElement();
			List list = root.getChildren();
			Element infos = null;
			
			
			String name,info,time;
			 for (int i = 0; i < list.size(); i++) {
				 	infos = (Element) list.get(i);		 	
				 	name = infos.getChild("name").getText();
				 	info = infos.getChild("info").getText();
				 	time = infos.getChild("time").getText();
				 	CollectionX.addList(name, info,time);
			 }
			 it = CollectionX.readList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return it;
	}
//	public static void main(String args[])
//	{
//		new AboutRecord().insertXml("1","2","3");
//	}
} 



























