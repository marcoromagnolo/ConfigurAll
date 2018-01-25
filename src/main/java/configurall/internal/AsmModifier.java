package configurall.internal;

import configurall.ConfigProperties;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.CheckClassAdapter;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
public class AsmModifier {

    private final static Logger LOGGER = Logger.getLogger(AsmModifier.class.getName());
    private ConfigWriterVisitor writerVisitor;
    private byte[] bytes;
    private ByteArrayOutputStream string = new ByteArrayOutputStream();

    @Override
    public String toString() {
        return string.toString();
    }

    public AsmModifier(String className, ConfigProperties defaultProp) {
        String classPath = (className).replaceAll("\\.", "/") + ".class";
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(classPath);
        try {
            // Read Class
            ClassReader classReader = new ClassReader(inputStream);
            ConfigReaderVisitor readerVisitor = new ConfigReaderVisitor(defaultProp);
            classReader.accept(readerVisitor, 0);
            if (readerVisitor.isConfigAnnotated()) {
                String classDir = className.replace('.', '/');
                ClassWriter classWriter = new ClassWriter(0);

                // Print Class modified
                PrintWriter printWriter = new PrintWriter(string);
                TraceClassVisitor traceClassVisitor = new TraceClassVisitor(classWriter, printWriter);
                CheckClassAdapter cv = new CheckClassAdapter(traceClassVisitor);

                // Write Class
                writerVisitor = new ConfigWriterVisitor(classDir, cv,
                        readerVisitor.getFields(), readerVisitor.getStatics());
                classReader.accept(writerVisitor, 0);
                bytes = classWriter.toByteArray();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public byte[] getBytes() {
        return bytes;
    }
}
