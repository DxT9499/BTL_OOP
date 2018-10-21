package sample.Models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;

public class SharingServices
{
   public static final SharingServices instance = new SharingServices();
   public BorderPane view;
   public String text_Value;
   public String ans_from_Google;
   public Word choosing_Word;
   public MediaPlayer mediaPlayer;


   public static  SharingServices getInstance() {return instance;}
    public void setView(BorderPane view){this.view=view;}
    public void setChoosing_Word(Word word){this.choosing_Word= word;}
    public Word getChoosing_Word(){return this.choosing_Word;}

    public void set_View_Change(String value)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource(value));
            view.setCenter(root);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            System.out.println("Waiting!!! in loading fxml File");
        }
    }
    public void LoadPlayer()
    {
        String music_File= "../voice.mp3";
        //Media sound = new Media(new File(music_File).toURI().toString());
        javafx.scene.media.Media sound = new javafx.scene.media.Media(new File(music_File).toURI().toString());
        this.mediaPlayer = new MediaPlayer(sound);
    }
}
