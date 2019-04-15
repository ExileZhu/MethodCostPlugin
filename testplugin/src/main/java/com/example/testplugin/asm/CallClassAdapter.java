package com.example.testplugin.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 类观察
 */
public class CallClassAdapter extends ClassVisitor implements Opcodes {
    boolean intercept;

    public CallClassAdapter(ClassVisitor cv) {
        super(ASM6, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        //拦截指定的class
        System.out.println("className: " + name);
        if (name.endsWith("MainActivity")) {
            intercept = true;
        }
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        //拦截想要拦截的方法
        System.out.println("methodName:" + name);
        if (intercept && "onCreate".equals(name)) {
            MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
            return new CallMethodAdapter(mv);
        }
        return super.visitMethod(access, name, desc, signature, exceptions);
    }

    /**
     * 方法观察
     */
    static class CallMethodAdapter extends MethodVisitor implements Opcodes {

        public CallMethodAdapter(MethodVisitor mv) {
            super(ASM6, mv);
        }

        @Override
        public void visitCode() {
            super.visitCode();
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            System.out.println("visitMethod: " + name);
            //过滤指定方法名称
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("CALL " + name);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitMethodInsn(opcode, owner, name, desc, itf);
//            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }
    }
}
