package agent;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class SourceCodeTransformer implements ClassFileTransformer {
    private static final String TRANSFER_MONEY_METHOD = "transfer";

    private String classPathName;

    private String targetClassName;

    private ClassLoader targetClassLoader;

    public SourceCodeTransformer(String classPathName, String targetClassName, ClassLoader targetClassLoader) {
        this.classPathName = classPathName;
        this.targetClassName = targetClassName;
        this.targetClassLoader = targetClassLoader;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] byteCode = classfileBuffer;

        String finalTargetClassName = this.targetClassName.replaceAll("\\.", "/"); //replace . with /
        if (!className.equals(finalTargetClassName)) {
            return byteCode;
        }

        if (className.equals(finalTargetClassName) && loader.equals(targetClassLoader)) {
            System.out.println("[Agent] Transforming class Account");
            try {
                ClassPool cp = ClassPool.getDefault();
                cp.appendClassPath(classPathName);
                cp.importPackage("java.math");
                CtClass cc = cp.get(targetClassName);

                System.out.println("before get TRANSFER_MONEY_METHOD: " + TRANSFER_MONEY_METHOD);
                CtMethod m = cc.getDeclaredMethod(TRANSFER_MONEY_METHOD);
                System.out.println("m.getSignature():" + m.getSignature());
                m.setBody("{$2=new BigDecimal(10000);" +
                        "$0.balance = $0.balance.subtract($2);" +
                        "$1.balance = $1.balance.add($2);}");

                String outputFile = "C:\\tmp\\";
                System.out.println("before write to :" + outputFile);
                cc.writeFile(outputFile);
                System.out.println("write to :" + outputFile);

                byteCode = cc.toBytecode();
                cc.detach();
            } catch (Exception e) {
                System.out.println("Exception: " + e.getClass().getName() + " " + e.getMessage() + " ");
                e.printStackTrace();
            }
        }
        return byteCode;
    }
}