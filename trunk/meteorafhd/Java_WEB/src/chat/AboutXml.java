package chat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

     
public class AboutXml {
     
    private void BuildXMLDoc(String name,String info,String time) throws IOException, JDOMException {   
       // 创建根节点 list;
        Element root = new Element("reocrd");       
      
        // 根节点添加到文档中；
        Document doc = new Document(root);     
        
        // 此处 for 循环可替换成 遍历 数据库表的结果集操作;
       
    	   // 创建节点 user;
           Element elements = new Element("info");          
          
//           // 给 user 节点添加属性 id;
//           elements.setAttribute("id", "" + i);
               
           // 给 user 节点添加子节点并赋值；
           ////////////////////////////////////////////////
          
           // new Element("name")中的 "name" 替换成表中相应字段，setText("xuehui")中 "xuehui 替换成表中记录值；
           elements.addContent(new Element("name").setText(name));
           elements.addContent(new Element("info").setText(info));
           elements.addContent(new Element("time").setText(time));
    
           // 给父节点list添加user子节点;
           root.addContent(elements);
    
        XMLOutputter XMLOut = new XMLOutputter();
          
       // 输出 user.xml 文件；
        XMLOut.output(doc, new FileOutputStream("c:/info.xml"));
    }
    
    public boolean isExist(){
    	  File dir = new File("c:/info.xml");  
    	  if(dir.exists())
    		  return true;
    	  else
    		  return false;
    }
    
    public void createXml(String name,String info,String time) {
       try {
    	   AboutXml j2x = new AboutXml();
           System.out.println("生成 mxl 文件...");
           j2x.BuildXMLDoc(name,info,time);
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
    
    public void insertXml(String name,String info,String time){
    	try{
    		FileInputStream fi = new FileInputStream("c:/info.xml");
    		SAXBuilder sb = new SAXBuilder();
    		Document doc = sb.build(fi);
    		Element root = doc.getRootElement();
    		List list = root.getChildren();
    		
    		Element newinfo = new Element("info");
    		newinfo.addContent(new Element("name").setText(name));
    		newinfo.addContent(new Element("info").setText(info));
    		newinfo.addContent(new Element("time").setText(time));
	
    		list.add(newinfo);
    		
            XMLOutputter XMLOut = new XMLOutputter();
            XMLOut.output(doc, new FileOutputStream("c:/info.xml"));
             
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    
    public static void main(String args[])
    {
    	new AboutXml().insertXml("1","2","3");
    }
    
} 