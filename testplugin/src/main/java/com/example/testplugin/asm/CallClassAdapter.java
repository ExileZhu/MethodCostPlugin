package com.example.testplugin.asm;

import com.example.testplugin.Cost;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * 类观察
 */
public class CallClassAdapter extends ClassVisitor implements Opcodes {


    public CallClassAdapter(ClassVisitor cv) {
        super(ASM6, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        //拦截指定的class
        System.out.println("className: " + name);
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        //拦截想要拦截的方法
        System.out.println("methodName:" + name);
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        return new AdviceAdapter(Opcodes.ASM6, mv, access, name, desc) {
            boolean intercept;
            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                if (Type.getDescriptor(Cost.class).equals(desc)) {
                    intercept = true;
                }
                return super.visitAnnotation(desc, visible);
            }

            @Override
            protected void onMethodEnter() {
                super.onMethodEnter();
                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitLdcInsn("onMethodEnter" + name);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            }

            @Override
            protected void onMethodExit(int opcode) {
                super.onMethodExit(opcode);
                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitLdcInsn("onMethodExit" + name);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            }
        };
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
