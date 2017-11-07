package gusev.max.tinkoff_homework.first;

import com.google.gson.annotations.SerializedName;

/**
 * Created by v on 06/11/2017.
 */

public class Developer {

    private long id;
    private String name;
    private String nick;

    @SerializedName("git_hub_link")//Чтобы получше смотрелось
    private String gitHubLink;
    private Language language;
    private String stream;

    public Developer(long id, String name, String nick, String gitHubLink, Language language, String stream) {
        this.id = id;
        this.name = name;
        this.nick = nick;
        this.gitHubLink = gitHubLink;
        this.language = language;
        this.stream = stream;
    }
}
