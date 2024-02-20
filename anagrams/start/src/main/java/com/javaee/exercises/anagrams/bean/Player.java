package com.javaee.exercises.anagrams.bean;

import com.javaee.exercises.anagrams.model.Word;
import com.javaee.exercises.anagrams.qualifier.Anagram;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@SessionScoped
@Named
public class Player implements Serializable {

    @Inject
    @Anagram(Anagram.Language.FRENCH)
    Word word;

    int attempt = 1;
    int maxAttempts = 5;
    private String guess;
    private boolean endgame;

    public void check() {
        if (guess.equals(word.getSolution())) {
            endgame = true;
            printMessage("You guessed! Click Restart");
        } else {
            printMessage("Wrong guess!");
            attempt++;
        }
        if (attempt == maxAttempts) {
            printMessage("You lost! Click Restart");
            endgame = true;
        }
    }

    private void printMessage(String string) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, string, string));
    }

    public void restart() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        try {
            ec.redirect(ec.getRequestContextPath() + "/faces/index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isEndgame() {
        return endgame;
    }

    public Word getWord() {
        return word;
    }

    public int getAttempt() {
        return attempt;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }
}
