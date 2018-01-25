package configurall.internal;

import org.objectweb.asm.Type;

/**
 * @author Marco Romagnolo
 */
public class TypeValue {

    private Type type;
    private Object value;
    private int access;

    public TypeValue(int access, Type type, Object value) {
        this.access = access;
        this.type = type;
        this.value = value;
    }

    public int getAccess() {
        return access;
    }

    public Type getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

}
