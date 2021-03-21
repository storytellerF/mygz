package storyteller_f.model;

import java.util.ArrayList;

public class Redirect {
    private String include;
    private ArrayList<String> exclude;
    private String to;

    @Override
    public String toString() {
        return "Redirect{" +
                "include='" + include + '\'' +
                ", exclude=" + exclude +
                ", to='" + to + '\'' +
                '}';
    }

    public Redirect() {
    }

    public Redirect(String include, String exclude, String to) {
        this.include = include;
        this.exclude = new ArrayList<>();
        this.exclude.add(exclude);
        this.to = to;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public ArrayList<String> getExclude() {
        return exclude;
    }

    public void setExclude(ArrayList<String> exclude) {
        this.exclude = exclude;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
