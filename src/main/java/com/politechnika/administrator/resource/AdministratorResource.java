package com.politechnika.administrator.resource;

import com.politechnika.administrator.dao.AdministatorDAO;
import com.politechnika.model.Administrator;
import com.politechnika.security.MongoDBRealm;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * Created by Łukasz on 31.12.13.
 */
@Path("/administrators/")
public class AdministratorResource {

    private static final Logger LOGGER = Logger.getLogger(AdministratorResource.class);
    @EJB
    private AdministatorDAO administatorDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Administrator> getAll() {
        return administatorDAO.find().asList();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Administrator administrator) {
        administrator = MongoDBRealm.createAdminAuthorization(administrator);
        administatorDAO.save(administrator);
        LOGGER.debug(administrator);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(Administrator administrator) {
        administrator = linkNotUpdatedData(administrator);
        if(administrator != null) {
            administatorDAO.save(administrator);
        }
    }

    private Administrator linkNotUpdatedData(Administrator administrator) {
        LOGGER.debug(administrator.getUsername());
        Administrator temp = administatorDAO.findOne("username", administrator.getUsername());
        if (temp != null) {
            LOGGER.debug(temp);
            administrator.setPassword(temp.getPassword());
            administrator.setSalt(temp.getSalt());
            administrator.setObjectId(temp.getObjectId());
            return administrator;
        }
        return null;
    }

    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Administrator findByUsername(@PathParam("username") String username) {
        return administatorDAO.findOne("username", username);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.TEXT_PLAIN)
    public Long countAdmins() {
        return administatorDAO.count();
    }
}