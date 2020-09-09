import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Copy from ThirdRatings. Made some modification.
 * Add dotProduct and getSilmilar
 * @author BeanLan
 *
 */
public class FourthRatings {
	private ArrayList<Rater> myRaters;

    
    /**
     * Call the FirstRatings
     * @param string
     */
	public FourthRatings(String string) {
		// TODO Auto-generated constructor stub
		// 直接从RaterDatabase中获取所有raters信息
		myRaters = RaterDatabase.getRaters();
	}
	
	
	public FourthRatings() {
		// TODO Auto-generated constructor stub
	}


	public int getRaterSize() {
		return myRaters.size();
	}
	
	/**
	 * Get the average ratings of a single movie with movie_id
	 * @param movie_id
	 * @param minimalRaters
	 * @return
	 */
	private double getAverageByID(String movie_id, int minimalRaters) {
		double aveRating = 0.0;
		ArrayList<Double> allRatings = new ArrayList<Double>();
		// traverse all the raters
		for(Rater r: myRaters) {
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
	
	
	private double dotProduct(Rater me, Rater other) {
		// 我的 和 比较人 的所有电影打分
		ArrayList<String> meItemsRated = me.getItemsRated();
		ArrayList<String> otherItemsRated = other.getItemsRated();
		double product = 0.0;
		for(String movie_id: meItemsRated) {
			if(other.hasRating(movie_id)){
				product = product + (me.getRating(movie_id)-5)*(other.getRating(movie_id)-5);
			}
		}
		return product;
	}
	
	/**
	 * The object <Rating> now store the other raterID and its similarity value compared
	 * to current rater.
	 * @param id
	 * @return
	 */
	public ArrayList<Rating> getSimilarities(String id) {
		ArrayList<Rating> similarList = new ArrayList<Rating>();
		Rater me = RaterDatabase.getRater(id);
		for(Rater r: RaterDatabase.getRaters()) {
			String otherID = r.getID();
			if(!otherID.equals(id)) {
				double similar = dotProduct(me, r);
				if (similar >= 0)
					similarList.add(new Rating(otherID, similar));
			}
		}
		Collections.sort(similarList, Collections.reverseOrder());
		return similarList;
	}
	
	/**
	 * 先通过相似度排序, 得到相似度高的raters, 整理出他们打分过的所有电影, 加权相似值和分数, 得到电影最终的加权分数,
	 * 再根据加权分数对电影进行排名
	 * @param id rater ID
	 * @param numSimilarRaters 最高相似度的其他rater数量(相似值为正数)
	 * @param minimalRaters 最少评分人数的数量(仅来自这些最高相似度的rater)
	 * @return 返回推荐电影
	 */
	public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters){
		// return the top movies recommended to the id rater.
		ArrayList<Rating> topRatersList = new ArrayList<Rating>();
		// get a list of raters with its dot product similarty
		ArrayList<Rating> similarList = getSimilarities(id);

		// get topList
		for(int i=0; i<numSimilarRaters; i++){
			// we need positive similarty raters
			if (similarList.get(i).getValue() > 0)
				topRatersList.add(similarList.get(i));
		}

		// now the topList stores the raters we need, traversing them
		// 得到一个由这些用户打分过得电影 hashmap<电影id, 打分人数>
		HashMap<String, Integer> movieList = new HashMap<String, Integer>();
		for(Rating rater: topRatersList){
			// RaterDatabase.getRater传入rater.getItem(raterID)得到指定rater, getItemsRated得到它所有打分电影
			for(String movie_id: RaterDatabase.getRater(rater.getItem()).getItemsRated()){
				if (!movieList.containsKey(movie_id))
					movieList.put(movie_id, Integer.valueOf(1));
				else
					movieList.put(movie_id, movieList.get(movie_id)+1);
			}
		}
		// movieRatedMap 存好了所有不重复且已经被打分过得电影
		ArrayList<Rating> recommendMovie = new ArrayList<Rating>();
		for(String movie_id: movieList.keySet()){
			double weight = 0;
			double weightedRating = 0;
			for(Rating rater: topRatersList){
				String rater_id = rater.getItem();
				// 得到当前rater对对该电影的评分 与相似得分相乘 得到weightedRating
				double movie_ratings = RaterDatabase.getRater(rater_id).getRating(movie_id);
				if (movie_ratings > 0 & movieList.get(movie_id) >= minimalRaters){
					weight ++;
					weightedRating = weightedRating + movie_ratings * rater.getValue();
				}
			}
			weightedRating = weightedRating / weight;
			// 再把加权得分放入map, 而且要满足最小评论人数的要求
			if(movieList.get(movie_id) >= minimalRaters)
				recommendMovie.add(new Rating(movie_id, weightedRating));
		}
		Collections.sort(recommendMovie, Collections.reverseOrder());
		return recommendMovie;
	}

	/**
	 * 调用getSimilarRatings, 返回符合filter条件的电影movie
	 * @param filter
	 * @return
	 */
	public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters, Filter filter){
		ArrayList<Rating> originMovieList = getSimilarRatings(id, numSimilarRaters, minimalRaters);
		ArrayList<Rating> filteredMovieList = new ArrayList<Rating>();
		// 通过过滤器筛选电影
		for(Rating movie: originMovieList){
			if(filter.satisfies(movie.getItem())){
				filteredMovieList.add(movie);
			}
		}
		return filteredMovieList;
	}

	/**
	 * print the recommendedMovies
	 * @param user_id 用户id
	 * @param numSimilarRaters	指定相似用户数
	 * @param minimalRaters		最少需要多少位相似用户打分过
	 * @param n 	指定top N电影
	 */
	public void printRecommendMovies(int user_id, int numSimilarRaters, int minimalRaters, int n){
		ArrayList<Rating> list = getSimilarRatings(String.valueOf(user_id), numSimilarRaters, minimalRaters);
		// 只看前十的推荐
		System.out.println("Recommended for user "+user_id);
		for(int i=0; i < n;i++)
			System.out.println(list.get(i).getValue()+" "+MovieDatabase.getTitle(list.get(i).getItem()));
	}

	public void printRecommendMoviesByGenre(int user_id, int numSimilarRaters, int minimalRaters, int n, String genre){
		ArrayList<Rating> list = getSimilarRatingsByFilter(String.valueOf(user_id), numSimilarRaters, minimalRaters, new GenreFilter(genre));
		// 只看前十的推荐
		System.out.println("Recommended for user "+user_id);
		for(int i=0; i < n;i++)
			System.out.println(list.get(i).getValue()+" "+MovieDatabase.getTitle(list.get(i).getItem()));
	}

	public void printRecommendMoviesByDirector(int user_id, int numSimilarRaters, int minimalRaters, int n, String director){
		ArrayList<Rating> list = getSimilarRatingsByFilter(String.valueOf(user_id), numSimilarRaters, minimalRaters, new DirectorFilter(director));
		// 只看前十的推荐
		System.out.println("Recommended for user "+user_id);
		for(int i=0; i < n;i++)
			System.out.println(list.get(i).getValue()+" "+MovieDatabase.getTitle(list.get(i).getItem()));
	}

	public void printRecommendMoviesByGenreAndMinutes(int user_id, int numSimilarRaters, int minimalRaters, int n, String genre, int min , int max){
		AllFilters twoFilters = new AllFilters();
		twoFilters.addFilter(new GenreFilter(genre));
		twoFilters.addFilter(new MinutesFilter(min, max));
		ArrayList<Rating> list = getSimilarRatingsByFilter(String.valueOf(user_id), numSimilarRaters, minimalRaters, twoFilters);
		// 只看前十的推荐
		System.out.println("Recommended for user "+user_id);
		for(int i=0; i < n;i++)
			System.out.println(list.get(i).getValue()+" "+MovieDatabase.getTitle(list.get(i).getItem()));
	}

	public void printRecommendMoviesByYearAfterAndMinutes(int user_id, int numSimilarRaters, int minimalRaters, int n, int year, int min, int max){
		AllFilters twoFilters = new AllFilters();
		twoFilters.addFilter(new YearAfterFilter(year));
		twoFilters.addFilter(new MinutesFilter(min, max));
		ArrayList<Rating> list = getSimilarRatingsByFilter(String.valueOf(user_id), numSimilarRaters, minimalRaters, twoFilters);
		// 只看前十的推荐
		System.out.println("Recommended for user "+user_id);
		for(int i=0; i < n;i++)
			System.out.println(list.get(i).getValue()+" "+MovieDatabase.getTitle(list.get(i).getItem()));
	}



	public static void main(String args[]) {
		FourthRatings test = new FourthRatings();
		MovieDatabase.initialize("ratedmoviesfull.csv");
		RaterDatabase.initialize("ratings.csv");

		//**** test ****//
		// test the other users' similarity with user 1
		//ArrayList<Rating> list = test.getSimilarities("1");
		//for(Rating r: list) System.out.println("user1 similar user"+r.getItem()+": "+r.getValue());
		//**** test ****//

		test.printRecommendMovies(337, 10,3, 1);
		test.printRecommendMoviesByGenre(964, 20,5, 1, "Mystery");
		test.printRecommendMovies(71, 20,5, 1);
		test.printRecommendMoviesByDirector(120,10,2,1,"Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh");
		test.printRecommendMoviesByGenreAndMinutes(168, 10, 3, 1, "Drama",80 ,160);
		test.printRecommendMoviesByYearAfterAndMinutes(314,10,5,1,1975,70,200);
	}
}
