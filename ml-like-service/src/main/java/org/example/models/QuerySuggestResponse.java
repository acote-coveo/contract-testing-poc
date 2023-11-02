package org.example.models;

import java.util.List;

public record QuerySuggestResponse(List<QuerySuggestion> suggestions) {
}
