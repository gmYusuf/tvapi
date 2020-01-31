package com.tvschedule.resource;
import javax.validation.ConstraintViolation;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.validation.Validator;
import com.tvschedule.dao.TVProgramDB;
import com.tvschedule.dao.TVProgramUtilities;
import com.tvschedule.domain.TVProgram;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

@Path("/tvprograms")
@Produces(MediaType.APPLICATION_JSON)
public class TVProgramsResource {
    private final Validator validator;
    private final TVProgramDB programList;

    public TVProgramsResource(Validator validator, TVProgramDB programList ) {
        this.validator = validator;
        this.programList = programList;
    }

    @GET
    public Response getTVList(){
        return Response.ok(programList.getTvProgramsAllWeek()).build();
    }
    @GET
    @Path("/date/{date}")   //date
    public Response getTVListWithDateFilter(@PathParam("date") String EnteredDate){
        Boolean isDateFormatOk = TVProgramUtilities.dateFormatValidation(EnteredDate);
        if(!isDateFormatOk)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(),
                    "Date Format Not Ok: Example Date Format: yyyy-mm-dd , ").build();

        ArrayList<TVProgram> tvProgramListByDate = programList.getTvProgramByDate(EnteredDate); //filter date
        if (tvProgramListByDate.size() != 0)
            return Response.ok(tvProgramListByDate).build();
        else
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(),
                        "That Day Not Exist In Schedule, " + Response.Status.NOT_FOUND.toString()).build();
    }
    @GET
    @Path("/name/{name}")   //program name
    public Response getTVListWithNameFilter(@PathParam("name") String name){
        ArrayList<TVProgram> tvProgramListByName =  programList.getTvProgramByName(name);// filter program name
        if (tvProgramListByName != null)
            return Response.ok(tvProgramListByName).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }
    /*
     * Create TV program
     */
    @POST
    public Response createTVProgram(TVProgram tvProgram) throws URISyntaxException {
        // Validation
        Set<ConstraintViolation<TVProgram>> violations = validator.validate(tvProgram);

        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<TVProgram> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(validationMessages).build();
        }
        Boolean isCreated = programList.createNewTVProgram(tvProgram);

        if (isCreated) {
            return Response.ok(tvProgram).build();// tv program added to list
        } else
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "NOT ADDED TO LIST").build();
     }
    /*
    * Update exist tv program
    * */
    @PUT
    public Response updateTVProgram(TVProgram tvProgram) throws URISyntaxException {
        // Validation
        Set<ConstraintViolation<TVProgram>> violations = validator.validate(tvProgram);
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<TVProgram> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(validationMessages).build();
        }
        Boolean isUpdated = programList.updateTVProgram(tvProgram);

        if (isUpdated) {
            return Response.ok(tvProgram).build();// tv program added to list
        } else
            return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "NOT UPDATED").build();
    }
    @DELETE
    @Path("/{id}")
    public Response deleteTVProgram(@PathParam("id") String  tvProgramID) throws URISyntaxException {
        Boolean isDeleted = programList.deleteTVProgram(tvProgramID);
        if (isDeleted) {
            return Response.status(Response.Status.OK.getStatusCode(), "DELETED").build();// tv program deleted from list
        } else
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(), "NOT DELETED").build();
    }
}
