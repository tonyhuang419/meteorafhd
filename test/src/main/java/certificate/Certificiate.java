package certificate;

import java.io.FileInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

public class Certificiate {

	
	public void  demo() throws Exception{
		CertificateFactory cf=CertificateFactory.getInstance("X.509");
		FileInputStream in = new FileInputStream("c://out.csr");
		Certificate c=cf.generateCertificate(in);
		String s=c.toString();
	}
	
	public static void main(String[] args) throws Exception{
		Certificiate certificiate = new Certificiate();
		certificiate.demo();
	}
}
