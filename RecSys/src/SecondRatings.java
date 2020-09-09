/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
/**
 * Create FirstRatings object to get two lists of movie and raters respectively.
 * Compute the average ratings of a movie, store all of the ratings in a list of <Rating>
 * @author BeanLan
 * @version 1.0 (20190926)
 */
public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<EfficientRater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    /**
     * Call the FirstRatings
     * @param string
     * @param string2
     */
	public SecondRatings(String string, String string2) {
		// TODO Auto-generated constructor stub
		FirstRatings firstRatings = new FirstRatings();
		myMovies = firstRatings.loadMovies(string);
		myRaters = firstRatings.loadRaters(string2);
	}
	
	public int getMovieSize() {
		return myMovies.size();
	}
	
	public int getRaterSize() {
		return myRaters.size();
	}
	
	/**
	 * Get the average ratings of a movie with movie_id
	 * @param movie_id
	 * @param minimalRaters
	 * @return
	 */
	private double getAverageByID(String movie_id, int minimalRaters) {
		double aveRating = 0.0;
		ArrayList<Double> allRatings = new ArrayList<Double>();
		for(EfficientRater r: myRaters) {
			double rating = r.getRating(movie_id);
			if(rating != -1)
				allRatings.add(rating);
		}
		if(allRatings.size() >= minimalRaters) {
			for(double num: allRatings) 
					aveRating = aveRating + num;
			aveRating = aveRating / allRatings.size();
		}
		return aveRating;
	}
	
	/**
	 * Get all the movies average raings.
	 * @param minimalRaters
	 * @return
	 */
	public ArrayList<Rating> getAverageRatings(int minimalRaters) {
		ArrayList<Rating> ratingsForAll = new ArrayList<Rating>();
		for(Movie m: myMovies) {
			Rating ave = new Rating(m.getID(), getAverageByID(m.getID(), minimalRaters));			
			if(ave.getValue()>0)
				ratingsForAll.add(ave);
		}
		return ratingsForAll;
	}
	
	/**
	 * Get the movie title by movie id
	 * @param movie_id
	 * @return
	 */
	public String getTitle(String movie_id) {
		for(Movie m: myMovies) {
			if(m.getID().equals(movie_id))
				return m.getTitle();
		}
		return "NO SUCH ID";
	}
	
	/**
	 * Get the movie id by movie title
	 */
	public String getID(String title) {
		for(Movie m: myMovies) {
			if(m.getTitle().equals(title))
				return m.getID();
		}
		return "NO SUCH TITLE";
	}
    
}
