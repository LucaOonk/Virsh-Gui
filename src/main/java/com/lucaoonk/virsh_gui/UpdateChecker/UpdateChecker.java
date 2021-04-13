package com.lucaoonk.virsh_gui.UpdateChecker;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.lucaoonk.virsh_gui.Backend.Objects.Context;

import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class UpdateChecker extends SwingWorker{


    private static final String urlToCheck = "https://api.github.com/repos/LucaOonk/Virsh-Gui/releases/latest";
    private Context context;


    public UpdateChecker(Context context){

        super();
        this.context = context;

        try {
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Boolean isNewewVersionAvailable(){
        return this.doInBackground();

    }

    @Override
    protected Boolean doInBackground() {

        System.out.println("Current: "+context.getVersion());
        try {
            HttpResponse<JsonNode> response = Unirest.get(urlToCheck).asJson();
            JsonNode responseBody = response.getBody();
            System.out.println(responseBody);

        if (response.isSuccess()) {

        System.out.println(responseBody);

        String latestVersion = responseBody.getObject().get("tag_name").toString();

        System.out.println("Latest version: "+ latestVersion);

        if(Context.getVersion().equals(latestVersion)){

            return false;

        }else{
            
            return true;


        }

    } else {
        System.out.println("Connection Failed");
        return null;
     }

    }catch(Exception e){

    }
        return null;
    }
}
