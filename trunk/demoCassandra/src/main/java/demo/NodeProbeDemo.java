package demo;
import java.io.IOException;

import org.apache.cassandra.tools.NodeProbe;


public class NodeProbeDemo {

	public static void main(String[] args) throws IOException, InterruptedException {
		NodeProbe n = new NodeProbe("127.0.0.1"  );
		n.printInfo(System.out);
		System.out.println("==============");
		n.printRing(System.out);
	}

}
