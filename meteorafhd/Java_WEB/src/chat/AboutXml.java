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
       // �������ڵ� list;
        Element root = new Element("reocrd");       
      
        // ���ڵ���ӵ��ĵ��У�
        Document doc = new Document(root);     
        
        // �˴� for ѭ�����滻�� ���� ���ݿ��Ľ��������;
       
    	   // �����ڵ� user;
           Element elements = new Element("info");          
          
//           // �� user �ڵ�������� id;
//           elements.setAttribute("id", "" + i);
               
           // �� user �ڵ�����ӽڵ㲢��ֵ��
           ////////////////////////////////////////////////
          
           // new Element("name")�е� "name" �滻�ɱ�����Ӧ�ֶΣ�setText("xuehui")�� "xuehui �滻�ɱ��м�¼ֵ��
           elements.addContent(new Element("name").setText(name));
           elements.addContent(new Element("info").setText(info));
           elements.addContent(new Element("time").setText(time));
    
           // �����ڵ�list���user�ӽڵ�;
           root.addContent(elements);
    
        XMLOutputter XMLOut = new XMLOutputter();
          
       // ��� user.xml �ļ���
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
           System.out.println("���� mxl �ļ�...");
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