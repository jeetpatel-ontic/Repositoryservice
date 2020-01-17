package common;

import java.util.HashMap;
import java.util.Map;

public class UnifiedQueryType {
    private Map<String, Object> params;

    public Map<String, Object> getParams() {
        return params;
    }

    private UnifiedQueryType(QueryBuilder builder) {
        this.params = builder.params;
    }

    //Builder Class
    public static class QueryBuilder {

        // required parameters
        private Map<String, Object> params;

        public QueryBuilder() {
            params = new HashMap<String, Object>();
        }

        public QueryBuilder addQueryParamPair(String key, Object value) {
            this.params.put(key, value);
            return this;
        }

        public UnifiedQueryType build() {
            return new UnifiedQueryType(this);
        }

    }

    @Override
    public String toString() {
        StringBuilder queryString = new StringBuilder();
        for(Map.Entry<String, Object> entry : this.params.entrySet()){
            queryString.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
        }
        return queryString.toString();
    }
}
