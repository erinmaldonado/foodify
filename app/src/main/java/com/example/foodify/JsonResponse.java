package com.example.foodify;

import java.net.URI;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("likes")
    @Expose
    private Object likes;
    @SerializedName("badges")
    @Expose
    private List<String> badges = null;
    @SerializedName("important_badges")
    @Expose
    private List<String> importantBadges = null;
    @SerializedName("nutrition_widget")
    @Expose
    private String nutritionWidget;
    @SerializedName("serving_size")
    @Expose
    private String servingSize;
    @SerializedName("number_of_servings")
    @Expose
    private Integer numberOfServings;
    @SerializedName("spoonacular_score")
    @Expose
    private Integer spoonacularScore;
    @SerializedName("breadcrumbs")
    @Expose
    private List<String> breadcrumbs = null;
    @SerializedName("generated_text")
    @Expose
    private String generatedText;
    @SerializedName("ingredientCount")
    @Expose
    private Object ingredientCount;
    @SerializedName("images")
    @Expose
    private List<URI> images = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Object getLikes() {
        return likes;
    }

    public void setLikes(Object likes) {
        this.likes = likes;
    }

    public List<String> getBadges() {
        return badges;
    }

    public void setBadges(List<String> badges) {
        this.badges = badges;
    }

    public List<String> getImportantBadges() {
        return importantBadges;
    }

    public void setImportantBadges(List<String> importantBadges) {
        this.importantBadges = importantBadges;
    }

    public String getNutritionWidget() {
        return nutritionWidget;
    }

    public void setNutritionWidget(String nutritionWidget) {
        this.nutritionWidget = nutritionWidget;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public Integer getNumberOfServings() {
        return numberOfServings;
    }

    public void setNumberOfServings(Integer numberOfServings) {
        this.numberOfServings = numberOfServings;
    }

    public Integer getSpoonacularScore() {
        return spoonacularScore;
    }

    public void setSpoonacularScore(Integer spoonacularScore) {
        this.spoonacularScore = spoonacularScore;
    }

    public List<String> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(List<String> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    public String getGeneratedText() {
        return generatedText;
    }

    public void setGeneratedText(String generatedText) {
        this.generatedText = generatedText;
    }

    public Object getIngredientCount() {
        return ingredientCount;
    }

    public void setIngredientCount(Object ingredientCount) {
        this.ingredientCount = ingredientCount;
    }

    public List<URI> getImages() {
        return images;
    }

    public void setImages(List<URI> images) {
        this.images = images;
    }

}
