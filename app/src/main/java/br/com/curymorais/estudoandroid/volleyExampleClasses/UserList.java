package br.com.curymorais.estudoandroid.volleyExampleClasses;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class UserList implements Serializable {

    @SerializedName("page")
    public Integer page;
    @SerializedName("per_page")
    public Integer perPage;
    @SerializedName("total")
    public Integer total;
    @SerializedName("total_pages")
    public Integer totalPages;
    @SerializedName("data")
    public List<UserDataList> userDataList;

    public class UserDataList implements Serializable {

        @SerializedName("id")
        public Integer id;
        @SerializedName("first_name")
        public String first_name;
        @SerializedName("last_name")
        public String last_name;
        @SerializedName("avatar")
        public String avatar;

    }
}