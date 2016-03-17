package noisyninja.com.marvelcharacterlibrary.models;

import java.util.ArrayList;

/**
 * Created by ir2pid on 12/03/16.
 */
public class CharacterDataContainer {

    int offset; //(int, optional): The requested offset (number of skipped results) of the call.,
    int limit; // (int, optional): The requested result limit.,
    int total; // (int, optional): The total number of resources available given the current filter set.,
    int count; // (int, optional): The total number of results returned by this call.,
    ArrayList<Character> results; //(Array[Character], optional): The list of characters returned by the call.

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<Character> getResults() {
        return results;
    }

    public void setResults(ArrayList<Character> results) {
        this.results = results;
    }
}
