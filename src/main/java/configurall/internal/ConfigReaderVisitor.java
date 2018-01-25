package configurall.internal;

import configurall.Config;
import configurall.ConfigProperties;
import org.objectweb.asm.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marco Romagnolo
 */
public class ConfigReaderVisitor extends ClassVisitor {

    private final Map<String, TypeValue> fields = new HashMap<>();
    private final Map<String, TypeValue> statics = new HashMap<>();
    private final ConfigProperties defaultProp;
    private boolean configAnnotated;

    public ConfigReaderVisitor(ConfigProperties defaultProp) {
        super(Opcodes.ASM5);
        this.defaultProp = defaultProp;
    }

    @Override
    public FieldVisitor visitField(final int access, final String name, final String desc, String signature, Object value) {
        FieldVisitor fieldVisitor = super.visitField(access, name, desc, signature, value);
        return new FieldVisitor(Opcodes.ASM5, fieldVisitor) {

            private boolean isStatic(int access) {
                return (access ^ Opcodes.ACC_STATIC) == Opcodes.ACC_PUBLIC
                        || (access - Opcodes.ACC_STATIC) == Opcodes.ACC_PROTECTED
                        || (access - Opcodes.ACC_STATIC) == Opcodes.ACC_PRIVATE
                        || (access - Opcodes.ACC_STATIC) == Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL
                        || (access - Opcodes.ACC_STATIC) == Opcodes.ACC_PROTECTED + Opcodes.ACC_FINAL
                        || (access - Opcodes.ACC_STATIC) == Opcodes.ACC_PRIVATE + Opcodes.ACC_FINAL
                        || (access - Opcodes.ACC_STATIC) == Opcodes.ACC_PUBLIC + Opcodes.ACC_VOLATILE
                        || (access - Opcodes.ACC_STATIC) == Opcodes.ACC_PROTECTED + Opcodes.ACC_VOLATILE
                        || (access - Opcodes.ACC_STATIC) == Opcodes.ACC_PRIVATE + Opcodes.ACC_VOLATILE
                        || (access - Opcodes.ACC_STATIC) == Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_TRANSIENT
                        || (access - Opcodes.ACC_STATIC) == Opcodes.ACC_PROTECTED + Opcodes.ACC_FINAL + Opcodes.ACC_TRANSIENT
                        || (access - Opcodes.ACC_STATIC) == Opcodes.ACC_PRIVATE + Opcodes.ACC_FINAL + Opcodes.ACC_TRANSIENT
                        || (access - Opcodes.ACC_STATIC) == Opcodes.ACC_PUBLIC + Opcodes.ACC_VOLATILE + Opcodes.ACC_TRANSIENT
                        || (access - Opcodes.ACC_STATIC) == Opcodes.ACC_PROTECTED + Opcodes.ACC_VOLATILE + Opcodes.ACC_TRANSIENT
                        || (access - Opcodes.ACC_STATIC) == Opcodes.ACC_PRIVATE + Opcodes.ACC_VOLATILE + Opcodes.ACC_TRANSIENT;
            }

            @Override
            public AnnotationVisitor visitAnnotation(String s, boolean b) {
                String annotation = Type.getType("Lconfigurall/Config;").getClassName();
                AnnotationVisitor annotationVisitor = super.visitAnnotation(s, b);
                if (annotation.equals(Config.class.getName())) {
                    configAnnotated = true;
                    return new AnnotationVisitor(Opcodes.ASM5, annotationVisitor) {
                        @Override
                        public void visit(String annotationProperty, Object annotationValue) {
                            Type type = Type.getType(desc);
                            String className = type.getClassName();
                            Object value;
                            if (className.equals(Integer.class.getName()) || className.equals(int.class.getName())) {
                                value = defaultProp.getAsInteger(annotationValue.toString());
                            } else if (className.equals(Long.class.getName()) || className.equals(long.class.getName())) {
                                value = defaultProp.getAsLong(annotationValue.toString());
                            } else if (className.equals(Float.class.getName()) || className.equals(float.class.getName())) {
                                value = defaultProp.getAsFloat(annotationValue.toString());
                            } else if (className.equals(Double.class.getName()) || className.equals(double.class.getName())) {
                                value = defaultProp.getAsDouble(annotationValue.toString());
                            } else if (className.equals(Boolean.class.getName()) || className.equals(boolean.class.getName())) {
                                value = defaultProp.getAsBoolean(annotationValue.toString());
                            } else if (className.equals(Byte.class.getName()) || className.equals(byte.class.getName())) {
                                value = defaultProp.getAsByte(annotationValue.toString());
                            } else if (className.equals(Character.class.getName()) || className.equals(char.class.getName())) {
                                value = defaultProp.getAsChar(annotationValue.toString());
                            } else {
                                value = defaultProp.get(annotationValue.toString());
                            }
                            if (isStatic(access)) {
                                statics.put(name, new TypeValue(access, type, value));
                            } else {
                                fields.put(name, new TypeValue(access, type, value));
                            }
                            super.visit(annotationProperty, annotationValue);
                        }
                    };
                }
                return annotationVisitor;
            }
        };
    }

    public Map<String, TypeValue> getFields() {
        return fields;
    }

    public Map<String, TypeValue> getStatics() {
        return statics;
    }

    public boolean isConfigAnnotated() {
        return configAnnotated;
    }
}
