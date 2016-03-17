package noisyninja.com.marvelcharacterlibrary.models;

import java.util.ArrayList;

/**
 * Created by ir2pid on 12/03/16.
 */
public class SeriesList {

    int available; //(int, optional): The number of total available series in this list. Will always be greater than or equal to the "returned" value.,
    int returned; // (int, optional): The number of series returned in this collection (up to 20).,
    String collectionURI; // (string, optional): The path to the full list of series in this collection.,
    ArrayList<SeriesSummary >items; // (Array[SeriesSummary], optional): The list of returned series in this collection.

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getReturned() {
        return returned;
    }

    public void setReturned(int returned) {
        this.returned = returned;
    }

    public String getCollectionURI() {
        return collectionURI;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public ArrayList<SeriesSummary> getItems() {
        return items;
    }

    public void setItems(ArrayList<SeriesSummary> items) {
        this.items = items;
    }
}
