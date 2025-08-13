package com.example.ex19;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnParse;
    ListView lv;
    ArrayList<String> myList;
    ArrayAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnParse = findViewById(R.id.btnParse);
        lv = findViewById(R.id.lv1);
        myList = new ArrayList<>();
        myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,myList);
        lv.setAdapter(myAdapter);
        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseXML();
            }
            private void parseXML(){
                try {
                    InputStream myInput = getAssets().open("employee.xml");
                    XmlPullParserFactory fc = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = fc.newPullParser();
                    parser.setInput(myInput,null);
                    int eventType = parser.getEventType();
                    String nodeName;
                    String datashow="";
                    while (eventType!=XmlPullParser.END_DOCUMENT)
                    {
                        eventType = parser.next();
                        switch (eventType)
                        {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                nodeName=parser.getName();
                                if(nodeName.equals("employee")){
                                    datashow += parser.getAttributeValue(null, "id") + "-";
                                    datashow += parser.getAttributeValue(null, "title") + "-";
                                } else if (nodeName.equals("name")) {
                                    datashow += parser.getText()+"-";
                                } else if (nodeName.equals("phone")) {
                                    datashow += parser.nextText();
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                nodeName=parser.getName();
                                if(nodeName.equals("employee")){
                                    myList.add(datashow);
                                    datashow="";
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                    myAdapter.notifyDataSetChanged();
                }
                catch (IOException e1){
                    e1.printStackTrace();
                }
                catch (XmlPullParserException e2){
                    e2.printStackTrace();
                }
            }
        });
    }
}
