package org.example.controllers;

import org.example.models.QuerySuggestRequest;
import org.example.models.QuerySuggestResponse;
import org.example.repositories.QuerySuggestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class QuerySuggestController {
    @Autowired
    QuerySuggestRepository repository;

    @PostMapping("/querySuggest")
    @ResponseBody
    QuerySuggestResponse execute(@RequestBody QuerySuggestRequest request) {
        var suggestions = repository.getSuggestions(request.query(), request.count());

        return new QuerySuggestResponse(suggestions);
    }
}
