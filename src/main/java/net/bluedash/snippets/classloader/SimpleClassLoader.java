package net.bluedash.snippets.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SimpleClassLoader extends ClassLoader {
	String[] dirs;

	public SimpleClassLoader(String path) {
		dirs = path.split(System.getProperty("path.separator"));
		String[] _dirs = dirs.clone();
		for (String dir : _dirs) {
			extendClasspath(dir);
		}
	}

	public void extendClasspath(String path) {
		String[] segments = path.split("/");
		String[] exDirs = new String[segments.length];
		for (int i = 0; i < (segments.length); i++) {
			exDirs[i] = popd(segments, i);
		}

		String[] newDirs = new String[dirs.length + exDirs.length];
		System.arraycopy(dirs, 0, newDirs, 0, dirs.length);
		System.arraycopy(exDirs, 0, newDirs, dirs.length, exDirs.length);
		dirs = newDirs;
	}

	private String popd(String[] pathSegments, int level) {
		StringBuffer path = new StringBuffer();
		for (int i = 0; i < level; i++) {
			path.append(pathSegments[i]).append("/");
		}
		return path.toString();
	}

	public synchronized Class findClass(String name)
			throws ClassNotFoundException {

		for (String dir : dirs) {
			byte[] buf = getClassData(dir, name);
			if (buf != null)
				return defineClass(name, buf, 0, buf.length);
		}
		throw new ClassNotFoundException();
	}

	protected byte[] getClassData(String directory, String name) {
		String[] tokens = name.split("\\.");
		String classFile = directory + "/" + tokens[tokens.length - 1]
				+ ".class";
		File f = (new File(classFile));
		int classSize = (new Long(f.length())).intValue();
		byte[] buf = new byte[classSize];
		try {
			FileInputStream filein = new FileInputStream(classFile);
			classSize = filein.read(buf);
			filein.close();
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return buf;
	}
}