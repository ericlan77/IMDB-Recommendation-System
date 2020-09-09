import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import org.apache.commons.csv.*;

/**
 * Load the data of movie and rater respectively to list.
 * Get some simple information about them.
 * @author BeanLan
 * @version 1.0 (20190917)
 */
public class FirstRatings {
	/**
	 *
	 * @param filename
	 * @return ArrayList<Movie>
	 */
	private static ArrayList<Movie> movieList;
	private static ArrayList<EfficientRater> raterList;
	public FirstRatings() {
		movieList = new ArrayList<Movie>();
		raterList = new ArrayList<EfficientRater>();
	}
	
	public ArrayList<Movie> loadMovies(String filename) {
		movieList.clear();
		try {
			Reader in = new FileReader(filename);
			Iterable<CSVRecord> records = CSVFormat.DEFAULT
					.withFirstRecordAsHeader()
					.withFirstRecordAsHeader()
					.parse(in);
			for (CSVRecord record : records) {
				String anID = record.get("id");
				String aTitle = record.get("title");
				String aYear = record.get("year");
			    String theGenres = record.get("genre");
			    String aDirector  = record.get("director");
			    String aCountry  = record.get("country");
			    String aPoster  = record.get("poster");
			    int theMinutes  = Integer.parseInt(record.get("minutes"));
			    Movie m = new Movie(anID, aTitle, aYear,  theGenres,  aDirector, aCountry,  aPoster,  theMinutes);
			    movieList.add(m);
			}
		}
		catch (Exception e){
			System.out.println(e); 
		}
		return movieList;
	}
	
	/**
	 * Get the number of movies, how many Comedy, how many more than 150 minutes
	 */
	public void testNumofMovies() {
		int movieNum = 0;
		int comedy = 0, movie = 0;
		for (Movie m: movieList) {
			movieNum ++;
			// One movie may have more than one genre.
			// **Use indexOf instead of split. More easy and concise.**
			if (m.getGenres().indexOf("Comedy") != -1)
                comedy ++;
			if(m.getMinutes() > 150)
				movie ++;
		}
		System.out.println("The num of movies is: "+movieNum);
		System.out.println("The num of COMEDY movies is: "+comedy);
		System.out.println("The num of 150+min movies is: "+movie);
	}
	
	/**
	 * Find the director who direct most movies, Remember: one movie may has
	 * more than one director.
	 */
	public void findMaxMovieDirector() {
		HashMap<String, ArrayList<String>> directorMap = new HashMap<String, ArrayList<String>>();
		for (Movie moive: movieList) {

			String directors = moive.getDirector(); // there may be more than one director
			String[] directorList = directors.split(",");
			for(int i=0 ; i < directorList.length ; i++) {
				// ���map���Ѿ��иõ��ݵ���Ϣ
				if(directorMap.containsKey(directorList[i])) {
					ArrayList<String> movies = directorMap.get(directorList[i]);
					movies.add(moive.getTitle());
					directorMap.put(directorList[i], movies);
				}
				// map��û�иõ��ݵ���Ϣ
				else {
					ArrayList<String> movies = new ArrayList<String>();
					movies.add(moive.getTitle());
					directorMap.put(directorList[i], movies);
				}
			}
		}
		int maxNum = 0;
		String maxDirector = null;
		for(String director: directorMap.keySet()) {
			if(directorMap.get(director).size() > maxNum) {
				maxNum = directorMap.get(director).size();
				maxDirector = director;
			}
		}
		System.out.println("Director: "+maxDirector+" with maxmium movies: "+ maxNum);
	}
	
	/**
	 *  �����Ӱ����������
	 * @param filename
	 */
	public ArrayList<EfficientRater> loadRaters(String filename) {
		raterList.clear();
		HashMap<String, EfficientRater> raterMap = new HashMap<String, EfficientRater>();
		
		try {
			Reader in = new FileReader(filename);
			Iterable<CSVRecord> records = CSVFormat.DEFAULT
					.withFirstRecordAsHeader()
					.withFirstRecordAsHeader()
					.parse(in);
			for (CSVRecord record : records) {
				String raterID = record.get("rater_id");
				String movieID = record.get("movie_id");
				Double rating = Double.valueOf(record.get("rating"));
			    // ��ǰ�����˲�����
			    if(!raterMap.containsKey(raterID)) {
			    	EfficientRater newRater = new EfficientRater(raterID);
			    	newRater.addRating(movieID, rating);
			    	raterMap.put(raterID, newRater);
			    }
			    // ��ǰ�����˴���
			    else {
			    	EfficientRater curRater = raterMap.get(raterID);
			    	curRater.addRating(movieID, rating);
			    	raterMap.put(raterID, curRater);
			    }
			}
		}
		catch (Exception e){
			System.out.println(e); 
		}
		
		for(String id: raterMap.keySet()) {
			raterList.add(raterMap.get(id));
		}
		
		return raterList;
	}
	
	/**
	 * get all the information about the rating file.
	 * @param filename
	 */
	public void testLoadRaters(String filename) {
		raterList = loadRaters(filename);
		System.out.println("The total number of raters: "+raterList.size());
		for(EfficientRater rater: raterList) {
			System.out.println("Rater ID: "+rater.getID()+" numbers of ratings: "+rater.numRatings());
		}
	}
	
	/**
	 * Get specified rater's rating information
	 * @param raterID
	 */
	public void getRaterInfo(String raterID) {
		for(EfficientRater rater: raterList) {
			if(rater.getID().equals(raterID)) {
				System.out.println("Rater ID is found: "+rater.getID()+" Numbers of ratings: "+rater.numRatings());
			}
		}
	}
	
	/**
	 * Find a rater who has most ratings.
	 * @return
	 */
	public int getRaterofMaxRatings() {
		int maxRating = 0;
		String maxRater = null;
		for(EfficientRater rater: raterList) {
			if(rater.numRatings() > maxRating) {
				maxRating = rater.numRatings();
				maxRater = rater.getID();
			}
		}
		System.out.println("Rater ID is found: "+maxRater+" Max numbers of ratings: "+maxRating);
		return maxRating;
	}
	
	/**
	 * Find the ratings of specified movie.
	 * @param movie_id
	 * @return
	 */
	public int getMovieRatings(String movie_id) {
		int ratings = 0;
		for(EfficientRater rater: raterList) {
			if(rater.hasRating(movie_id))
				ratings ++;
		}
		System.out.println("The movie id "+movie_id+" has ratings: "+ratings);
		return ratings;
	}

	/**
	 * Get the number of all rated movie(no repeat)
	 * @return
	 */
	public int numOfMovieRated() {
		ArrayList<String> movie_id  = new ArrayList<String>();
		for(EfficientRater rater: raterList) {
			ArrayList<String> movieList = rater.getItemsRated();
			for(String movie: movieList) {
				if(!movie_id.contains(movie))
					movie_id.add(movie);
			}
		}
		System.out.println("The number of rated movie is: "+ movie_id.size());
		return movie_id.size();
	}
	
	public static void main(String args[]) {
		FirstRatings test = new FirstRatings();
		movieList = test.loadMovies("src/data/ratedmoviesfull.csv");
		test.testNumofMovies();
		test.findMaxMovieDirector();
		
		raterList = test.loadRaters("src/data/ratings.csv");
		test.getRaterInfo("193");
		test.getRaterofMaxRatings();
		test.getMovieRatings("1798709");
		test.numOfMovieRated();
		
		System.out.println(movieList.get(0).toString());
	}
}
