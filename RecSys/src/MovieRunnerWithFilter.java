import java.util.ArrayList;
import java.util.Collections;

/**
 * Create ThirdRatings object to get the rank of movies by average
 * rating and get specified movie average rating.
 * @author BeanLan
 * @version 1.0 (20190926)
 */
public class MovieRunnerWithFilter {
	public void printAveragetRatings() {
		int minimalRaters = 35;
		ThirdRatings trdRatings = new ThirdRatings("src/data/ratings.csv");
		MovieDatabase.initialize("ratedmoviesfull.csv");
		System.out.println("Total movies: "+ MovieDatabase.size()+" , total raters: "+trdRatings.getRaterSize());
		
		ArrayList<Rating> ratingsForAll = trdRatings.getAverageRatings(minimalRaters, new TrueFilter());
		Collections.sort(ratingsForAll); 
		
		System.out.println("find "+ratingsForAll.size()+" movies rated.");
		for(Rating r: ratingsForAll)
			System.out.println(r.getValue()+"\t"+ MovieDatabase.getTitle(r.getItem()));
	}
	
	public void printAverageRatingsByYear() {
		int minimalRaters = 20;
		int yearAfter = 2000;
		ThirdRatings trdRatings = new ThirdRatings("src/data/ratings.csv");
		MovieDatabase.initialize("ratedmoviesfull.csv");
		System.out.println("Total movies: "+ MovieDatabase.size()+" , total raters: "+trdRatings.getRaterSize());
		
		ArrayList<Rating> ratingsForAll = trdRatings.getAverageRatings(minimalRaters, new YearAfterFilter(yearAfter));
		Collections.sort(ratingsForAll); 
		
		System.out.println("find "+ratingsForAll.size()+" movies rated after "+yearAfter);
		for(Rating r: ratingsForAll)
			System.out.println(r.getValue()+"\t"+ MovieDatabase.getYear(r.getItem())+"\t"+ MovieDatabase.getTitle(r.getItem()));
	}
	
	public void printAverageRatingsByGenre() {
		int minimalRaters = 20;
		String genre = "Comedy";
		ThirdRatings trdRatings = new ThirdRatings("src/data/ratings.csv");
		MovieDatabase.initialize("ratedmoviesfull.csv");
		System.out.println("Total movies: "+ MovieDatabase.size()+" , total raters: "+trdRatings.getRaterSize());
		
		ArrayList<Rating> ratingsForAll = trdRatings.getAverageRatings(minimalRaters, new GenreFilter(genre));
		Collections.sort(ratingsForAll); 
		
		System.out.println("find "+ratingsForAll.size()+" movies rated with "+genre+" genre");
		for(Rating r: ratingsForAll) {
			System.out.println(r.getValue()+"\t"+ MovieDatabase.getYear(r.getItem())+"\t"+ MovieDatabase.getTitle(r.getItem()));
			System.out.println("\t\t"+"Genres:"+"\t"+ MovieDatabase.getGenres(r.getItem()));
		}
	}
	
	public void printAverageRatingsByMinutes() {
		int minimalRaters = 5;
		int min = 105 , max = 135 ;
		ThirdRatings trdRatings = new ThirdRatings("src/data/ratings.csv");
		MovieDatabase.initialize("ratedmoviesfull.csv");
		System.out.println("Total movies: "+ MovieDatabase.size()+" , total raters: "+trdRatings.getRaterSize());
		
		ArrayList<Rating> ratingsForAll = trdRatings.getAverageRatings(minimalRaters, new MinutesFilter(min, max));
		Collections.sort(ratingsForAll); 
		
		System.out.println("find "+ratingsForAll.size()+" movies rated between "+min+":"+max);
		for(Rating r: ratingsForAll) {
			System.out.println(r.getValue()+"\t"+ MovieDatabase.getYear(r.getItem())+"\t"+ MovieDatabase.getTitle(r.getItem()));
			System.out.println("\t\t"+"Time:"+"\t"+ MovieDatabase.getMinutes(r.getItem()));
		}
	}
	
	public void printAverageRatingsByDirectors() {
		int minimalRaters = 4;
		String directorsList ="Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
		ThirdRatings trdRatings = new ThirdRatings("src/data/ratings.csv");
		MovieDatabase.initialize("ratedmoviesfull.csv");
		System.out.println("Total movies: "+ MovieDatabase.size()+" , total raters: "+trdRatings.getRaterSize());
		
		ArrayList<Rating> ratingsForAll = trdRatings.getAverageRatings(minimalRaters, new DirectorFilter(directorsList));
		Collections.sort(ratingsForAll); 
		
		System.out.println("find "+ratingsForAll.size()+" movies");
		for(Rating r: ratingsForAll) {
			System.out.println(r.getValue()+"\t"+ MovieDatabase.getTitle(r.getItem()));
			System.out.println("Director:"+"\t"+ MovieDatabase.getDirector(r.getItem()));
		}
	}
	
	public void printAverageRatingsByYearAfterAndGenre() {
		int minimalRaters = 3;
		int yearAfter = 1980;
		String genres = "Romance";
		ThirdRatings trdRatings = new ThirdRatings("src/data/ratings.csv");
		MovieDatabase.initialize("ratedmoviesfull.csv");
		System.out.println("Total movies: "+ MovieDatabase.size()+" , total raters: "+trdRatings.getRaterSize());
		
		// initiate the AllFilters
		AllFilters filters = new AllFilters();
		filters.addFilter(new YearAfterFilter(yearAfter));
		filters.addFilter(new GenreFilter(genres));
		ArrayList<Rating> ratingsForAll = trdRatings.getAverageRatings(minimalRaters, filters);
		Collections.sort(ratingsForAll); 
		
		System.out.println("find "+ratingsForAll.size()+" movies");
		for(Rating r: ratingsForAll) {
			System.out.println(r.getValue()+"\t"+ MovieDatabase.getTitle(r.getItem()));
			System.out.print("Genres:"+"\t"+ MovieDatabase.getGenres(r.getItem())+'\t');
			System.out.println("Year:"+"\t"+ MovieDatabase.getYear(r.getItem()));
		}
	}
	
	public void printAverageRatingsByDirectorsAndMinutes() {
		int minimalRaters = 3;
		int min = 90, max = 180;
		String directors =  "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";
		ThirdRatings trdRatings = new ThirdRatings("src/data/ratings.csv");
		MovieDatabase.initialize("ratedmoviesfull.csv");
		System.out.println("Total movies: "+ MovieDatabase.size()+" , total raters: "+trdRatings.getRaterSize());
		
		// initiate the AllFilters
		AllFilters filters = new AllFilters();
		filters.addFilter(new MinutesFilter(min, max));
		filters.addFilter(new DirectorFilter(directors));
		ArrayList<Rating> ratingsForAll = trdRatings.getAverageRatings(minimalRaters, filters);
		Collections.sort(ratingsForAll); 
		
		System.out.println("find "+ratingsForAll.size()+" movies");
		for(Rating r: ratingsForAll) {
			System.out.println(r.getValue()+"\t"+ MovieDatabase.getTitle(r.getItem()));
			System.out.print("Time:"+"\t"+ MovieDatabase.getMinutes(r.getItem())+'\t');
			System.out.println("Directors:"+"\t"+ MovieDatabase.getDirector(r.getItem()));
		}
	}
	public static void main(String args[]) {
		MovieRunnerWithFilter test = new MovieRunnerWithFilter();
		//test.printAveragetRatings();
		//test.printAverageRatingsByYear();
		//test.printAverageRatingsByGenre();
		//test.printAverageRatingsByMinutes();
		//test.printAverageRatingsByDirectors();
		//test.printAverageRatingsByYearAfterAndGenre();
		test.printAverageRatingsByDirectorsAndMinutes();
	}
}
