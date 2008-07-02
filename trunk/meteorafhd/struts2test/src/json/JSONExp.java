package json;

import org.apache.log4j.Logger;

import com.googlecode.jsonplugin.annotations.JSON;
import com.opensymphony.xwork2.Action;

public class JSONExp {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(JSONExp.class);

	private String field1 = null;
	//'transient' fields are not serialized
	private transient String field2 = null;
	//fields without getter method are not serialized
	private String field3 = null;

	public String execute() {
		logger.info(field1);
		logger.info(field2);
		logger.info(field3);
		field1 = "hahaah";
		return Action.SUCCESS;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}


	public void setField3(String field3) {
		this.field3 = field3;
	}

	public static Logger getLogger() {
		return logger;
	}
	
	@JSON(name="newName")
	public String getCustomName() {
		return this.getField1();
	}

}
