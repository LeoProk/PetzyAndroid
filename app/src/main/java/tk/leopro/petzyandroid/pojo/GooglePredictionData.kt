package tk.leopro.petzyandroid.pojo

import java.util.ArrayList
import java.util.HashMap

class GooglePredictionData {

    /**
     *
     * @return
     * The htmlAttributions
     */
    /**
     *
     * @param htmlAttributions
     * The html_attributions
     */
    var htmlAttributions: List<Object> = ArrayList<Object>()
    /**
     *
     * @return
     * The results
     */
    /**
     *
     * @param results
     * The results
     */
    var results: List<Result> = ArrayList()
    /**
     *
     * @return
     * The status
     */
    /**
     *
     * @param status
     * The status
     */
    var status: String? = null
    val additionalProperties: Map<String, Object> = HashMap<String, Object>()

    fun setAdditionalProperty(name: String, value: Object) {
        this.additionalProperties.put(name, value)
    }

}
