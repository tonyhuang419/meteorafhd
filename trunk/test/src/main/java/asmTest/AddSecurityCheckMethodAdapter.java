package asmTest;

import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AddSecurityCheckMethodAdapter extends MethodAdapter {
	public AddSecurityCheckMethodAdapter(MethodVisitor mv) {
		super(mv);
	}

	public void visitCode() {
		visitMethodInsn(Opcodes.INVOKESTATIC, "target/classes/asmTest/SecurityChecker",
			"checkSecurity", "()V");
	}
}   