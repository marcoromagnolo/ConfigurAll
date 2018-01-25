package configurall.internal;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.Map;

/**
 * @author Marco Romagnolo
 */
public class ConfigWriterVisitor extends ClassVisitor {

    private final Map<String, TypeValue> fields;
    private final String className;
    private final Map<String, TypeValue> statics;
    private final boolean[] isStaticVisited = {false};
    private final String superClassName;

    public ConfigWriterVisitor(String className, ClassVisitor classVisitor, Map<String, TypeValue> fields, Map<String, TypeValue> statics) {
        super(Opcodes.ASM5, classVisitor);
        this.fields = fields;
        this.statics = statics;
        this.className = className;
        this.superClassName = className + "Super";
    }

    @Override
    public void visit(int opcodeJavaVersion, int opcodeAccessLevel, String className, String string, String superClass, String[] strings) {
        super.visit(opcodeJavaVersion, opcodeAccessLevel, superClassName, string, className, strings);
    }

    @Override
    public MethodVisitor visitMethod(int opcodeAccessLevel, String methodDesc, String returnDesc, String string, String[] strings) {
        MethodVisitor mv = super.visitMethod(opcodeAccessLevel, methodDesc, returnDesc, string, strings);
        if (returnDesc.equals("()V")) {
            if (methodDesc.equals("<init>")) {

                return new MethodVisitor(Opcodes.ASM5, mv) {

                    @Override
                    public void visitMaxs(int i, int i1) {
                        super.visitMaxs(i + 4, i1);
                    }

                    @Override
                    public void visitMethodInsn(int opcodeInvoke, String superClass, String methodDesc, String returnDesc, boolean b) {
                        super.visitMethodInsn(opcodeInvoke, className, methodDesc, returnDesc, b);
                        if (methodDesc.equals("<init>") && returnDesc.equals("()V")) {
                            for (Map.Entry<String, TypeValue> entry : fields.entrySet()) {
                                TypeValue typeValue = entry.getValue();
                                Type type = typeValue.getType();
                                Object value = typeValue.getValue();
                                String field = entry.getKey();
                                super.visitVarInsn(Opcodes.ALOAD, 0);
                                setField(mv, value, type);
                                super.visitFieldInsn(Opcodes.PUTFIELD, superClassName, field, type.getDescriptor());
                            }
                        }
                    }

                };
            } else if (methodDesc.equals("<clinit>")) {

                return new MethodVisitor(Opcodes.ASM5, mv) {

                    @Override
                    public void visitCode() {
                        for (Map.Entry<String, TypeValue> entry : statics.entrySet()) {
                            TypeValue typeValue = entry.getValue();
                            Type type = typeValue.getType();
                            Object value = typeValue.getValue();
                            String field = entry.getKey();
                            setField(mv, value, type);
                            super.visitFieldInsn(Opcodes.PUTSTATIC, className, field, type.getDescriptor());
                        }
                        isStaticVisited[0] = true;
                    }

                    @Override
                    public void visitMaxs(int i, int i1) {
                        super.visitMaxs(i + 4, i1);
                    }
                };

            }
        } else {
            return new MethodVisitor(Opcodes.ASM5, mv) {

                @Override
                public void visitFieldInsn(int opcode, String className, String fieldName, String fieldClass) {
                    super.visitFieldInsn(opcode, superClassName, fieldName, fieldClass);
                }

            };
        }
        return mv;
    }

    @Override
    public void visitEnd() {
        if (!isStaticVisited[0]) {
            MethodVisitor mv = cv.visitMethod(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);
            mv.visitCode();
            for (Map.Entry<String, TypeValue> entry : statics.entrySet()) {
                TypeValue typeValue = entry.getValue();
                Type type = typeValue.getType();
                Object value = typeValue.getValue();
                String field = entry.getKey();

                setField(mv, value, type);
                mv.visitFieldInsn(Opcodes.PUTSTATIC, className, field, type.getDescriptor());
            }
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(4, 1);
            mv.visitEnd();
        }
        super.visitEnd();
    }

    public void setField(MethodVisitor mv, Object value, Type type) {
        if (value == null) {
            mv.visitInsn(Opcodes.ACONST_NULL);
        } else if (type.equals(Type.getType(boolean.class))) {
            int opcode = ((boolean) value) ? Opcodes.ICONST_1 : Opcodes.ICONST_0;
            mv.visitInsn(opcode);
        } else if (type.equals(Type.getType(Boolean.class))) {
            int opcode = ((boolean) value) ? Opcodes.ICONST_1 : Opcodes.ICONST_0;
            mv.visitInsn(opcode);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
        } else if (type.equals(Type.getType(int.class))) {
            mv.visitLdcInsn(value);
        } else if (type.equals(Type.getType(Integer.class))) {
            mv.visitLdcInsn(value);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
        } else if (type.equals(Type.getType(byte.class))) {
            mv.visitIntInsn(Opcodes.BIPUSH, (byte) value);
        } else if (type.equals(Type.getType(Byte.class))) {
            mv.visitIntInsn(Opcodes.BIPUSH, (byte) value);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
        } else if (type.equals(Type.getType(char.class))) {
            mv.visitIntInsn(Opcodes.BIPUSH, (char) value);
        } else if (type.equals(Type.getType(Character.class))) {
            mv.visitIntInsn(Opcodes.BIPUSH, (char) value);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
        } else if (type.equals(Type.getType(long.class))) {
            mv.visitLdcInsn(value);
        } else if (type.equals(Type.getType(Long.class))) {
            mv.visitLdcInsn(value);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
        } else if (type.equals(Type.getType(float.class))) {
            mv.visitLdcInsn(value);
        } else if (type.equals(Type.getType(Float.class))) {
            mv.visitLdcInsn(value);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
        } else if (type.equals(Type.getType(double.class))) {
            mv.visitLdcInsn(value);
        } else if (type.equals(Type.getType(Double.class))) {
            mv.visitLdcInsn(value);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
        } else if (type.equals(Type.getType(String.class))) {
            mv.visitLdcInsn(value);
        } else {
            mv.visitTypeInsn(Opcodes.NEW, "java/lang/Object");
            mv.visitInsn(Opcodes.DUP);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        }
    }

}
