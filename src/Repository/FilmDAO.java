package Repository;

import Domain.Film;

import java.sql.*;
import java.util.ArrayList;


public class FilmDAO {
	
	Film oneFilm = null;
	Connection conn = null;
    Statement stmt = null;

	public FilmDAO() {}

	
	private void openConnection(){
		// loading jdbc driver for mysql
		try{
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch(Exception e) { System.out.println(e); }

		// connecting to database
		try{
			// connection string for demos database, username demos, password demos
		    conn = DriverManager.getConnection
	        ("jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:3306/puigi?user=puigi&password=Blefrowy6");
		    stmt = conn.createStatement();
		} catch(SQLException se) { System.out.println(se); }	   
    }
	private void closeConnection(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Film getNextFilm(ResultSet rs){
    	Film thisFilm=null;
		try {
			thisFilm = new Film(
					rs.getInt("id"),
					rs.getString("title"),
					rs.getInt("year"),
					rs.getString("director"),
					rs.getInt("duration"),
					rs.getString("credits"),
					rs.getString("review")
			);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return thisFilm;		
	}
	
	
	
   public ArrayList<Film> getAllFilms(){
	   
		ArrayList<Film> allFilms = new ArrayList<Film>();
		openConnection();
		
	    // Create select statement and execute it
		try{
		    String selectSQL = "select * from films_proj limit 50";
		    ResultSet rs1 = stmt.executeQuery(selectSQL);
	    // Retrieve the results
		    while(rs1.next()){
		    	oneFilm = getNextFilm(rs1);
		    	allFilms.add(oneFilm);
		   }

		    stmt.close();
		    closeConnection();
		} catch(SQLException se) { System.out.println(se); }

	   return allFilms;
   }

   public Film getFilmByID(int id){
		oneFilm=null;
	    // Create select statement and execute it
		try{
			if (!IdExists(id)) {
				//closeConnection();
				return null;
			}
            openConnection();
		    String selectSQL = "select * from films_proj where id="+id;
		    ResultSet rs1 = stmt.executeQuery(selectSQL);
	    // Retrieve the results
		    while(rs1.next()){
		    	oneFilm = getNextFilm(rs1);
		    }

		    stmt.close();
		    closeConnection();
		} catch(SQLException se) { System.out.println(se); }

	   return oneFilm;
   }
   
   public ArrayList<Film> getFilmByTitle(String name){
		ArrayList<Film> allFilms = new ArrayList<Film>();
	    // Create select statement and execute it
		try{
			openConnection();
		    String selectSQL = "select * from films_proj where title like \"%" + name + "%\""; 
		    ResultSet rs1 = stmt.executeQuery(selectSQL);
		    // Retrieve the results
		    while(rs1.next()){
		    	oneFilm = getNextFilm(rs1);
		    	allFilms.add(oneFilm);
		    }

		    stmt.close();
		    closeConnection();
		} catch(SQLException se) { System.out.println(se); }

	   return allFilms;
  }

    private boolean IdExists(int id) {
        openConnection();
        int num = 0;
        try{
            String selectSQL = "select count(*) from films_proj where id="+id;
            ResultSet rs1 = stmt.executeQuery(selectSQL);
            // Retrieve the results
            while(rs1.next()){
                num = rs1.getInt(1);
            }

            stmt.close();
            closeConnection();
        } catch(SQLException se) {
            System.out.println(se);
            return false;
        }
        if (num > 0) return true;
        else return false;
    }

    public int addFilm(Film film) {
    	int max = -2;
 	   try {
 		   max = getMaxId();
 		   openConnection();
 		   String insertSQL = "INSERT INTO films_proj VALUES (" + (max+1) + ",\""
 				   + film.getTitle() + "\"," +film.getYear() + ",\"" +film.getDirector()
 				   + "\"," +film.getDuration()+ ",\"" +film.getCredits()+ "\",\"" +film.getReview()+ "\")";
 		   int rs1 = stmt.executeUpdate(insertSQL);
 		   if (rs1 == 1) System.out.println("Given ID is "+(max+1));
 		   stmt.close();
 		   closeConnection();
 	   } catch (SQLException se){System.out.println(se);}
 	   return (max+1);
    }

	public void deleteFilmByID(int id) {
		openConnection();
		try {


			String deleteSQL = "DELETE FROM films_proj where id = " + id;
			//System.out.println(deleteSQL);
			int rs1 = stmt.executeUpdate(deleteSQL);
			// Retrieve the results


			stmt.close();
			closeConnection();
		} catch (SQLException se){System.out.println(se);}
	}
   
   	public int getMaxId() {
		openConnection();
		int max = 0;
		// Create select statement and execute it
		try{
			String selectSQL = "select max(id) from films_proj";
			ResultSet rs1 = stmt.executeQuery(selectSQL);
			// Retrieve the results
			while(rs1.next()){
				max = rs1.getInt(1);
			}

			stmt.close();
			closeConnection();
			return max;
		} catch(SQLException se) { System.out.println(se); }

   		return 0;
	}
   
}
