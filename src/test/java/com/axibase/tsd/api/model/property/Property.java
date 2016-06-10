package com.axibase.tsd.api.model.property;

import com.axibase.tsd.api.Util;
import com.axibase.tsd.api.model.Model;
import com.axibase.tsd.api.registry.PropertyRegistry;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitry Korchagin.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Property extends Model {
    private String type;
    private String entity;
    private Map<String, String> key;
    private Map<String, String> tags;
    private Date date;

    public Property() {
    }

    public Property(String type, String entity) {
        if(type != null)
            PropertyRegistry.getInstance().registerType(type);
        if(entity != null)
            PropertyRegistry.getInstance().registerEntity(entity);
        this.type = type;
        this.entity = entity;
    }

    public void addTag(String tagName, String tagValue) {
        if(tags == null) {
            tags = new HashMap<>();
        }
        tags.put(tagName, tagValue);
    }

    public void addKey(String keyName, String keyValue) {
        if(key == null) {
            key = new HashMap<>();
        }
        key.put(keyName, keyValue);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Map<String, String> getKey() {
        return key;
    }

    public void setKey(Map<String, String> key) {
        this.key = key;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public String getDate() {
        if(date == null) {
            return null;
        }
        return Util.ISOFormat(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(Long millis) {
        this.date = new Date(millis);
    }

    @Override
    public String toString() {
        return "{" +
                "type='" + getType() + '\'' +
                ", entity='" + getEntity() + '\'' +
                ", key=" + getKey() +
                ", tags=" + getTags() +
                ", date=" + getDate() +
                '}';
    }
}