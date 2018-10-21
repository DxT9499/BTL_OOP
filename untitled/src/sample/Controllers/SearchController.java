package sample.Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import sample.Models.*;

import java.util.ArrayList;

public class SearchController
{
    @FXML
    private ListView<Word> listView;
    @FXML
    private TextField searchWord;

    @FXML
    private Label wordText;
    @FXML
    private Label pronounceText;
    @FXML
    private Label meaningText;
    @FXML
    private HBox Fix;
    @FXML private ImageView voiceImage;

    // khoi tao
    public void initialize()
    {

        wordText.setText("Loading the data...");

        Fix.setVisible(false);// khong hien lên
        voiceImage.setVisible(false);



        Runnable task = new Runnable()// luong do hoa // luong dũ liệu
        {
            @Override
            public void run()
            {
               // kiểm tra xem kết nối data base chưa
               // chưa thì mình kết nối
                if (!Services.getInstance().Is_Connected())
                {
                    System.out.println("chua kết nối");
                    Services.getInstance().Connect_to_Db();
                }
                if (!Services.getInstance().Is_LoadData())
                {
                    System.out.println("Chua load Data");
                    Services.getInstance().Get_Data_From_DB();
                    if(Services.getInstance().Is_LoadData())
                    {System.out.println("đã load Data");}
                }


                Platform.runLater(new Runnable() {
                    @Override
                    public void run()
                    {
                        System.out.println("ok1");
                        listView.getItems().addAll(Services.getInstance().Get_Dic());
                        wordText.setText("");
                    }
                });

            }
        };

        new Thread(task).start();

        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(0.5)));

        timeLine.setOnFinished(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                //System.out.println("ok4");
                Runnable task = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        System.out.println("ok2");
                        Word temp = new Word(SharingServices.getInstance().text_Value,"","D");
                        System.out.println(temp.word);
                        try {
                            //System.out.println("okem");

                            SharingServices.getInstance().ans_from_Google = GoogleAPI.translate(SharingServices.getInstance().text_Value);

                            temp.meaning = SharingServices.getInstance().ans_from_Google;
                            temp.pronounce = "Taken from Google Translate API";

                        } catch (Exception e)
                        {
                            System.out.println(e.toString());
                            temp.meaning = "Disconnected From Internet";
                            temp.pronounce = "Disconnected From Internet";
                        }

                        Platform.runLater(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                System.out.println("cang nhi");
                                listView.getItems().clear();
                                SharingServices.getInstance().setChoosing_Word(temp);
                                listView.getItems().add(temp);
                                listView.setDisable(false);
                                Fix.setVisible(false);
                            }
                        });
                    }
                };
                new Thread(task).start();
            }
        });

        ChangeListener<Word> viewListener = new ChangeListener<Word>()
        {

            @Override
            public void changed(ObservableValue<? extends Word> observable, Word oldValue, Word newValue)
            {
                System.out.println("ok3");
                if (!((newValue.pronounce.equals("Taken from Google Translate API")
                        ||(newValue.pronounce.equals("Disconnected From Internet")))))
                    Fix.setVisible(true);
                else Fix.setVisible(false);
                voiceImage.setVisible(false);
                Word word = listView.getSelectionModel().getSelectedItem();
                SharingServices.getInstance().setChoosing_Word(word);//
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        voiceImage.setImage(new Image(getClass().getResourceAsStream("../images/voice_image.png")));
                        voiceImage.setDisable(false);
                        try {
                            APIVoice.loadVoice(SharingServices.getInstance().getChoosing_Word().word);
                            SharingServices.getInstance().LoadPlayer();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            voiceImage.setDisable(true);
                            voiceImage.setImage(new Image(getClass().getResourceAsStream("../image/disconnect.png")));
                        }

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                voiceImage.setVisible(true);
                            }
                        });
                    }
                }).start();
                wordText.setText(word.word);
                pronounceText.setText("/" + (word.pronounce.equals("") ? "updating..." : word.pronounce) + "/");
                meaningText.setText(word.meaning);
            }

        };


        listView.getSelectionModel().selectedItemProperty().addListener(viewListener);
//textProperty()

        searchWord.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                System.out.println("ok5");
                listView.getSelectionModel().selectedItemProperty().removeListener(viewListener);
                listView.getItems().clear();
                if (newValue.equals(""))
                {
                    listView.getItems().addAll(Services.getInstance().Get_Dic());
                } else {
                    ArrayList<Word> list= Services.getInstance().search_Dic(newValue);
                    if (!list.isEmpty()) {
                        listView.getItems().addAll(list);

                    } else {
                        SharingServices.getInstance().text_Value = newValue;
                        listView.getItems().add(new Word("searching...","",""));
                        listView.setDisable(true);
// time Line == Search
                        timeLine.playFromStart();
                    }

                }
                listView.scrollTo(0);
                listView.getSelectionModel().selectedItemProperty().addListener(viewListener);
            }
        });
        // time line độ trẽ của việc requesst len google

    }
    public void playVoice()
    {
        SharingServices.getInstance().mediaPlayer.stop();
        SharingServices.getInstance().mediaPlayer.play();
    }
    public void updateWord()
    {
        SharingServices.getInstance().set_View_Change("../views/Update.fxml");
    }
    public void deleteWord()
    {
        wordText.setText("Deleting...");
        meaningText.setText("");
        pronounceText.setText("");
        Fix.setVisible(false);
        Runnable task = new Runnable() {
            @Override
            public void run() {

                if (!Services.getInstance().Is_Connected()) {
                    Services.getInstance().Connect_to_Db();
                }
                if (!Services.getInstance().Is_LoadData()) {
                    Services.getInstance().Get_Data_From_DB();
                }
                Services.getInstance().Delete_In_DB(SharingServices.getInstance().getChoosing_Word().id);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        wordText.setText("");
                    }
                });

            }
        };
        new Thread(task).start();
    }
}
