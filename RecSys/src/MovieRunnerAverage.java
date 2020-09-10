import java.util.*;
/**
 * Create SecondRatings object to get the rank of movies by average
 * rating and get specified movie average rating.
 * @author BeanLan
 * @version 1.0 (20190926)
 */
public class MovieRunnerAverage {
	public void printAveragetRatings() {
		int minimalRaters = 60; 
		SecondRatings secRatings = new SecondRatings("src/data/ratedmoviesfull.csv", "src/data/ratings.csv");
		System.out.println("Total movies: "+secRatings.getMovieSize()+" , total raters: "+secRatings.getRaterSize());
		
		ArrayList<Rating> ratingsForAll = secRatings.getAverageRatings(minimalRaters);
		Collections.sort(ratingsForAll); 
		System.out.println("There are "+ratingsForAll.size()+" movies with more than "+minimalRaters+" raters");
		for(Rating r: ratingsForAll)
			if(r.getValue() > 0)
				System.out.println(r.getValue()+"\t"+secRatings.getTitle(r.getItem()));
	}
	
	public void getAverageRatingOneMovie(String movie_title) {
		SecondRatings secRatings = new SecondRatings("src/data/ratedmoviesfull.csv", "src/data/ratings.csv");
		String id = secRatings.getID(movie_title);
		ArrayList<Rating> ratingsForAll = secRatings.getAverageRatings(1);
		for(Rating r: ratingsForAll) {
			if(r.getItem().equals(id)) {
				System.out.println("rating of the Movie \""+movie_title+"\" is "+r.getValue());
				break;
			}
		}
	}
	public static void main(String args[]) {
		MovieRunnerAverage test = new MovieRunnerAverage();
		test.printAveragetRatings();
		test.getAverageRatingOneMovie("Vacation");
	}
}
