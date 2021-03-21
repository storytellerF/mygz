package com.storyteller_f;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTTPRequest {
    private final String header;
    private final String encodedPath;

    public HTTPRequest(Socket socket) throws IOException {
        header = getRequest(socket);
        String requestType = getRequestType(header);
        String path = getRequestPath(header);
        encodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8.toString());
        long contentLength = getContentLength(header);
        String contentType = getContentType(header);
        boolean isMultipart = isMultipart(contentType);
//        System.out.println("com.storyteller_f.HTTPRequest: type:" + requestType + " path:" + path + " encodedPath:" + encodedPath + " length:" + contentLength + " type:" + contentType + " multi:" + isMultipart);
//        if (isMultipart) {
//            String[] boundaries = getBoundaries(contentType);
//        }
    }

    private String getRequestType(String header) {
        int endIndex = header.indexOf(" ");
        if (endIndex == -1) {
            System.out.println("getRequestType: header:" + header);
            System.out.println("getRequestType: header bytes:" + Arrays.toString(header.getBytes()));
            return null;
        }
        return header.substring(0, endIndex);
    }

    public String getEncodedPath() {
        return encodedPath;
    }

    public boolean isEmptyRequest() {
        return header.length() == 0;
    }

    private long getContentLength(String request) {
        Matcher matchContentLength = Pattern.compile("Content-Length: (.*)").matcher(request);
        boolean contentLengthFind = matchContentLength.find();
        if (contentLengthFind) {
            String group = matchContentLength.group(1);
            if (group == null) {
                return 0;
            }
            return Long.parseLong(group);
        }
        return -1;
    }

    private String getContentType(String request) {
        Matcher matchContentLength = Pattern.compile("Content-Type: (.*)").matcher(request);
        boolean contentLengthFind = matchContentLength.find();
        if (!contentLengthFind) {
            return null;
        }
        return matchContentLength.group(1);
    }

    private String getRequestPath(String request) {
        int first_space = request.indexOf(" ");
        int second_space = request.indexOf(" ", first_space + 2);
        if (second_space == -1) {
            return "";
        }
        return request.substring(first_space + 1, second_space);
    }

    private String getRequest(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        socket.setSoTimeout(5000);
        byte[] bytes = new byte[2048];
        byte[] stop = new byte[4];
        stop[0] = '\r';
        stop[1] = '\n';
        stop[2] = '\r';
        stop[3] = '\n';
        int stopIndex = 0;
        int index = 0;
        while (true) {
            byte temp = (byte) inputStream.read();
            if (temp == -1) {
                break;
            }
            if (stop[stopIndex] == temp) {
                stopIndex++;
                if (stopIndex == 4) {
                    break;
                }
            } else {
                stopIndex = 0;
            }
            bytes[index++] = temp;
        }
        return new String(bytes, 0, index);
    }

    private boolean isMultipart(String contentType) {
        if (contentType == null) {
            return false;
        }
        return contentType.indexOf("multipart/form-data;") == 0;
    }

}
