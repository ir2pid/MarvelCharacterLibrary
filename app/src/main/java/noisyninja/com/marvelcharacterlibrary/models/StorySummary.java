package noisyninja.com.marvelcharacterlibrary.models;

/**
 * Created by ir2pid on 12/03/16.
 */
public class StorySummary {

    String resourceURI; //(string, optional): The path to the individual story resource.,
    String name; //(string, optional): The canonical name of the story.,
    String type; //(string, optional): The type of the story (interior or cover).

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
