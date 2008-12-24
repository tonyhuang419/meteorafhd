package com.exam.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.components.Component;
import org.apache.struts2.dispatcher.StrutsRequestWrapper;

import com.exam.utils.PageInfo;
import com.opensymphony.xwork2.util.ValueStack;
public class Pages extends Component {

	private String styleClass;
	private String baseAction;
	private String beanName;
	
	public Pages(ValueStack arg0) {
		super(arg0);
	}

	@Override
	public boolean start(Writer writer) {
		boolean result = super.start(writer);
		PageInfo pi = (PageInfo)getStack().findValue(beanName);
		getStack().findValue("pageNo");
		int pageNo = 1;

		int total = pi.getTotalPageCount();
		StringBuilder str = new StringBuilder();
		StrutsRequestWrapper req=(StrutsRequestWrapper)this.getStack().getContext().get(StrutsStatics.HTTP_REQUEST);
		String url=(String)req.getAttribute("javax.servlet.forward.context_path");
		url = url + "/"+baseAction;
		
		String pageNoStr = req.getParameter("pageNo");
		if(  pageNoStr != null){
			pageNo = Integer.valueOf( pageNoStr );
		}else{
			pageNo = 1;
		}

		str.append("<span ");
		if (StringUtils.isNotBlank(styleClass)) {
			str.append(" class='"+styleClass+"'>");
		} else {
			str.append(">");
		}

		//数字样式 < <<1 2 3 4 5 6 7 8 9 10 > >>
		//如果只有一页，则无需分页
		str.append("&nbsp;");
		if (total == 1) {
			str.append("<strong>1</strong>&nbsp;");
		} else {                    
			if (pageNo > 1) {
				//当前不是第一组，要显示“<< <”
				//<<：返回前一组第一页
				//<：返回前一页
				str.append("<a href='"+url+"&pageNo=1"+"'>&laquo;</a>&nbsp;");
				str.append("<a href='"+url+"&pageNo=" + (pageNo - 1) ).append("'>&lsaquo;</a>&nbsp;" );
			}else{                        	
				str.append("&laquo;&nbsp;&lsaquo;&nbsp;" );
			}

			int v=(pageNo-4)>0?(pageNo-4):1;
			int v1=(pageNo+4)<total?(pageNo+4):total;
			if(v1==total){
				v=total-10;
			}else if(v==1&&v1<total){
				v1=total>10?10:total;
			}
			//10个为一组显示
			for (int i = v; i <= v1; i++) {
				if (pageNo == i) { //当前页要加粗显示
					str.append("<strong>"+i+"</strong>&nbsp;");                                 
				}else{
					str.append("<a href='"+url+"&pageNo=" + i +"'>" + i + "</a>&nbsp;");
				}                            
			}
			//如果多于1组并且不是最后一组，显示“> >>”
			if (pageNo<total) {
				//>>：返回下一组最后一页
				//>：返回下一页
				str.append("<a href='"+url+"&pageNo=" + (pageNo + 1)).append("'>&rsaquo;</a>&nbsp;" );
				str.append("<a href='"+url+"&pageNo=" + total).append("'>&raquo;</a>&nbsp;" );
			}else{
				str.append("&raquo;&nbsp;&nbsp;&nbsp;" );
			}
		}
		str.append("</span>");
		try {
			writer.write(str.toString());
		} catch (IOException ex) {
			Logger.getLogger(Pages.class.getName()).log(Level.SEVERE, null, ex);
		}
		return result;
	}


	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getBaseAction() {
		return baseAction;
	}

	public void setBaseAction(String baseAction) {
		this.baseAction = baseAction;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

}


