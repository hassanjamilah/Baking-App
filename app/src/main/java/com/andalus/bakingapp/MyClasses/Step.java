package com.andalus.bakingapp.MyClasses;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Step implements Parcelable {

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
    private static final String JSON_STEP_ID_KEY = "id";
    private static final String JSON_STEP_SHORT_DESCRIPTION_KEY = "shortDescription";
    private static final String JSON_STEP_DESCRIPTION_KEY = "description";
    private static final String JSON_STEP_VIDEO_URL_KEY = "videoURL";
    private static final String JSON_STEP_THUMBAIL_URL_KEY = "thumbnailURL";
    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbailURL;

    public Step() {
    }

    protected Step(Parcel in) {
        this.id = in.readInt();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbailURL = in.readString();
    }

    public static Creator<Step> getCREATOR() {
        return CREATOR;
    }

    /**
     * This method is used to get the steps from the json array object
     * this object is getten in the Recipe parseFromJson Method
     * and return an array of steps
     *
     * @param arr
     * @return
     */
    public static ArrayList<Step> parseIngredientsFromJson(JSONArray arr) {

        if (arr == null || arr.length() == 0) {

            return null;
        }
        ArrayList<Step> steps = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            try {
                JSONObject ob = arr.getJSONObject(i);
                Step st = new Step();
                st.setDescription(ob.getString(JSON_STEP_DESCRIPTION_KEY));
                st.setShortDescription(ob.getString(JSON_STEP_SHORT_DESCRIPTION_KEY));
                st.setStepId(ob.getInt(JSON_STEP_ID_KEY));
                st.setThumbailURL(ob.getString(JSON_STEP_THUMBAIL_URL_KEY));
                st.setVideoURL(ob.getString(JSON_STEP_VIDEO_URL_KEY));

                steps.add(st);
            } catch (JSONException e) {
                e.printStackTrace();


            }

        }
        return steps;

    }

    public int getStepId() {
        return id;
    }

    public void setStepId(int stepId) {
        this.id = stepId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbailURL() {
        return thumbailURL;
    }

    public void setThumbailURL(String thumbailURL) {
        this.thumbailURL = thumbailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbailURL);
    }

    @Override
    public String toString() {
        return "Step{" +
                "stepId=" + id +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", thumbailURL='" + thumbailURL + '\'' +
                '}';
    }

}
