package group.uchain.project.test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author project
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".")+1)+".class";
                    InputStream inputStream = getClass().getResourceAsStream(fileName);
                    if (inputStream == null){
                        return super.loadClass(name);
                    }
                    byte[] bytes = new byte[inputStream.available()];
                    inputStream.read(bytes);
                    return defineClass(name,bytes,0,bytes.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };
        Object obj=classLoader.loadClass("group.uchain.project.test.ClassLoaderTest").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof ClassLoaderTest);
    }
}
