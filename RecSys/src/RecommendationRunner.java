import java.util.ArrayList;
import java.util.Random;

/**
 * @ClassName: RecommendationRunner
 * @Description: 通过Database获取全部电影数据, 随机获取20个电影
 * @Author: Eric Lan
 * @Date: 2019/10/19 10:03
 * @Version: TODO
 **/
public class RecommendationRunner implements Recommender {
    public ArrayList<String> getItemsToRate (){
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<String> items = new ArrayList<String>();
        Random r = new Random();
        for(int i=0; i<10; i++){
            items.add(movies.get(r.nextInt(movies.size())));
        }
        return items;
    }

    @Override
    public void printRecommendationsFor(String webRaterID) {
        FourthRatings runner = new FourthRatings();
        ArrayList<Rating> recommendMovies = runner.getSimilarRatings(webRaterID, 10,2);

        if(recommendMovies.size() == 0){
            System.out.println("Sorry, no recommend movie for you");
            return;
        }
        StringBuffer out = new StringBuffer();
        String head = "<table>\n" +
                "  <tbody> \n" +
                "    <tr>\n" +
                "      <th>Rank\t</th>\n" +
                "      <th>Movie</th>\n" +
                "    </tr>\n";
        out.append(head);
        int topN = 10;
        if(topN > recommendMovies.size())
            topN = recommendMovies.size();
        for(int i=0; i<topN; i++){
            Rating movie = recommendMovies.get(i);
            String s = "<tr>\n" +
                    "      </td>\n" +
                    "      <td style=\"color:red;\">"+(i+1)+"</td>\n" +
                    "      <td>"+MovieDatabase.getYear(movie.getItem())+"\n" +
                    "      <a href=\"http://www.imdb.com/title/tt"+movie.getItem()+"/\">"+MovieDatabase.getTitle(movie.getItem())+"</a>\n" +
                    "   </tr>\n";
            out.append(s);
        }
        out.append("  </tbody>\n" +
                "</table>");
        System.out.println(out.toString());
    }


}
