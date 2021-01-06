package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${trello.api.username}")
    private String trelloApiUsername;

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    public List<TrelloBoardDto> getTrelloBoards() {
//        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(trelloApiEndpoint+"/members/adrianmienkowski/boards"+"?key="+trelloAppKey+"&token="+trelloToken, TrelloBoardDto[].class);
        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(urlBuilder(), TrelloBoardDto[].class);

        return Optional.ofNullable(boardsResponse).map(Arrays::asList).orElseGet(ArrayList::new);
    }

    private URI urlBuilder() {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint+"/members/"+trelloApiUsername+"/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id").build().encode().toUri();
    }
}
