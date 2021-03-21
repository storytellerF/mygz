package storyteller_f;

import org.yaml.snakeyaml.Yaml;
import storyteller_f.model.Config;
import storyteller_f.model.Redirect;
import storyteller_f.model.Site;

import java.io.InputStream;
import java.util.ArrayList;

public class Start {

    public void config(InputStream resource) {
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
