package org.popins.dev;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

@Path("/movies")
public class MovieResource {

	List<String> movies = new ArrayList<String>();
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getMovies() {
		return Response.ok(movies).build();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("results")
	public Response size() {
		return Response.ok(movies.size()).build();
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("{movieToBeAdded}")
	public Response addMovie(@PathParam("movieToBeAdded") String movieToBeAdded) {
		movies.add(movieToBeAdded);
		return Response.ok(movies).build();
	}
	
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("{movieToBeUpdated}")
	public Response updateMovie(
			@PathParam("movieToBeUpdated") String movieToBeUpdated,
			@QueryParam("movie") String newMovieName) {
		movies=movies.parallelStream()
			  .map(movie -> {
				  if(movie.equals(movieToBeUpdated))
					  return newMovieName;
				  return movie;
			  })
			  .collect(Collectors.toList());
		return Response.ok(movies).build();
	}
	
	@DELETE
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("{movieToBeDeleted}")
	public Response deleteMovie(@PathParam("movieToBeDeleted") String movieToBeDeleted) {
		boolean remove = movies.remove(movieToBeDeleted);
		return remove ? Response.noContent().build()
				      : Response.status(Status.BAD_REQUEST).build();
	}
	
	
}
