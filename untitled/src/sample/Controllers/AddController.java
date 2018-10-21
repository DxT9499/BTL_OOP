package sample.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.Models.Services;
import sample.Models.Word;

public class AddController
{
        @FXML private TextField word;
        @FXML private TextField pronounce;
        @FXML private TextField meaning;
        @FXML private Label info;

        public void addWord()
        {
            if (word.getText().equals(""))
            {
                info.setText("Word field can't be empty.");
                return;
            }
            if (!Services.getInstance().Is_Connected())
            {
                Services.getInstance().Connect_to_Db();
            }
            if (!Services.getInstance().Is_LoadData())
            {
                Services.getInstance().Get_Data_From_DB();
            }
            if (Services.getInstance().isContain(word.getText()))
            {
                info.setText("Word has been in Dictionary already.");
            }
            else {
                info.setText("Updating data base...");
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        Services.getInstance().Insert_To_DB(new Word(word.getText(),meaning.getText(),pronounce.getText()));
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                info.setText("Your word has been updated into database.");
                            }
                        });
                    }
                };

                new Thread(task).start();
            }
        }
    }
