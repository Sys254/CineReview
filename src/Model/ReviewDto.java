// Model - ReviewDto
package Model;

public class ReviewDto {
    private int id;
    private String userId;
    private String movieTitle;
    private String movieGenre;
    private int rating;
    private String reviewText;



    // 생성자
    public ReviewDto(int id,String userId, String movieTitle,String movieGenre, int rating, String reviewText) {
        this.id = id;
        this.userId = userId;
        this.movieTitle = movieTitle;
        this.movieGenre = movieGenre;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    // Getter와 Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle =movieTitle;
    }


    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}

