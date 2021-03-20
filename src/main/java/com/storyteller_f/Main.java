package com.storyteller_f;

import com.storyteller_f.model.Config;
import com.storyteller_f.model.Redirect;
import com.storyteller_f.model.Site;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        InputStream resource = Main.class.getResourceAsStream("/config.yaml");
        Config config = new Yaml().loadAs(resource, Config.class);
        ArrayList<Site> sites = config.getSites();
        for (Site site : sites) {
            ArrayList<Redirect> redirects = site.getRedirects();
            if (redirects == null) {
                site.setRedirects(new ArrayList<>());
                redirects=site.getRedirects();
            }
            if (redirects.size() == 0) {
                redirects.add(new Redirect("/", "", "/index.html"));
            } else {
                int i;
                for (i = 0; i < redirects.size(); i++) {
                    if (redirects.get(i).getInclude().equals("/")) {
                        break;

                    }
                }
                if (i == redirects.size()) {
                    redirects.add(new Redirect("/", "", "/index.html"));
                }
            }
        }
        System.out.println(config);
        new Server().start(config);
    }
}
