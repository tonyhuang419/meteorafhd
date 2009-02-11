package axis2;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;

public class HelloWorld {
	public OMElement sayHello(OMElement in){
		String name=in.getText();
		String info=name+"HelloWorld!";
		OMFactory fac=OMAbstractFactory.getOMFactory();
		OMNamespace omNs=fac.createOMNamespace("http://127.0.0.1:8080/","hw");
		OMElement resp=fac.createOMElement("sayHelloResponse",omNs);
		resp.setText(info);
		return resp;
	}
}
