# Fluent Builder Pattern

Whenever you need ensure users are building in a certain order or provide values for certain fields.

- Simply make builder methods return the new Interface type
    - Can make the builder implement each interface to continue passing same builder

```java
//Stream API, Mockito, etc use this

public class WebRequest {
    private WebRequest();

    public WebRequest(RequestBuilder builder) {
        //set values..
    }

    //other code...
    interface UriInfo {
        MethodInfo url(String url);
    }

    interface MethodInfo {
        Finish method(String method);
    }

    interface Finish {
        WebRequest build();
    }

    public static class RequestBuilder implements UriInfo, MethodInfo, Finish {
        private RequestBuilder() {
        }

        public static UriInfo from() {
            return new RequestBuilder();
        }

        @Override
        public MethodInfo url(String url) {
            //do sth
            return this;
        }

        @Override
        public OptionalInfo method(String method) {
            //do sth
            return this;
        }

        @Override
        public WebRequest build() {
            return new WebRequest(this);
        }
    }
}

public class Example {
    public static void main(String[] args) {
        //Forcing users to build in a specific manner
        WebRequest request = WebRequest.RequestBuilder.from()
                .url("www.website.com")
                .method("POST")
                .build();
    }
}
```