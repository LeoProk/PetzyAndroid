package tk.leopro.petzyandroid.pojo

import java.util.ArrayList
import java.util.HashMap

class Result {

    /**
     *
     * @return
     * The formatted_address
     */
    /**
     *
     * @param formatted_address
     * The formatted_address
     */
    var formattedAddress: String? = null
    /**
     *
     * @return
     * The geometry
     */
    /**
     *
     * @param geometry
     * The geometry
     */
    var geometry: Geometry? = null
    /**
     *
     * @return
     * The icon
     */
    /**
     *
     * @param icon
     * The icon
     */
    var icon: String? = null
    /**
     *
     * @return
     * The id
     */
    /**
     *
     * @param id
     * The id
     */
    var id: String? = null
    /**
     *
     * @return
     * The name
     */
    /**
     *
     * @param name
     * The name
     */
    var name: String? = null
    /**
     *
     * @return
     * The placeId
     */
    /**
     *
     * @param placeId
     * The place_id
     */
    var placeId: String? = null
    /**
     *
     * @return
     * The reference
     */
    /**
     *
     * @param reference
     * The reference
     */
    var reference: String? = null
    /**
     *
     * @return
     * The types
     */
    /**
     *
     * @param types
     * The types
     */
    var types: List<String> = ArrayList<String>()
    val additionalProperties: Map<String, Object> = HashMap<String, Object>()

    fun setAdditionalProperty(name: String, value: Object) {
        this.additionalProperties.put(name, value)
    }

}
