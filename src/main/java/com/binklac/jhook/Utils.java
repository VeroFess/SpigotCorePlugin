package com.binklac.jhook;

import com.google.common.io.ByteStreams;
import com.nqzero.permit.Permit;
import net.minecraft.server.players.PlayerList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

public class Utils {
    public static String GetJHookJarPath() {
        String RawPath = JHook.class.getProtectionDomain().getCodeSource().getLocation().toString();

        int endIndex = RawPath.lastIndexOf(".jar") + 4;

        if(System.getProperty("os.name").toLowerCase().contains("linux")){
            return (new File(URLDecoder.decode(RawPath.substring(RawPath.indexOf(':'), endIndex)))).getAbsolutePath();
        } else {
            return (new File(URLDecoder.decode(RawPath.substring(RawPath.indexOf(':') + 1, endIndex)))).getAbsolutePath();
        }
    }

    public static Boolean IsJvmAfter8() {
        try {
            String version = System.getProperty("java.version");

            if(version.startsWith("1.")) {
                version = version.substring(2, 3);
            } else {
                int dot = version.indexOf(".");
                if(dot != -1) {
                    version = version.substring(0, dot);
                }
            }

            return Integer.parseInt(version) > 8;
        } catch (Exception e) {
            return false;
        }
    }

    public static InputStream getResourceAsStream(ClassLoader loader, String name) {
        return loader.getResourceAsStream(name);
    }

    public static InputStream getClassAsStream(ClassLoader loader, String name) {
        return getResourceAsStream(loader, name.replace('.', '/') + ".class");
    }

    public static byte[] getClassAsByteArray(ClassLoader loader, String name) throws IOException {
        try (InputStream is = getClassAsStream(loader, name)) {
            if (is == null) throw new FileNotFoundException("Class not found: " + name);
            return ByteStreams.toByteArray(is);
        }
    }

    public static void addSelfToUrlClassLoaderUrls(ClassLoader classLoader) throws NoSuchMethodException, MalformedURLException, InvocationTargetException, IllegalAccessException {
        Method addUrlMethod = classLoader.getClass().getDeclaredMethod("addURL", URL.class);
        Permit.setAccessible(addUrlMethod);
        addUrlMethod.invoke(PlayerList.class.getClassLoader(), (new File(Utils.GetJHookJarPath())).toURI().toURL());
    }
}
