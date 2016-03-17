package noisyninja.com.marvelcharacterlibrary.models;

/**
 * Created by ir2pid on 12/03/16.
 */
public class SeriesSummary {

    String resourceURI; // (string, optional): The path to the individual series resource.,
    String name; //(string, optional): The canonical name of the series.

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

