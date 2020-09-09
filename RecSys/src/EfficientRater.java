import java.util.ArrayList;
import java.util.HashMap;

/**
 *  使用hashmap提高检索效率, 用movie id就可以检索到想要的rating对象
 */
public class EfficientRater implements Rater {
	private String myID;
	private HashMap<String, Rating> ratingMap;
	
	public EfficientRater(String id) {
        myID = id;
        ratingMap = new HashMap<String, Rating>();
    }
	
	@Override
	public void addRating(String item, double rating) {
		// TODO Auto-generated method stub
		ratingMap.put(item, new Rating(item, rating));
	}

	@Override
	public boolean hasRating(String item) {
		// TODO Auto-generated method stub
		return ratingMap.containsKey(item);
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return myID;
	}
	
	public ArrayList<Rating> getRating(){
		ArrayList<Rating> list = new ArrayList<Rating>();
		for(String id: ratingMap.keySet())
			list.add(ratingMap.get(id));
    	return list;
    }
	
	@Override
	public double getRating(String item) {
		// TODO Auto-generated method stub
		if(ratingMap.containsKey(item))
			return ratingMap.get(item).getValue();
		else
			return -1;
	}

	@Override
	public int numRatings() {
		// TODO Auto-generated method stub
		return ratingMap.size();
	}
	
	 // Get all the movie rated. Return a movie list.
	@Override
	public ArrayList<String> getItemsRated() {
		// TODO Auto-generated method stub
		ArrayList<String> list = new ArrayList<String>();
        for(String movie_id: ratingMap.keySet()) 
        	list.add(ratingMap.get(movie_id).getItem());
        return list;
	}
}
