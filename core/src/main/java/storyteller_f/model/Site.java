package storyteller_f.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Site {
    ArrayList<Redirect> redirects;
    ArrayList<Translate> translates;
    private String addr;
    private int port;
    private String dir;
    private String name;
}
