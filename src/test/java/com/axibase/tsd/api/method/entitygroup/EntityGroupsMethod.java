package com.axibase.tsd.api.method.entitygroup;

import com.axibase.tsd.api.method.BaseMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Shmagrinskiy
 */
public class EntityGroupsMethod extends BaseMethod {

    private static final String METHOD_ENTITY_LIST = "/entity-groups/";
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static WebTarget httpEntitiesApiResource = httpApiResource.path(METHOD_ENTITY_LIST);

    public static Response list(String expression, Map<String, String> tags) {
        Response response = httpEntitiesApiResource
                .queryParam("tags", tags)
                .queryParam("expression", expression)
                .request()
                .get();
        response.bufferEntity();
        return response;
    }

    public static Response list(String expression) {
        Response response = httpEntitiesApiResource
                .queryParam("expression", expression)
                .request()
                .get();
        response.bufferEntity();
        return response;
    }

    public static Response list(Map<String, String> tags) {
        Response response = httpEntitiesApiResource
                .queryParam("tags", tags)
                .request()
                .get();
        response.bufferEntity();
        return response;
    }

    public static Response list() {
        Response response = httpEntitiesApiResource
                .request()
                .get();
        response.bufferEntity();
        return response;
    }


    public static EntityGroupMethod group(String entityGroupName) {
        return new EntityGroupMethod(entityGroupName);
    }


    public static class EntityGroupMethod {
        private WebTarget httpEntityGroupApiResource;
        private WebTarget httpEntityGroupEntitiesApiResource;

        EntityGroupMethod(String entityGroupName) {
            httpEntityGroupApiResource = httpEntitiesApiResource.path(entityGroupName);
            httpEntityGroupEntitiesApiResource = httpEntityGroupApiResource.path("entities");
        }

        public <T> Response update(T query) {
            Response response = httpEntityGroupApiResource
                    .request()
                    .method("PATCH", Entity.json(query));
            response.bufferEntity();
            return response;
        }


        public <T> Response update(T query, String expression, Map<String, String> tags) {
            Response response = httpEntityGroupApiResource
                    .queryParam("expression", expression)
                    .queryParam("tags", tags)
                    .request()
                    .method("PATCH", Entity.json(query));
            response.bufferEntity();
            return response;
        }

        public <T> Response update(T query, String expression) {
            Response response = httpEntityGroupApiResource
                    .queryParam("expression", expression)
                    .request()
                    .method("PATCH", Entity.json(query));
            response.bufferEntity();
            return response;
        }

        public <T> Response update(T query, Map<String, String> tags) {
            Response response = httpEntityGroupApiResource
                    .queryParam("tags", tags)
                    .request()
                    .method("PATCH", Entity.json(query));
            response.bufferEntity();
            return response;
        }


        public <T> Response createOrReplace(T query, String expression, Map<String, String> tags) {
            Response response = httpEntityGroupApiResource
                    .queryParam("expression", expression)
                    .queryParam("tags", tags)
                    .request()
                    .put(Entity.json(query));
            response.bufferEntity();
            return response;
        }

        public <T> Response createOrReplace(T query, String expression) {
            Response response = httpEntityGroupApiResource
                    .queryParam("expression", expression)
                    .request()
                    .put(Entity.json(query));
            response.bufferEntity();
            return response;
        }

        public <T> Response createOrReplace(T query, Map<String, String> tags) {
            Response response = httpEntityGroupApiResource
                    .queryParam("tags", tags)
                    .request()
                    .put(Entity.json(query));
            response.bufferEntity();
            return response;
        }

        public <T> Response createOrReplace(T query) {
            Response response = httpEntityGroupApiResource
                    .request()
                    .put(Entity.json(query));
            response.bufferEntity();
            return response;
        }

        public <T> Response createOrReplace() {
            Response response = httpEntityGroupApiResource
                    .request()
                    .put(Entity.json(new com.axibase.tsd.api.model.entity.Entity()));
            response.bufferEntity();
            return response;
        }


        public Response get() {
            Response response = httpEntityGroupApiResource
                    .request()
                    .get();
            response.bufferEntity();
            return response;
        }


        public Response delete() {
            Response response = httpEntityGroupApiResource
                    .request()
                    .delete();
            response.bufferEntity();
            return response;
        }


        public Response getEntities() {
            Response response = httpEntityGroupEntitiesApiResource
                    .request()
                    .get();
            response.bufferEntity();
            return response;
        }

        public Response addEntities(List<String> entitiesNames) {
            Response response = httpEntityGroupEntitiesApiResource
                    .path("add")
                    .request()
                    .post(Entity.json(entitiesNames));
            response.bufferEntity();
            return response;
        }

        public Response setEntities(List<String> entitiesNames) {
            Response response = httpEntityGroupEntitiesApiResource
                    .path("set")
                    .request()
                    .post(Entity.json(entitiesNames));
            response.bufferEntity();
            return response;
        }

        public Response deleteEntites(List<String> entitiesNames) {
            Response response = httpEntityGroupEntitiesApiResource
                    .path("delete")
                    .request()
                    .post(Entity.json(entitiesNames));
            response.bufferEntity();
            return response;
        }

    }

}
