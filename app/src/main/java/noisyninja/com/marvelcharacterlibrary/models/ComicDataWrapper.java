package noisyninja.com.marvelcharacterlibrary.models;

/**
 * Created by ir2pid on 17/03/16.
 */
public class ComicDataWrapper {
    int code; // (int, optional): The HTTP status code of the returned result.,
    String status;  //(string, optional): A string description of the call status.,
    String copyright; //(string, optional): The copyright notice for the returned result.,
    String attributionText; // (string, optional): The attribution notice for this result. Please display either this notice or the contents of the attributionHTML field on all screens which contain data from the Marvel Comics API.,
    String attributionHTML; // (string, optional): An HTML representation of the attribution notice for this result. Please display either this notice or the contents of the attributionText field on all screens which contain data from the Marvel Comics API.,
    ComicDataContainer data; // (ComicDataWrapper, optional): The results returned by the call.,
    String etag; // (string, optional): A digest value of the content returned by the call.

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public void setAttributionText(String attributionText) {
        this.attributionText = attributionText;
    }

    public String getAttributionHTML() {
        return attributionHTML;
    }

    public void setAttributionHTML(String attributionHTML) {
        this.attributionHTML = attributionHTML;
    }

    public ComicDataContainer getData() {
        return data;
    }

    public void setData(ComicDataContainer data) {
        this.data = data;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }
}
