# A simple movie recommendation system

## Overview
It's the final project of [Java Programming: Build a Recommendation System](https://www.coursera.org/learn/java-programming-recommender/home/welcome) in Cousera.

You can click [this link](https://www.dukelearntoprogram.com//capstone/recommender.php?id=b15dByLiNFfSuv) to see the page, which will offer you 10 random movies from IMDB to rate.

After submitting your ratings, the system will offer you several movies(10 at most) that most fit your taste based on the database of IMDB. But I have set some threshold so that sometimes there will no be less 10 movies to show, you know, there may be some situations that a user's taste is too special to recommend movies.

## System Design
the course contains 5 steps
### Step 1
[read document](https://www.coursera.org/learn/java-programming-recommender/supplement/ILMcl/programming-exercise-step-one)
class revolved: Movie, Rating, Rater, FirstRating

##class Movie##: basic information such as id, title, etc.

##class Rating##: item(id of movie), value;

##Rater## keeps track of one rater and all their ratings:
- myID: a unique String ID for this rater
- myRatings: an ArrayList of Ratings

##Assignment##
there're 2 csv files, one contains all the movie information, other contains all the ratings of all the raters, after loading, we can get two import attributes:
movieList, raterList. raterList consists of Rater.

We can get some information:
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
[read document]()
### Step 3
[read document]()
### Step 4
[read document]()
### Step 5
[read document]()



