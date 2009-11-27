package fhdone.venusframework.action;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public abstract class RestBaseAction<T> implements ModelDriven<T>, Serializable, Preparable {

	private static final long serialVersionUID = 3693284996669377224L;

	protected Log logger = LogFactory.getLog(this.getClass());
	
	protected T entity;  

	/** 
	 * GET: /{model} 
	 */  
	public abstract HttpHeaders index();  

	/** 
	 * POST: /{model} 
	 */  
	public abstract HttpHeaders create();  

	/** 
	 * PUT: /{model}/{id} 
	 */  
	public abstract String update();  

	/** 
	 * DELETE: /{model}/{id} 
	 */  
	public abstract String destroy();  

	/** 
	 * GET: /{model}/{id} 
	 */  
	public abstract HttpHeaders show();  

	/** 
	 * GET: /{model}/{id}/edit 
	 */  
	public abstract String edit();  

	/** 
	 * GET: /{model}/new 
	 */  
	public abstract String editNew();  

	@Override  
	public T getModel() {  
		return entity;  
	}  

	/** 
	 * 子类通过重写此方法初始化实体类 
	 */  
	@Override  
	public abstract void prepare();  


}
