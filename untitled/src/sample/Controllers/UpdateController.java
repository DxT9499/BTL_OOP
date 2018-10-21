package sample.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.Models.Services;
import sample.Models.SharingServices;
import sample.Models.Word;

public class UpdateController
{
    @FXML
    private TextField word;
    @FXML private TextField pronounce;
    @FXML private TextArea Mean;
    @FXML private Label info;
    private Word wordObj;
    private int id;
    public void initialize()
    {

            this.wordObj = SharingServices.getInstance().getChoosing_Word();
            this.id = wordObj.id;
            word.setText(wordObj.word);
            pronounce.setText(wordObj.pronounce);
            Mean.setText(wordObj.meaning);

    }
    public void UpdateWord()
    {
        if(word!=null) {
            info.setText("Modifying word...");
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    Services.getInstance().Update_To_DB(id, wordObj.word, pronounce.getText(), Mean.getText());
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            info.setText("Word has been Updated! ");
                        }
                    });
                }
            };

            new Thread(task).start();
        }


    }
}
