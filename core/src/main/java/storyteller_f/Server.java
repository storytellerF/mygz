package storyteller_f;


import storyteller_f.model.Config;
import storyteller_f.model.Site;

import java.io.File;
import java.util.ArrayList;

public class Server {
    public void start(Config path) {
        ArrayList<Site> sites = path.getSites();
        for (Site site : sites) {
            if (new File(site.getDir()).exists()) {
                Serv serv = new Serv(site);
                serv.start();
            }else {
                System.out.println(site.getName()+" 目录"+site.getDir()+"不存在");
            }
        }
    }
}
