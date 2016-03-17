package noisyninja.com.marvelcharacterlibrary.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ir2pid on 12/03/16.
 */
public class Character {

    int id; //(int, optional): The unique ID of the character resource.,
    String name; // (string, optional): The name of the character.,
    String description; // (string, optional): A short bio or description of the character.,
    String modified; // (Date, optional): The date the resource was most recently modified.,
    String resourceURI; // (string, optional): The canonical URL identifier for this resource.,
    ArrayList<Url> urls; // (Array[Url], optional): A set of public web site URLs for the resource.,
    Image thumbnail; // (Image, optional): The representative image for this character.,
    ComicList comics; // (ComicList, optional): A resource list containing comics which feature this character.,
    StoryList stories; // (StoryList, optional): A resource list of stories in which this character appears.,
    EventList events; // (EventList, optional): A resource list of events in which this character appears.,
    SeriesList series; // (SeriesList, optional): A resource list of series in which this character appears.

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public ArrayList<Url> getUrls() {
        return urls;
    }

    public void setUrls(ArrayList<Url> urls) {
        this.urls = urls;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ComicList getComics() {
        return comics;
    }

    public void setComics(ComicList comics) {
        this.comics = comics;
    }

    public StoryList getStories() {
        return stories;
    }

    public void setStories(StoryList stories) {
        this.stories = stories;
    }

    public EventList getEvents() {
        return events;
    }

    public void setEvents(EventList events) {
        this.events = events;
    }

    public SeriesList getSeries() {
        return series;
    }

    public void setSeries(SeriesList series) {
        this.series = series;
    }
}
