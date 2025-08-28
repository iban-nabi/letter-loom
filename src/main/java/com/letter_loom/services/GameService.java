package com.letter_loom.services;

import com.letter_loom.entities.Game;
import com.letter_loom.entities.User;
import com.letter_loom.objects.GameState;
import com.letter_loom.objects.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class GameService {
    //Services
    GameStateService gameStateService;

    //for letter generator
    private static final String vowels = "AEIOU";
    private static final String consonants = "BCDFGHJKLMNPQRSTVWXYZ";
    private static final int consonantLength = 6;
    private static final int vowelsLength = 4;
    private static final Random random = new Random();

    @Value("${dictionary.apiUrl}")
    private String dictionaryApiUrl;

    public List<Character> generateLetters(){
        List<Character> letters = new ArrayList<Character>();

        //generate random vowels
        for(int i = 0; i < vowelsLength; i++) {
            letters.add(vowels.charAt(random.nextInt(vowels.length())));
        }

        // generate random consonants
        for(int i = 0; i < consonantLength; i++) {
            letters.add(consonants.charAt(random.nextInt(consonants.length())));
        }

        Collections.shuffle(letters);
        return letters;
    }

    public boolean verifyWord(String word) {
        RestTemplate restTemplate = new RestTemplate();
        String url = dictionaryApiUrl + "/" + word;
        String response = restTemplate.getForObject(url, String.class);
        return response != null && !response.contains("No Definitions Found") && !response.contains("\"title\"");
    }

    public boolean verifyDuplicate(Long id, String word){
        GameState gameState = gameStateService.getGameState(id);
        return gameState.getSubmittedWords().add(word);
    }

    public void generatePoints(){}
    public void identifyWinner(){}
    public void startTimer(){}
    public void stopTimer(){}
}
