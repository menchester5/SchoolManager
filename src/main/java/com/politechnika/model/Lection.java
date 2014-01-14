/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.politechnika.model;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author l.lis
 */
@Entity
@XmlRootElement
public class Lection {

    private String lectionID = new ObjectId().toString();
    private String title;
    private String description;

    @Reference
    private Course course;
    @Reference
    private User lecturer;
    @Reference
    private List<Presence> presence;

    public String getLectionID() {
        return lectionID;
    }

    public void setLectionID(String lectionID) {
        this.lectionID = lectionID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getLecturer() {
        return lecturer;
    }

    public void setLecturer(User lecturer) {
        this.lecturer = lecturer;
    }

    public List<Presence> getPresence() {
        return presence;
    }

    public void setPresence(List<Presence> presence) {
        this.presence = presence;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}