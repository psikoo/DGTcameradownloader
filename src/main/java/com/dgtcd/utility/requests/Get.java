package com.dgtcd.utility.requests;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Get {
    public static void getImgFromURL(String uriString, String destinationFile) throws IOException, URISyntaxException { //basic implementation for GET request that saves to the given file path
        URI uri = new URI(uriString);
        URL url = uri.toURL();
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }
}
