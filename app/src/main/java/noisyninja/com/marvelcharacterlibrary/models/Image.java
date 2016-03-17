package noisyninja.com.marvelcharacterlibrary.models;

/**
 * Created by ir2pid on 12/03/16.
 */
public class Image {

    String path; //(string, optional): The directory path of to the image.,
    String extension; //(string, optional): The file extension for the image.

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
