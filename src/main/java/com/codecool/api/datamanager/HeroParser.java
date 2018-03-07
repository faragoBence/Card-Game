package com.codecool.api.datamanager;

import com.codecool.api.Hero;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HeroParser implements Parser {
    private final List<Hero> heroList;
    private Document document;

    public HeroParser() {
        heroList = new ArrayList<>();
        initDocument();
        readFile();

    }

    public List<Hero> getHeroes() {
        return heroList;
    }

    public void initDocument() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(new File("src/main/resources/Heroes.xml"));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void readFile() {
        Element heroesElement = (Element) document.getElementsByTagName("Heroes").item(0);
        NodeList heroes = heroesElement.getElementsByTagName("Hero");

        Element currentElement;
        Hero currentHero;
        for (int i = 0; i < heroes.getLength(); i++) {
            currentElement = (Element) heroes.item(i);
            currentHero = new Hero(currentElement.getAttribute("name"),
                    currentElement.getAttribute("image"), currentElement.getAttribute("fullImage"));
            heroList.add(currentHero);
        }
    }

}
