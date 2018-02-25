package tk.leopro.petzyandroid.pojo

import java.util.HashMap

class Location {

    /**
     *
     * @return
     * The lat
     */
    val lat: String? = null
    /**
     *
     * @return
     * The lng
     */
    val lng: String? = null
    val additionalProperties: MutableMap<String, Object> = HashMap<String, Object>()

    fun setAdditionalProperty(name: String, value: Object) {
        this.additionalProperties.put(name, value)
    }

}
