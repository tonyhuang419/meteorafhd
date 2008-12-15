package com.exam.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;
import org.apache.struts2.components.Component;
import org.apache.struts2.dispatcher.StrutsRequestWrapper;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 分页逻辑Bean
 * @author tangs
 */
public class Pages extends Component {
	
    private HttpServletRequest request;
    
    private String pageNo;
    private String total;
    private String styleClass;
    private String theme;
    private String includes;
    
    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }    
    public String getIncludes() {
		return includes;
	}
	public void setIncludes(String includes) {
		this.includes = includes;
	}
	public String getStyleClass() {
        return styleClass;
    }
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }    
    public String getPageNo() {
        return pageNo;
    }
    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }
    public String getTotal() {
        return total;
    }
    public void setTotal(String total) {
        this.total = total;
    }
    
    
    public Pages(ValueStack arg0, HttpServletRequest request) {
        super(arg0);
        this.request = request;
    }

    @Override
    public boolean start(Writer writer) {
    	
        boolean result = super.start(writer);
        try {
            StringBuilder str = new StringBuilder();
            Map cont=this.getStack().getContext();
            StrutsRequestWrapper req=(StrutsRequestWrapper)cont.get(StrutsStatics.HTTP_REQUEST);
            
            String url=(String)req.getAttribute("javax.servlet.forward.request_uri");
                       
            //从ValueStack中取出数值
            Object obj=this.getStack().findValue(pageNo);            
            pageNo = String.valueOf((Integer)obj);  
            obj=this.getStack().findValue(total);
            total = String.valueOf((Integer)obj);  
            
            StringBuffer perUrl=new StringBuffer("");
            if(includes!=null){
            	String[] perm=includes.split(",");
            	for(int i=0;i<perm.length;i++){
            		String permName=perm[i];
            		Object obje=this.getStack().findValue(permName);
            		 String vType=obje.getClass().toString();
            		
            		vType=vType.substring(vType.lastIndexOf(".")+1,vType.length());             		
            		perUrl.append("&"+permName);
            		if(vType.equals("String")){
            			String tmp= (String)this.getStack().findValue(permName);
            			perUrl.append("="+tmp);
            		}else if(vType.equals("Long")){
            			Long tmp= (Long)this.getStack().findValue(permName);
            			perUrl.append("="+tmp);
            		}else if(vType.equals("Float")){
            			Float tmp= (Float)this.getStack().findValue(permName);
            			perUrl.append("="+tmp);
            		}else if(vType.equals("Integer")){
            			Integer tmp= (Integer)this.getStack().findValue(permName);
            			perUrl.append("="+tmp);
            		}else if(vType.equals("Boolean")){
            			Boolean tmp= (Boolean)this.getStack().findValue(permName);
            			perUrl.append("="+tmp);
            		}
            	}            	
            }
            
            
            Integer cpageInt = Integer.valueOf(pageNo);
            str.append("<span ");
            if (styleClass != null) {
                str.append(" class='"+styleClass+"'>");
            } else {
                str.append(">");
            }
            
            //文本样式
            if (theme == null || "text".equals(theme)) {
                //当前页与总页数相等
                if (pageNo.equals(total)) {
                    //如果total = 1，则无需分页，显示“[第1页] [共1页]”
                    if ("1".equals(total)) {
                        str.append("[第 " + pageNo + " 页]");
                        str.append("&nbsp;[共 " + total + " 页]");
                    } else {
                        //到达最后一页,显示“[首页] [上一页] [末页]”
                        str.append("<a href='"+url+"?pageNo=1"+perUrl+"'>[首页]</a> "); 
                        str.append("<a href='"+url+"?pageNo=" + (cpageInt - 1)+perUrl+"'>[上一页]</a>" );
                        str.append(" <a href='"+url+"?pageNo=" + total+perUrl+"'>[末页]</a> ");
                    }
                } else {
                    //当前页与总页数不相同
                    if ("1".equals(pageNo)) {
                        //第一页，显示“[首页] [下一页] [末页]”
                        str.append("<a href='"+url+"?pageNo=1"+perUrl+"'>[首页]</a>");
                        str.append("<a href='"+url+"?pageNo=" + (cpageInt + 1) +perUrl+"'>[下一页]</a>");                       
                        str.append("<a href='"+url+"?pageNo=" + total +perUrl+"'>[末页]</a>");
                    } else {
                        //不是第一页，显示“[首页] [上一页] [下一页] [末页]”
                        str.append("<a href='"+url+"?pageNo=1"+perUrl+"'>[首页]</a>");
                        str.append("<a href='"+url+"?pageNo=" + (cpageInt - 1)+perUrl+"'>[上一页]</a>");
                        str.append("<a href='"+url+"?pageNo=" + (cpageInt + 1)+perUrl+"'>[下一页]</a>");
                        str.append("<a href='"+url+"?pageNo=" + total +perUrl+"'>[末页]</a>");
                    }
                }
            } else if ("number".equals(theme)) {  //数字样式 [1 2 3 4 5 6 7 8 9 10 > >>]
                Integer totalInt = Integer.valueOf(total);
               
                //如果只有一页，则无需分页
                str.append("[&nbsp;");
                if (totalInt == 1) {
                    str.append("<strong>1</strong>&nbsp;");
                } else {                    
                    if (cpageInt > 1) {
                        //当前不是第一组，要显示“<< <”
                        //<<：返回前一组第一页
                        //<：返回前一页
                        str.append("<a href='"+url+"?pageNo=1"+perUrl+"'>«</a>&nbsp;");
                        str.append("<a href='"+url+"?pageNo=" + (cpageInt - 1)+perUrl);
                        str.append("'>‹</a>&nbsp;" );
                    }else{                        	
                        str.append("«&nbsp;‹&nbsp;" );
                    }
                    
                    int v=(cpageInt-4)>0?(cpageInt-4):1;
                    int v1=(cpageInt+4)<totalInt?(cpageInt+4):totalInt;
                    if(v1==totalInt){
                    	v=totalInt-10;
                    }else if(v==1&&v1<totalInt){
                    	v1=totalInt>10?10:totalInt;
                    }
                    //10个为一组显示
                    for (int i = v; i <= v1; i++) {
                        if (cpageInt == i) { //当前页要加粗显示
                            str.append("<strong>"+i+"</strong>&nbsp;");                                 
                        }else{
                        	str.append("<a href='"+url+"?pageNo=" + i +perUrl+"'>" + i + "</a>&nbsp;");
                        }                            
                    }
                    //如果多于1组并且不是最后一组，显示“> >>”
                    if (cpageInt<totalInt) {
                        //>>：返回下一组最后一页
                        //>：返回下一页
                        str.append("<a href='"+url+"?pageNo=" + (cpageInt + 1)+perUrl);
                        str.append("'>›</a>&nbsp;" );
                        str.append("<a href='"+url+"?pageNo=" + totalInt+perUrl);
                        str.append("'>»</a>&nbsp;" );
                    }else{
                    	str.append("›&nbsp;»&nbsp;" );
                    }
                }
                str.append("]");
            }
            str.append("</span>");
            
            writer.write(str.toString());
            
        } catch (IOException ex) {
            Logger.getLogger(Pages.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}


