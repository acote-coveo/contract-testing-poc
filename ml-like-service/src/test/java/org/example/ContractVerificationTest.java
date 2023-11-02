package org.example;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.QuerySuggestion;
import org.example.repositories.QuerySuggestRepository;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Provider("ml-querysuggest")
@PactBroker
public class ContractVerificationTest {

    @MockBean
    private QuerySuggestRepository querySuggestRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }


    @State("with suggestions")
    public void withSuggestion(Map<String,Object> params) throws IOException {
        var count = (BigInteger)params.get("count");
        var fromFile = getSuggestionsFromFile("suggestions.json");
        var newSuggestions = IntStream.rangeClosed(1, count.intValue())
                .boxed()
                .map(i -> fromFile.get(0))
                .toList();
        when(querySuggestRepository.getSuggestions(anyString(), anyInt()))
                .thenReturn(newSuggestions);
    }

    @State("without suggestion")
    public void withoutSuggestion() throws IOException {
        when(querySuggestRepository.getSuggestions(anyString(), anyInt()))
                .thenReturn(getSuggestionsFromFile("noSuggestions.json"));
    }

    private List<QuerySuggestion> getSuggestionsFromFile(String filename) throws IOException {
        URL resource = getClass().getClassLoader().getResource(filename);
        return objectMapper.readValue(resource, new TypeReference<>() {
        });
    }
}
