package storyteller_f;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class HTTPResponse {
    private final Socket socket;

    public HTTPResponse(Socket socket) {
        this.socket = socket;
    }

    public static String getHttpContent(String message) {
        return "<!doctype html>\r\n" +
                "<html\r\n>" +
                "<head>\r\n" +
                "</head>\r\n" +
                "<body>\r\n" +
                message +
                "</body>\r\n" +
                "</html>\r\n";
    }

    public void responseNotFound() {
        try {
            socket.getOutputStream().write(("HTTP/1.1 404 Not Found\r\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void responseFile(File file, String mimeType) {
        String header = "HTTP/1.1 200 OK\r\n" +
                "Content-type:" + mimeType + "\r\n" +
                "Content-length:" + file.length() + "\r\n" +
                "Last-Modified:" + new Date(file.lastModified()) + "\r\n" +
                "Connection:keep-alive\r\n" +
                "\r\n";
        byte[] bytes = new byte[1024];
        int len;
        InputStream inputStream1 = null;
        try {
            inputStream1 = new FileInputStream(file);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(header.getBytes(StandardCharsets.UTF_8));
            while ((len = inputStream1.read(bytes)) != -1) {
                outputStream.write(bytes,0,len);
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException io) {
            responseStringMessage(io.getMessage());
        } finally {
            try {
                if (inputStream1 != null) {
                    inputStream1.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void responseDownloadFile(File file) {
        String header = "HTTP/1.1 200 OK\r\n" +
                "Content-type:application/octet-stream\r\n" +
                "Content-Disposition:attachment;filename=" + file.getName() + "\r\n" +
                "Content-length:" + file.length() + "\r\n" +
                "Last-Modified:" + new Date(file.lastModified()) + "\r\n" +
                "Connection:keep-alive\r\n" +
                "\r\n";
        BufferedInputStream inputStream = null;
        try {
            socket.setTcpNoDelay(false);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(header.getBytes());
            inputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] bytes = new byte[65535];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseStringMessage(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void responseHTML(int status, String message, String html) {
        try {
            socket.getOutputStream().write(("HTTP/1.1 " + status + " " + message + "\r\n" +
                    "Date: " + new Date().toString() + "\r\n" +
                    "Content-Type: text/html; charset=UTF-8\r\n" +
                    "Content-Length:" + html.getBytes().length + "\r\n" +
                    "\r\n" +
                    html).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void responseStringMessage(String message) {
        String httpContent = getHttpContent(message);
        responseHTML(200, "OK", httpContent);
    }
}
