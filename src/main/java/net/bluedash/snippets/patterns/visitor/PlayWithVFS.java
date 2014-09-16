package net.bluedash.snippets.patterns.visitor;

import org.jboss.vfs.VFS;
import org.jboss.vfs.VFSUtils;
import org.jboss.vfs.VirtualFile;
import org.jboss.vfs.VirtualFileAssembly;
import org.jboss.vfs.util.FilterVirtualFileVisitor;
import org.jboss.vfs.util.SuffixMatchFilter;

import java.io.Closeable;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class PlayWithVFS {
    public static void main(String[] args) throws Exception {
        System.out.println("---vfs---");
        VirtualFile dir = org.jboss.vfs.VFS.getChild("/Users/weinanli/projs/java-snippets/target/classes/");
        System.out.println(dir.getName());
        for (VirtualFile file : dir.getChildren()) {
            System.out.format("%s -> %s\n", file.getName(), file.getLastModified());
        }

        // using the vfs filter
        FilterVirtualFileVisitor visitor = new FilterVirtualFileVisitor(new SuffixMatchFilter("json"));
        dir.visit(visitor);
        System.out.println(visitor.getMatched());
        VirtualFile jsonFile = visitor.getMatched().get(0);

        // deal with package
        VirtualFile war = VFS.getChild("/tmp/test.war");
        System.out.println(war);
        VirtualFileAssembly warAssembly = new VirtualFileAssembly();
        Closeable warHandle = VFS.mountAssembly(warAssembly, war);
        warAssembly.add("/", jsonFile);
        VFSUtils.safeClose(warHandle);

    }
}
