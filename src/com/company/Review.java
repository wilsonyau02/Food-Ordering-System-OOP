package com.company;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Review {
    private String description;
    private int rating = -1;
    private static int totalNumberOfRating = 0, totalRating = 0;
    private LocalDateTime dateTime;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
    private boolean reviewed = false;



    public Review() {
        this.rating = -1;
        this.description = "";
    }

    public static int getTotalRating() {
        return totalRating;
    }

    public String getDateTime() {
        return formatter.format(dateTime);
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }


    public void setReview(int rating) {
        if (rating == -1){}
        else {totalRating -= this.rating;}
        totalRating += rating;
        this.rating = rating;
        this.description = "";
        this.dateTime = LocalDateTime.now();
        if (this.reviewed) {}
        else {
            totalNumberOfRating++;
            this.reviewed = true;
        }
    }

    public void setReview(double rating) {
        if (rating == -1){}
        else {totalRating -= this.rating;}
        totalRating += rating;
        this.rating = (int)rating;
        this.description = "";
        this.dateTime = LocalDateTime.now();
        if (this.reviewed) {}
        else {
            totalNumberOfRating++;
            this.reviewed = true;
        }
    }

    public void setReview(int rating, String description) {
        if (rating == -1){}
        else {totalRating -= this.rating;}
        totalRating += rating;
        this.rating = rating;
        this.description = description;
        this.dateTime = LocalDateTime.now();
        if (this.reviewed) {}
        else {
            totalNumberOfRating++;
            this.reviewed = true;
        }
    }

    public void setReview(double rating, String description) {
        if (rating == -1){}
        else {totalRating -= this.rating;}
        totalRating += rating;
        this.rating = (int)rating;
        this.description = description;
        this.dateTime = LocalDateTime.now();
        if (this.reviewed) {}
        else {
            totalNumberOfRating++;
            this.reviewed = true;
        }
    }

    @Override
    public String toString() {
        StringBuilder rating = new StringBuilder("");
        if (this.rating == 0) {
            for (int i = 1; i <= this.rating; i++) {
                rating.append("*");
            }
        } else {
            rating.append("None");
        }

        if (description.equals("")) {
            return "Date and time: " + formatter.format(dateTime) +
                    "rating: " + rating +
                    "\ndescription: -";
        }else {
            return "Date and time: " + formatter.format(dateTime) +
                    "rating: " + rating +
                    "\ndescription: " + description;
        }
    }

    public static double averageRating() {
        if (totalNumberOfRating == 0){
            return 0;
        }else {
            return (double)totalRating / totalNumberOfRating;
        }
    }
}