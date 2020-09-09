import java.util.ArrayList;
/**
 * Copy from SecondRatings. Made some modification.
 * Remove the private variable myMovie and all methods about it. like getID, getTitle, getSize.
 * Using MovieDatabase class. Modify the "getAverageRatings" method.
 * @author BeanLan
 *
 */
public class ThirdRatings {
    private ArrayList<EfficientRater> myRaters;
    
    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }
    
    /**
     * Call the FirstRatings
     * @param string
     */
	public ThirdRatings(String string) {
		// TODO Auto-generated constructor stub
		FirstRatings firstRatings = new FirstRatings();
		myRaters = firstRatings.loadRaters(string);
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
	 * Get all the movies average ratings.
	 * @param minimalRaters
	 * @return
	 */
	public ArrayList<Rating> getAverageRatings(int minimalRaters, Filter filterCriteria) {
		ArrayList<Rating> ratingsForAll = new ArrayList<Rating>();
		ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
		for(String m: movies) {
			Rating ave = new Rating(m, getAverageByID(m, minimalRaters));
			if(ave.getValue()>0)
				ratingsForAll.add(ave);
		}
		return ratingsForAll;
	}
    
}
