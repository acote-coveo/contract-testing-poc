package org.example.repositories;

import org.example.models.QuerySuggestion;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class QuerySuggestRepository {

    public List<QuerySuggestion> getSuggestions(String query, int count) {
        if (query.equals("foo")) {
            return IntStream.rangeClosed(1, count)
                    .boxed()
                    .map(i -> new QuerySuggestion("foo" + i, 100d - i))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}
