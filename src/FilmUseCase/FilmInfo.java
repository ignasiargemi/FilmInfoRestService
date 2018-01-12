package FilmUseCase;

import Domain.Film;

import java.util.Collection;

public interface FilmInfo {

	/**
	 * Interface with the minimum functions required
	 */
	public void addFilm(Film film);
	public Collection<Film> listFilm();
	public Collection<Film> searchFilm(String searchFilm);

}