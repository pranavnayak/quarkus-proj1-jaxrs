package org.popins.dev;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.eclipse.microprofile.openapi.annotations.*;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/v2/movies")
@Tag(name="Movie Resource V2", description="Movie Rest API")
public class MovieUpdatedResource {

	List<Movie> movies = new ArrayList<>();
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(
			operationId="getMovies",
			summary = "Get Movies",
			description="Get List of Movies"
			)
	@APIResponse(
			responseCode="200",
			description="Operation Completed",
			content=@Content(mediaType = MediaType.APPLICATION_JSON))
	public Response getMovies() {
		return Response.ok(movies).build();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("results")
	@Operation(
			operationId="size",
			summary = "Get Count",
			description="Get Count of Movies present in the application"
			)
	@APIResponse(
			responseCode="200",
			description="Operation Completed",
			content=@Content(mediaType = MediaType.TEXT_PLAIN))
	public Response size() {
		return Response.ok(movies.size()).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(
			operationId="addMovie",
			summary = "Add Movie",
			description="Add a Movies into the application"
			)
	@APIResponse(
			responseCode="201",
			description="Operation Completed",
			content=@Content(mediaType = MediaType.APPLICATION_JSON))
	public Response addMovie(@RequestBody(
				description="Movie to be added",
				required=true,
				content=@Content(schema=@Schema(implementation=Movie.class)))
			Movie movieToBeAdded) {
		movies.add(movieToBeAdded);
		return Response.status(Response.Status.CREATED).entity(movies).build();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/{movieToBeUpdated}")
	@Operation(
			operationId="updateMovie",
			summary = "Update Movie",
			description="Update an existing Movies residing in the application"
			)
	@APIResponse(
			responseCode="200",
			description="Operation Completed",
			content=@Content(mediaType = MediaType.APPLICATION_JSON))
	public Response updateMovie(
			@Parameter(
					description="Movie Title",
					required=true,
					example="Matrix"
					)
			@PathParam("movieToBeUpdated") String movieToBeUpdated,
			@Parameter(
					description="Movie Id",
					required=true,
					example="5"
					)
			@PathParam("id") Long id) {
		movies = movies.parallelStream()
			  .map(movie -> {
				  if(movie.getId().equals(id))
					  movie.setTitle(movieToBeUpdated);
				  return movie;
			  })
			  .collect(Collectors.toList());
		return Response.ok(movies).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	@Operation(
			operationId="deleteMovie",
			summary = "Delete Movie",
			description="Delete an existing Movies residing in the application"
			)
	@APIResponse(
			responseCode="204",
			description="Operation Completed/Movie Deleted",
			content=@Content(mediaType = MediaType.APPLICATION_JSON))
	@APIResponse(
			responseCode="400",
			description="Operation Incomplete/Movie Not deleted",
			content=@Content(mediaType = MediaType.APPLICATION_JSON))
	public Response deleteMovie(@PathParam("id") Long id) {
		int startSize= movies.size();
		movies = movies.parallelStream()
			  .filter(movie -> ! movie.getId().equals(id))
			  .collect(Collectors.toList());
			  
		
		return startSize> movies.size() ? Response.noContent().build()
				      : Response.status(Status.BAD_REQUEST).build();
	}
	
	
}
