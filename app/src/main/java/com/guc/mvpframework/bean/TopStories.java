package com.guc.mvpframework.bean;

/**
 * Created by guc on 2018/9/5.
 * 描述：
 */
public class TopStories {
    private String ga_prefix;
    private String id;
    private String multipic;
    private String title;
    private String type;
    private String image;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public String getId() {
        return id;
    }

    public String getMultipic() {
        return multipic;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "TopStories{" +
                "ga_prefix='" + ga_prefix + '\'' +
                ", id='" + id + '\'' +
                ", multipic='" + multipic + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
