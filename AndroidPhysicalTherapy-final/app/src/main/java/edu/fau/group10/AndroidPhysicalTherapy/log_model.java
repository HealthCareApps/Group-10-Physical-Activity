package edu.fau.group10.AndroidPhysicalTherapy;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class log_model {

    @SerializedName("vista_id")
    @Expose
    public int vistaId;
    @SerializedName("exercise_id")
    @Expose
    public int exerciseId;
    @SerializedName("date_time")
    @Expose
    public String dateTime;
    @SerializedName("elapsedTime")
    @Expose
    public int elapsedTime;

    @Override
    public String toString() {
        return "log_model{" +
                "vistaId=" + vistaId +
                ", exerciseId=" + exerciseId +
                ", dateTime='" + dateTime + '\'' +
                ", elapsedTime=" + elapsedTime +
                '}';
    }
}