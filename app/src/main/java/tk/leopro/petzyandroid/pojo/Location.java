
package tk.leopro.petzyandroid.pojo;

import java.util.HashMap;
import java.util.Map;

public class Location {

    private String lat;
    private String lng;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     *     The lat
     */
    public String getLat() {
        return lat;
    }
    /**
     * 
     * @return
     *     The lng
     */
    public String getLng() {
        return lng;
    }
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
