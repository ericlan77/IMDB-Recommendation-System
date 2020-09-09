# A simple movie recommendation system

## Overview
It's the final project of [Java Programming: Build a Recommendation System](https://www.coursera.org/learn/java-programming-recommender/home/welcome) in Cousera.

You can click [this link](https://www.dukelearntoprogram.com//capstone/recommender.php?id=b15dByLiNFfSuv) to see the page, which will offer you 10 random movies from IMDB to rate.

After submitting your ratings, the system will offer you several movies(10 at most) that most fit your taste based on the database of IMDB. But I have set some threshold so that sometimes there will no be less 10 movies to show, you know, there may be some situations that a user's taste is too special to recommend movies.

## System Design
the course contains 5 steps
### Step 1
[read document](https://www.coursera.org/learn/java-programming-recommender/supplement/ILMcl/programming-exercise-step-one)
**class revolved**: Movie, Rating, Rater, FirstRatings

**class Movie**: basic information such as id, title, etc.

**class Rating**: item(id of movie), value;

**Rater**: keeps track of one rater and all their ratings:
- myID: a unique String ID for this rater
- myRatings: an ArrayList of Ratings

**Assignment**:
there're 2 csv files, one contains all the movie information, other contains all the ratings of all the raters, after loading, we can get two import attributes:
movieList, raterList. raterList consists of Rater.

We can get following output:
```
The num of movies is: 3143
The num of COMEDY movies is: 960
The num of 150+min movies is: 132
Director: Woody Allen with maxmium movies: 23
Rater ID is found: 193 Numbers of ratings: 119
Rater ID is found: 735 Max numbers of ratings: 314
The movie id 1798709 has ratings: 38
The number of rated movie is: 3143
Movie [id=3112654, title=Mea culpa, year=2014, genres= Action, Thriller]
```
### Step 2
[read document](https://www.coursera.org/learn/java-programming-recommender/supplement/KTrOQ/programming-exercise-step-two)

**class revolved**: SecondRatings， MovieRunnerAverage

In SecondRatings we get movieList, raterList by FirstRatings, compute the average rating value of a specific movie. 

**Assignment**:
create a MovieRunnerAverage, it has 2 method:
1. **printAveragetRatings()**：given "minimalRaters", this method should find the average rating for every movie that has been rated by at least minimalRaters raters；
2. **getAverageRatingOneMovie**: given the movie id, get the average rating of this movie.

We can get following output:
```
Total movies: 3143 , total raters: 1048
There are 3 movies with more than 60 raters
7.616666666666666	Man of Steel
8.376811594202898	The Wolf of Wall Street
8.421875	Gravity
rating of the Movie "Vacation" is 6.8
```

### Step 3
[read document](https://www.coursera.org/learn/java-programming-recommender/supplement/E9Xy0/programming-exercise-step-three)
**class revolved**：EfficientRater, MovieDatabase, Filter, ThirdRatings, MovieRunnerWithFilters

**Assiment1**:
original Rater class use ArrayList<Ratings> to store all the ratings of one rater, if you want to know a rater's rating about the movie, you should traverse all the list, the time complexity is O(n). But if using hashmap, the complexity will be O(1) by calling hashmap.contains(key) or hashmap.get(key).
so EfficientRater use HashMap<movieID, Ratings>, one movieID maps a Rating class.

**Assignment2**:
**MovieDatabase**: it stores movie information in a HashMap for fast lookup of movie information given a movie **ID**, also **allows filtering** movies based on queries. All methods and fields in the class are static. Once it's initialized,we'll be able to access methods in MovieDatabase without using new to create objects, but by calling methods like MovieDatabase.getMovie("0120915"), by movieID we can index a Movie object.

**Filter** class: method of filters will returns true if the movie satisfies the criteria in the method and returns false otherwise. There're GeneFilter, YearFilter, etc. In **AllFilters** class we get a list of different filters, it return true only when this movie satisfies all the criteria.

In **MovieDatabase** we call **ArrayList<String> filterBy(Filter f)** to get a list of movies satisfy all the criteria.

ThirdRatings is similar to SecondRatings, only adding filters to filter before computing the average rating.
create MovieRunnerWithFilters to output information.
(Year after "2015", Genres contains "Romance")
```
find 5 movies
4.75	Fifty Shades of Grey
Genres:	Drama, Romance	Year:	2015
6.0	Paper Towns
Genres:	Drama, Mystery, Romance	Year:	2015
...
```
### Step 4
[read document](https://www.coursera.org/learn/java-programming-recommender/supplement/433EU/programming-exercise-step-four)
**How to recommend movie for a rater-A?**:
First, we compute the dot product of ratings of movies that rater-A and other raters both rated. Then we can get a list of raters has most similar taste as rater-A. Next, we dive into those chosen raters's movie ratings. What they like most may match the taste of rater-A. 
MovieScore = \Sigma dotProduct * 

**class revolved**：RaterDatabase, RaterDatabase, MovieRunnerSimilarRatings

**RaterDatabase**: A HashMap named ourRaters that maps a rater ID String to a Rater object that includes all the movie ratings made by this rater.

**MovieRunnerSimilarRatings**: 

**Rating<otherId, dotProduct>**: otherRater's ID maps it's dot product of current rater.

**FourthRatings**: in this class we have following methods:
- dotProduct(Rater me, Rater other): translate a rating from the scale 0 to 10 to the scale -5 to 5. Return the dot product of the ratings of movies that they both rated. The higher the score, the more similar two people are, and vice versa.
- getSimilarities(String id): given a rater's id, call dotProduct to find the most similar raters. return a Rating list.
- getSimilarRatings:




### Step 5
[read document]()



