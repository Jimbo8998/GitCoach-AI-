package com.gitcoachai.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChallengeService {

    private final List<String> challenges = new ArrayList<>();
    private final List<String> progress = new ArrayList<>();

    public ChallengeService() {
        // Initialize challenges
        challenges.add("Create a new branch");
        challenges.add("Commit changes to the branch");
        challenges.add("Merge the branch into main");
    }

    public List<String> getChallenges() {
        return new ArrayList<>(challenges);
    }

    public void updateProgress(String challenge) {
        if (challenges.contains(challenge) && !progress.contains(challenge)) {
            progress.add(challenge);
        }
    }

    public List<String> getProgress() {
        return new ArrayList<>(progress);
    }
}
