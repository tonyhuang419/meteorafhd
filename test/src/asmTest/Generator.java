package asmTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;


//run me first   then run test
public class Generator {
	public static void main(String[] args) throws Exception {
		InputStream is = new FileInputStream("target/classes/asmTest/Account.class" );
		ClassReader cr = new ClassReader(is);
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		ClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw);
		cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
		byte[] data = cw.toByteArray();
		
		File file = new File("target/classes/asmTest/Account.class");
//		System.out.println(file.getAbsolutePath());
		
		FileOutputStream fout = new FileOutputStream(file);
		fout.write(data);
		fout.close();
	}
}
