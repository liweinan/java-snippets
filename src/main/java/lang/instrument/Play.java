package lang.instrument;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.ASMifier;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by weli on 14/05/2017.
 */
public class Play {

    public static void main(String[] args) throws Exception {
        FileInputStream input = new FileInputStream("/Users/weli/projs/java-snippets/target/classes/lang/instrument/Test.class");

        ClassReader reader = new ClassReader(input);

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        ClassVisitor visitor = new ClassAdapter(writer);

        reader.accept(visitor, 0);

        FileOutputStream fos = new FileOutputStream("/tmp/Test.class");
        fos.write(writer.toByteArray());
    }
}
