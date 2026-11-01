
package com.planethunting;

import com.google.gson.annotations.SerializedName;

public class Planet {

    @SerializedName("pl_name")
    String name;

    @SerializedName("pl_eqt")
    double temp;

    @SerializedName("pl_rade")
    double radius;

    @SerializedName("pl_orbper")
    double orbit;

    @SerializedName("sy_vmag")
    double brightness;

    @SerializedName("sy_dist")
    double distance;

    double score;
}
