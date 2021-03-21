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
}
