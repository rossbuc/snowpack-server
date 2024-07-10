package com.snowpack.snowpack_server.specifications;

import com.snowpack.snowpack_server.models.Aspect;
import com.snowpack.snowpack_server.models.Post;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecification {

    public static Specification<Post> hasElevationGreaterThanEqual(Integer elevation) {
        return (root, query, criteriaBuilder) ->
                elevation == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("elevation"), elevation);
    }

    public static Specification<Post> hasAspect(Aspect aspect) {
        return (root, query, criteriaBuilder) ->
                aspect == null ? null : criteriaBuilder.equal(root.get("aspect"), aspect);
    }

    public static Specification<Post> hasTemperatureGreaterThanEqual(Integer temperature) {
        return (root, query, criteriaBuilder) ->
                temperature == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("temperature"), temperature);
    }

    public static Specification<Post> isSortedByDateTimeDesc(String sortBy) {
        return (root, query, criteriaBuilder) -> {
            if ("date".equals(sortBy)) {
                query.orderBy(criteriaBuilder.desc(root.get("dateTime")));
            }
            return null;
        };
    }
}
