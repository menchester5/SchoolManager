package com.politechnika.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: Łukasz
 * Date: 19.11.13
 * Time: 22:56
 * To change this template use File | Settings | File Templates.
 */
@Entity("Payment")
@XmlRootElement
public class CoursePayment extends Payment {
    @Reference
    private Course subject;


    public Course getSubject() {
        return subject;
    }

    public void setSubject(Course subject) {
        this.subject = subject;

    }
}
