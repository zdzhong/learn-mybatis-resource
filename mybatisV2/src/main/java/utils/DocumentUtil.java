package utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

public class DocumentUtil {

    public static Document createDocument(InputStream inputStream) {
        SAXReader reader = new SAXReader();
        try {
            return reader.read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

}
