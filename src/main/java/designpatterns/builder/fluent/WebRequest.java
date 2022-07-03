package designpatterns.builder.fluent;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class WebRequest {
    private URI uri;
    private String httpMethod;
    private Map<String, String> queryParams;
    private Map<String, String> headerParams;
    private Map<String, Object> body;
    private String contentType;

    private WebRequest(RequestBuilder builder) {
        //Set values accordingly
    }

    public enum HttpMethod {
        GET, POST, PUT, DELETE;
    }

    interface UriInfo {
        MethodInfo url(String url);
    }

    interface MethodInfo {
        OptionalInfo method(HttpMethod method);
    }

    interface OptionalInfo {
        OptionalInfo contentType(String contentType);

        OptionalInfo addHeaderParam(String key, String value);

        OptionalInfo addQueryParam(String key, String value);

        OptionalInfo addBodyItem(String key, Object obj);

        CreationInfo then();
    }

    interface CreationInfo {
        WebRequest build();
    }

    public static class RequestBuilder implements UriInfo, MethodInfo, OptionalInfo, CreationInfo {
        private String url;
        private String httpMethod;
        private Map<String, String> queryParams;
        private Map<String, String> headerParams;
        private Map<String, Object> body;
        private String contentType;

        public RequestBuilder() {
            queryParams = new HashMap<>();
            headerParams = new HashMap<>();
            body = new HashMap<>();
        }


        @Override
        public MethodInfo url(String url) {
            this.url = url;
            return this;
        }

        @Override
        public OptionalInfo method(HttpMethod method) {
            return null;
        }

        @Override
        public OptionalInfo contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        @Override
        public OptionalInfo addHeaderParam(String key, String value) {
            headerParams.put(key, value);
            return this;
        }

        @Override
        public OptionalInfo addQueryParam(String key, String value) {
            queryParams.put(key, value);
            return this;
        }

        @Override
        public OptionalInfo addBodyItem(String key, Object obj) {
            body.put(key, obj);
            return this;
        }


        @Override
        public CreationInfo then() {
            return this;
        }

        @Override
        public WebRequest build() {
            return new WebRequest(this);
        }
    }
}
