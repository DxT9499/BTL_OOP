package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class GoogleTransController
{
    @FXML
    private WebView webView;
    public void initialize() {
        WebEngine web = webView.getEngine();
        web.load("https://translate.google.com/?hl=vi");
    }
}
