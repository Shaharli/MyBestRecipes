package com.avigezerit.mybestrecipes;

/**
 * Created by Shaharli on 19/07/2016.
 */
public class Recipe {

    private int sql_id;
    private String name;
    private String uri;
    private String desc;
    private int category;

    public Recipe(String name, String desc, String uri, int category) {
        this.name = name;
        this.desc = desc;
        this.uri = uri;
        this.category = category;
    }

    public int getSql_id() {
        return sql_id;
    }

    public void setSql_id(int sql_id) {
        this.sql_id = sql_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getUri() {
        return uri;
    }
}
