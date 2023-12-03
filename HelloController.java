package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;

public class HelloController {

    @FXML
    private Text winnerText;

    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;

    @FXML
    private Button start;
    @FXML
    private Button compvcomp;
    @FXML
    private Button humvhum;
    @FXML
    private Button compvhum;

    private List<Button> buttons;
    private int playerTurn = 0;
    private boolean gameStarted = false;

    @FXML
    public void initialize() {
        buttons = Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9);
        buttons.forEach(this::setupButton);
    }

    private void setupButton(Button button) {
        button.setOnAction(event -> {
            if (gameStarted) {
                return;
            }
            start.setDisable(false);
            Button selectedButton = (Button) event.getSource();
            selectedButton.setStyle("-fx-text-fill: red;");
            setPlayerSymbol(selectedButton);
            checkGameIsOver();
        });
    }

    @FXML
    void startGame(ActionEvent event) {
        compvcomp.setDisable(true);
        humvhum.setDisable(true);
        compvhum.setDisable(true);

        Arrays.asList(compvcomp, humvhum, compvhum).forEach(button -> button.setStyle(""));

        gameStarted = true;
    }

    @FXML
    void restartGame(ActionEvent event) {
        buttons.forEach(this::resetButton);
        winnerText.setText("");
        Arrays.asList(compvcomp, humvhum, compvhum).forEach(button -> button.setDisable(false));
        start.setDisable(true);
        playerTurn = 0;
        gameStarted = false;
    }


    private void resetButton(Button button) {
        button.setDisable(false);
        button.setText("");
        button.setStyle("");
    }

    private void setPlayerSymbol(Button button) {
        button.setText(playerTurn % 2 == 0 ? "X" : "O");
        playerTurn++;
    }

    private void checkGameIsOver() {
        for (int a = 0; a < 8; a++) {
            String line = switch (a) {
                case 0 -> button1.getText() + button2.getText() + button3.getText();
                case 1 -> button4.getText() + button5.getText() + button6.getText();
                case 2 -> button7.getText() + button8.getText() + button9.getText();
                case 3 -> button1.getText() + button5.getText() + button9.getText();
                case 4 -> button3.getText() + button5.getText() + button7.getText();
                case 5 -> button1.getText() + button4.getText() + button7.getText();
                case 6 -> button2.getText() + button5.getText() + button8.getText();
                case 7 -> button3.getText() + button6.getText() + button9.getText();
                default -> null;
            };

            if (line.equals("XXX")) {
                winnerText.setText("Player X has won!");
                endGame();
            } else if (line.equals("OOO")) {
                winnerText.setText("Player O has won!");
                endGame();
            }
        }
        if (isBoardFull()) {
            winnerText.setText("It's a tie!");
            endGame();
        }
    }

    private boolean isBoardFull() {
        return buttons.stream().noneMatch(button -> button.getText().isEmpty());
    }

    private void endGame() {
        buttons.forEach(button -> button.setDisable(true));
    }
}
