package storyteller_f;

import storyteller_f.model.Redirect;
import storyteller_f.model.Site;
import storyteller_f.model.Translate;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Serv extends StoppableThread {
    private final Site site;
    private final ExecutorService executorService;
    private ServerSocket serverSocket;

    public Serv(Site site) {
        this.site = site;
        executorService = Executors.newCachedThreadPool();
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

    @Override
    public void task() {
        if (setServer()) return;
        while (true) {
            if (stopByNext) continue;
            try {
                Socket socket = serverSocket.accept();
                executorService.execute(new H(socket));
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private boolean translateWhether(String encodedPath, Translate translate) {
        return encodedPath.matches(translate.getOrigin());
    }

    private boolean redirectWhether(String encodedPath, Redirect redirect) {
//        System.out.println(encodedPath);
//        System.out.println(redirect);
        String include = redirect.getInclude();
        ArrayList<String> exclude = redirect.getExclude();
        for (String s : exclude) {
            if (encodedPath.matches(s)) {
                return false;
            }
        }
        return encodedPath.matches(include);
    }

    class H implements Runnable {
        Socket socket;

        public H(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            HTTPRequest httpRequest;
            HTTPResponse httpResponse = null;
            try {
                httpRequest = new HTTPRequest(socket);
                httpResponse = new HTTPResponse(socket);
                if (httpRequest.isEmptyRequest()) {
                    httpResponse.responseNotFound();
                    return;
                }
                String encodedPath = httpRequest.getEncodedPath();
                ArrayList<Redirect> redirects = site.getRedirects();
                for (Redirect redirect : redirects) {
                    if (stopByNext) return;
                    if (redirectWhether(encodedPath, redirect)) {
                        encodedPath = redirect.getTo();
//                        System.out.println("redirect to:" + redirect.getTo());
                        break;
                    }
                }
                ArrayList<Translate> translates = site.getTranslates();
                for (Translate translate : translates) {
                    if (stopByNext) return;
                    if (translateWhether(encodedPath, translate)) {
                        encodedPath = encodedPath.replace(translate.getOrigin(), translate.getCurrent());
                        break;
                    }
                }
                File file = new File(site.getDir(), encodedPath);
//                System.out.println(file.getAbsolutePath());
                if (stopByNext) return;
                if (file.exists()) {
//                    System.out.println("exits");
                    httpResponse.responseFile(file, FileUtility.getInstance().getMimeType(FileURIUtility.getExtensions(file)));
                } else {
                    System.out.println(file+" do not exits");
                    httpResponse.responseHTML(404, "Not Found", "404 Not Found");
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (socket.isConnected()) {
                    assert false;
                    httpResponse.responseStringMessage(e.getMessage());
                }
            }finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}