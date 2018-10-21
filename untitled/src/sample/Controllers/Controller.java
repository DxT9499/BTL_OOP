package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import sample.Models.SharingServices;

public class Controller
{
    @FXML
    private BorderPane center;
    public void initialize()
    {
        SharingServices.getInstance().setView(center);
        SharingServices.getInstance().set_View_Change("../Views/Search.fxml");
    }

    public void changeToSearch()
    {
        SharingServices.getInstance().set_View_Change("../Views/Search.fxml");
    }
    public void changeToGoogleTrans()
    {
        SharingServices.getInstance().set_View_Change("../views/GoogleTranslate.fxml");
    }
    public void changeToOxford()
    {
        SharingServices.getInstance().set_View_Change("../Views/Oxford.fxml");
    }
    public void changeToWordAdd()
    {
        SharingServices.getInstance().set_View_Change("../Views/Add.fxml");
    }
     public void changeToAbout()
    {
        SharingServices.getInstance().set_View_Change("../Views/About.fxml");
    }
}
