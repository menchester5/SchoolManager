/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.politechnika.student.resource;

import com.politechnika.model.Student;
import com.politechnika.model.User;
import com.politechnika.security.MongoDBRealm;
import com.politechnika.student.dao.StudentDAO;
import com.politechnika.user.resource.UserResource;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.mongodb.morphia.query.Query;

import java.util.List;

/**
 *
 * @author l.lis
 */
@Path("/students/")
public class StudentResource {
        private static final Logger LOGGER = Logger.getLogger(UserResource.class);
    @EJB
    private StudentDAO studentDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> getStudents() {
        Query<Student> query = studentDAO.createQuery().filter("role", MongoDBRealm.STUDENT_ROLE);
        return studentDAO.find(query).asList();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Student student) {
        student = MongoDBRealm.createStudentAuthorization(student);
        studentDAO.save(student);
        LOGGER.debug(student);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(Student student) {
        student = linkNotUpdatedData(student);
        if (student != null) {
            studentDAO.save(student);
        }
    }

    private Student linkNotUpdatedData(Student student) {
        LOGGER.debug(student.getUsername());
        User temp = studentDAO.findOne("username", student.getUsername());
        if (temp != null) {
            LOGGER.debug(temp);
            student.setPassword(temp.getPassword());
            student.setSalt(temp.getSalt());
            student.setUserID(temp.getUserID());
            return student;
        }
        return null;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Student findByUsername(@PathParam("id") String id) {
        return studentDAO.findOne("userID", id);
    }
    
    
    
}
