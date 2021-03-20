package com.storyteller_f;

import com.storyteller_f.model.Config;
import com.storyteller_f.model.Redirect;
import com.storyteller_f.model.Site;
import com.storyteller_f.model.Translate;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {
    public void start(Config path) {
        ArrayList<Site> sites = path.getSites();
        for (Site site : sites) {
            Serv serv = new Serv(site);
            serv.start();
        }

    }

    static abstract class StoppableThread extends Thread {
        public boolean stopByNext = false;
        public boolean isRunning = false;

        @Override
        public void run() {
            isRunning = true;
            super.run();
            task();
            isRunning = false;
        }

        public abstract void task();
    }

    static class Serv extends StoppableThread {
        private final Site site;
        private ServerSocket serverSocket;

        public Serv(Site site) {
            this.site = site;
        }

        private boolean setServer() {
            try {
                serverSocket = new ServerSocket(site.getPort());
            } catch (IOException e) {
                e.printStackTrace();
                return true;
            }
            return false;
        }

        private void closeSocket(Socket socket) {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void task() {
            if (setServer()) return;
            while (true) {
                if (stopByNext) continue;
                ;
                Socket socket = null;
                HTTPRequest httpRequest;
                HTTPResponse httpResponse = null;
                try {
                    socket = serverSocket.accept();
                    httpRequest = new HTTPRequest(socket);
                    httpResponse = new HTTPResponse(socket);
                    if (httpRequest.isEmptyRequest()) {
                        httpResponse.responseNotFound();
                        continue;
                    }
                    String encodedPath = httpRequest.getEncodedPath();
                    ArrayList<Redirect> redirects = site.getRedirects();
                    for (Redirect redirect : redirects) {
                        if (redirectWeather(encodedPath, redirect)) {
                            encodedPath = redirect.getTo();
                            System.out.println("redirect to:" + redirect.getTo());
                            break;
                        }
                    }
                    ArrayList<Translate> translates = site.getTranslates();
                    for (Translate translate : translates) {
                        if (translateWeather(encodedPath, translate)) {
                            encodedPath = encodedPath.replace(translate.getOrigin(), translate.getCurrent());
                            break;
                        }
                    }
                    File file = new File(site.getDir(), encodedPath);
                    System.out.println(file.getAbsolutePath());
                    if (file.exists()) {
                        System.out.println("exits");
                        httpResponse.responseFile(file, FileUtility.getInstance().getMimeType(FileURIUtility.getExtensions(file)));
                    } else {
                        System.out.println("do not exits");
                        httpResponse.responseHTML(404,"Not Found","404 Not Found");
                    }

                } catch (SocketException e) {
                    e.printStackTrace();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    if (socket != null && httpResponse != null)
                        httpResponse.responseStringMessage("IOException:" + e.getMessage());
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    if (socket != null && httpResponse != null) {
                        httpResponse.responseStringMessage(e.getMessage() == null ? "错误֪" : e.getMessage());
                    }
                    return;
                } finally {
                    closeSocket(socket);
                }

            }
        }

        private boolean translateWeather(String encodedPath, Translate translate) {
            return encodedPath.matches(translate.getOrigin());
        }

        private boolean redirectWeather(String encodedPath, Redirect redirect) {
            System.out.println(encodedPath);
            System.out.println(redirect);
            String include = redirect.getInclude();
            ArrayList<String> exclude = redirect.getExclude();
            for (String s : exclude) {
                if (encodedPath.matches(s)) {
                    return false;
                }
            }
            return encodedPath.matches(include);
        }
    }
}
