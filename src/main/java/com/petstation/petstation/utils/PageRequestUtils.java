package com.petstation.petstation.utils;

import com.petstation.petstation.dtos.RequestListDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageRequestUtils {

    public static PageRequest createPageRequest(RequestListDTO requestListDTO) {
        Sort.Direction direction = Sort.Direction.fromString(requestListDTO.getSortOrder() == 1 ? "ASC" : "DESC");
        return PageRequest.of(
                requestListDTO.getPage(),
                requestListDTO.getRows(),
                Sort.by(direction, requestListDTO.getSortField())
        );
    }

    public static <T> Specification<T> createSpecification(RequestListDTO requestListDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (requestListDTO.getFilters() != null && !requestListDTO.getFilters().isEmpty()) {
                for (Map.Entry<String, String> filter : requestListDTO.getFilters().entrySet()) {
                    String field = filter.getKey();
                    String value = filter.getValue();
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), "%" + value.toLowerCase() + "%"));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
