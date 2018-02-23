package tk.leopro.petzyandroid.pojo

import java.util.HashMap

class Geometry {

    /**
     *
     * @return
     * The location
     */
    /**
     *
     * @param location
     * The location
     */
    var location: Location? = null
    val additionalProperties: Map<String, Object> = HashMap<String, Object>()

    fun setAdditionalProperty(name: String, value: Object) {
        this.additionalProperties.put(name, value)
    }

}
