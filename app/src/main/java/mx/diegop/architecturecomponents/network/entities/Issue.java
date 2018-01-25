
package mx.diegop.architecturecomponents.network.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Issue {
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("repository_url")
    @Expose
    public String repositoryUrl;
    @SerializedName("labels_url")
    @Expose
    public String labelsUrl;
    @SerializedName("comments_url")
    @Expose
    public String commentsUrl;
    @SerializedName("events_url")
    @Expose
    public String eventsUrl;
    @SerializedName("html_url")
    @Expose
    public String htmlUrl;
    @PrimaryKey
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("number")
    @Expose
    public Integer number;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("locked")
    @Expose
    public Boolean locked;
    @SerializedName("comments")
    @Expose
    public Integer comments;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("author_association")
    @Expose
    public String authorAssociation;
    @SerializedName("body")
    @Expose
    public String body;
}
