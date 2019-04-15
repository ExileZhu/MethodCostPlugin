package com.example.testplugin.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class TestClassVisitor extends ClassVisitor {
    public TestClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if (cv == null) {
            super.visit(version, access, name, signature, superName, interfaces);
            return;
        }
        cv.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

        System.out.println("method name:" + name);


        if (cv != null) {
            return cv.visitMethod(access, name, desc, signature, exceptions);
        }

        return super.visitMethod(access, name, desc, signature, exceptions);
    }
}
