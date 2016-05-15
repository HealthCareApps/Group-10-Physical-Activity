package edu.fau.group10.AndroidPhysicalTherapy;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class user_model {

    @SerializedName("_id")
    @Expose
    public String Id;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("lastName")
    @Expose
    public String lastName;
    @SerializedName("dob")
    @Expose
    public float dob;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("vista_id")
    @Expose
    public String vistaId;
    @SerializedName("exercises")
    @Expose
    public List<Integer> exercises = new ArrayList<Integer>();

}