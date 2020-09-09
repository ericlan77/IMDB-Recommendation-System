import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerSimilarRatings {
	public void printAveragetRatings() {
		int minimalRaters = 1;
		FourthRatings fthRatings = new FourthRatings("src/data/ratings_short.csv");
		MovieDatabase.initialize("ratedmovies_short.csv");
		System.out.println("Total movies: "+MovieDatabase.size()+" , total raters: "+fthRatings.getRaterSize());
		
		ArrayList<Rating> ratingsForAll = fthRatings.getAverageRatings(minimalRaters, new TrueFilter());
		Collections.sort(ratingsForAll); 
		
		System.out.println("find "+ratingsForAll.size()+" movies rated.");
		for(Rating r: ratingsForAll)
			System.out.println(r.getValue()+"\t"+MovieDatabase.getTitle(r.getItem()));
	}
	
	public void printAverageRatingsByYearAfterAndGenre() {
		int minimalRaters = 1;
		int yearAfter = 1980;
		String genres = "Romance";
		FourthRatings fthRatings = new FourthRatings("src/data/ratings_short.csv");
		MovieDatabase.initialize("ratedmovies_short.csv");
		System.out.println("Total movies: "+ MovieDatabase.size()+" , total raters: "+fthRatings.getRaterSize());
		
		// initiate the AllFilters
		AllFilters filters = new AllFilters();
		filters.addFilter(new YearAfterFilter(yearAfter));
		filters.addFilter(new GenreFilter(genres));
		ArrayList<Rating> ratingsForAll = fthRatings.getAverageRatings(minimalRaters, filters);
		Collections.sort(ratingsForAll); 
		
		System.out.println("find "+ratingsForAll.size()+" movies");
		for(Rating r: ratingsForAll) {
			System.out.println(r.getValue()+"\t"+MovieDatabase.getTitle(r.getItem()));
			System.out.print("Genres:"+"\t"+MovieDatabase.getGenres(r.getItem())+'\t');
			System.out.println("Year:"+"\t"+MovieDatabase.getYear(r.getItem()));
		}
	}
	
	public static void main(String args[]) {
		MovieRunnerSimilarRatings test = new MovieRunnerSimilarRatings();
		test.printAveragetRatings();
		//test.printAverageRatingsByYear();
		//test.printAverageRatingsByGenre();
		//test.printAverageRatingsByMinutes();
		//test.printAverageRatingsByDirectors();
		//test.printAverageRatingsByYearAfterAndGenre();
		//test.printAverageRatingsByYearAfterAndGenre();
		
	}
}
