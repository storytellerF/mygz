package com.storyteller_f;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTTPRequest {
    private static final String TAG = "com.storyteller_f.HTTPRequest";
    private final Socket socket;
    private final String header;
    private final String path;
    private final String encodedPath;
    private final String contentType;
    private final String requestType;
    private final long contentLength;
    private String[] boundaries;

    public HTTPRequest(Socket socket) throws IOException {
        this.socket = socket;
        header = getRequest(socket);
        requestType = getRequestType(header);
        path = getRequestPath(header);
        encodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8.toString());
        contentLength = getContentLength(header);
        contentType = getContentType(header);
        boolean isMultipart = isMultipart(contentType);
//        Log.i(TAG, "com.storyteller_f.HTTPRequest: header:"+header);
        System.out.println("com.storyteller_f.HTTPRequest: type:" + requestType + " path:" + path + " encodedPath:" + encodedPath + " length:" + contentLength + " type:" + contentType + " multi:" + isMultipart);
        if (isMultipart) {
            boundaries = getBoundaries(contentType);
        }
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

    private String[] getBoundaries(String contentType) {
        Matcher matchContentLength = Pattern.compile("boundary=(.*)").matcher(contentType);
        boolean contentLengthFind = matchContentLength.find();
        String group = matchContentLength.group(1);
        if (!contentLengthFind || group == null) {
            return null;
        }
        return group.split(";");
    }

    private String getRequest(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        socket.setSoTimeout(5000);
        byte[] bytes = new byte[1024];
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

    public String getHeader() {
        return header;
    }

    public String getPath() {
        return path;
    }

    private String getContentType() {
        return contentType;
    }

    private long getContentLength() {
        return contentLength;
    }

    private boolean isMultipart(String contentType) {
        if (contentType == null) {
            return false;
        }
        return contentType.indexOf("multipart/form-data;") == 0;
    }

    private String getFileName(String second) {
        Matcher matchContentLength = Pattern.compile("filename=\"(.*)\"").matcher(second);
        boolean contentLengthFind = matchContentLength.find();
        if (!contentLengthFind) {
            return null;
        }
        return matchContentLength.group(1);
    }

    public boolean hasFile() {
        long contentLength = getContentLength();
        System.out.println("content contentLength:" + contentLength);
        if (contentLength == 0) {
            return false;
        }
        String contentType = getContentType();
        System.out.println("contentType:" + contentType);
        if (contentType == null) {
            return false;
        }
        boolean isMultipart = isMultipart(contentType);
        System.out.println("isMultipart:" + isMultipart);
        if (!isMultipart) {
            return false;
        }
        String[] boundaries = getBoundaries(contentType);
        return boundaries != null;
    }

    public void saveFile(File parent) throws IOException {
        InputStream inputStream = socket.getInputStream();
        for (String boundary : boundaries) {
            //读取第一行 f1orm data 的开始区间
            String first = getLine(inputStream);
            if (first == null) {
                break;
            } else {
                String should = "--" + boundary;
                if (!first.equals(should)) {
                    break;
                }
            }

            //读取第二行 获得file name
            String second = getLine(inputStream);
            String fileName = getFileName(second);
            if (fileName == null) {
                break;
            }
            System.out.println( "second:" + second + " filename:" + fileName);
            //读取第三行 获得类型
            String triple = getLine(inputStream);
            System.out.println("triple:" + triple);
            //是一个换行
            getLine(inputStream);
            System.out.println("run: space had got");
            //后面开始存储文件
            File file = new File(parent, fileName);
            String extensions = FileURIUtility.getExtensions(file);
            int capacity;
            if (isTextFile(extensions)) {
                capacity = 256;
            } else {
                capacity=10240;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            System.out.println("run: start while...");
            LineData lastLine = null;
            while (true) {
                LineData temp = getLineData(inputStream, capacity);
                if (temp == null) {//出现异常
                    break;
                }
                if (temp.string.contains(first)) {
                    //上一行(lastLine)是crlf，这样就没有输出这个内容就退出了
                    break;
                }
                if (lastLine != null) {
                    bufferedOutputStream.write(lastLine.bytes, 0, lastLine.length);
                }
                lastLine = temp;
            }
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            System.out.println("run: while stopped");
            getLine(inputStream);
            //一个空行
            getLine(inputStream);
            //上传的源文件的名称
            String source = getLine(inputStream);
            System.out.println("source:" + source);
            String end = getLine(inputStream);
            System.out.println("end:" + end);
            String anObject = "--" + boundary + "--";
            if (end == null || !end.equals(anObject)) {
                System.out.println("最后一行不一致:" + end + " boundary:" + boundary + " an:" + anObject);
            }
        }
        System.out.println("saveFile: method had stopped");
    }
    private static final ArrayList<String> ex = new ArrayList<>();

    static {
        String[] string = new String[]{
                "txt",
                "html",
                "js",
                "css",
                "c",
                "cpp",
                "py"
        };
        ex.addAll(Arrays.asList(string));
    }

    private boolean isTextFile(String extensions) {
        return ex.contains(extensions);
    }

    private String getLine(InputStream inputStream) {
        byte[] l = new byte[1024];
        int index = 0;
        int current;
        try {
            while ((current = inputStream.read()) != '\n') {
                l[index++] = (byte) current;
            }
            if (l[index - 1] == '\r') {
                index--;
            }
            return new String(l, 0, index);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private LineData getLineData(InputStream inputStream, int capacity) {
        byte[] bytes = new byte[capacity];
        int index = 0;
        try {
            while (true) {
                int current = inputStream.read();
                if (current == '\r') {
                    //检查下一个是否是\n
                    int next = inputStream.read();
                    bytes[index++] = (byte) current;
                    bytes[index++] = (byte) next;
                    if (next == '\n') {
                        //是\n，是一个crlf换行，退出循环
                        break;
                    } else if (next == -1) {
                        //因为是-1，所以退出while 不会添加换行，基本不会出现这种情况，可以根据自己需要抛出异常
                        index--;
                        break;
                    }  //current 是正常的一个\r

                } else if (current == -1) {//因为是-1，所以退出while 不会添加换行，基本不会出现这种情况，可以根据自己需要抛出异常
                    break;
                } else
                    bytes[index++] = (byte) current;
                if (index >= capacity - 1) {//不够下一次的\r\n了
                    break;
                }
            }
            return new LineData(bytes, index);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static class LineData {
        byte[] bytes;
        String string;
        int length;

        LineData(byte[] bytes, int length) {
            this.bytes = bytes;
            this.string = new String(bytes, 0, length);
            this.length = length;
        }
    }

}
