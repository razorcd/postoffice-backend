package com.postbox.controler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.ObjectInputStream;
        import java.io.ObjectOutputStream;

/**
 * Binary dump of an object.
 */
//TODO: wip
public class JavaObjectSerializer {

    public static void write(HttpServletRequest request, HttpServletResponse response) {

        try {
            String a = new String("a");

            // write object to file
            FileOutputStream fos = new FileOutputStream("test1.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(a);
            oos.close();

            // read object from file
            FileInputStream fis = new FileInputStream("test1.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
//            HttpServletRequest result = (HttpServletRequest) ois.readObject();
            String result = (String) ois.readObject();
            ois.close();

            System.out.println("Result:" + result.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}