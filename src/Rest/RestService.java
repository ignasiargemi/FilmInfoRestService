package Rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import Domain.Film;
import Domain.FilmList;
import Repository.FilmDAO;

/**
 * RESTful class to attend the request
 */
@Path("/films")
public class RestService {
	
	private FilmDAO filmDAO = new FilmDAO();
	
	// This method is called if TEXT_PLAIN is request	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String showAllFilms() {
		FilmList list = new FilmList(filmDAO.getAllFilms());
		return list.toString();
	}

	// This method is called if HTML is request
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		FilmList list = new FilmList(filmDAO.getAllFilms());
		return "<html> " + "<title>" + "List of 50 first films" + "</title>"
		     + "<body><h1>List of 50 first films\n</h1>" + list.toString() +"</body>" + "</html> ";
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("film/{film}")
	public String showFilmsByTitle(@PathParam("film") String name) {
		FilmList list = new FilmList(filmDAO.getFilmByTitle(name));
		return list.toString();	
	}
	
	@POST
	@Path("addFilm")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newTodo(
			@FormParam("title") String title,
			@FormParam("year") int year,
			@FormParam("director") String director,
			@FormParam("duration") int duration,
			@FormParam("credits") String credits,
			@FormParam("review") String review,
			@Context HttpServletResponse servletResponse
	) throws IOException {
		
		Film film = new Film(title,year,director,duration,credits,review);
		filmDAO.addFilm(film);
		
		servletResponse.sendRedirect("../create_films.html");
	}
	

}
