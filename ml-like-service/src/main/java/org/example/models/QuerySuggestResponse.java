package org.example.entities;

import java.util.List;

public record QuerySuggestResponse(List<QuerySuggestion> suggestions) {
}
