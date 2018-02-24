package com.codecool.api.datamanager;

import com.codecool.api.Card;
import com.codecool.api.MagicCard;
import com.codecool.api.Minion;
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

// XML Structure


public class CardParser implements Parser {
    private final String xmlPath;
    private Document document;
    private final List<Card> cards = new ArrayList<>();
    private final List<Minion> minions = new ArrayList<>();
    private final List<MagicCard> magicCards = new ArrayList<>();


    // Constructor(s)
    public CardParser(String xmlPath) {
        this.xmlPath = xmlPath;
        initDocument();
        readFile();
    }


    // Getter(s)
    public List<Card> getCards() {
        return cards;
    }

    public List<Minion> getMinions() {
        return minions;
    }

    public List<MagicCard> getMagicCards() {
        return magicCards;
    }

    // Method(s)
    public void initDocument() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(new File(xmlPath));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void readFile() {
        Element minionsElement = (Element) document.getElementsByTagName("Minions").item(0);
        NodeList minions = minionsElement.getElementsByTagName("Minion");

        Element currentElement;
        Minion currentMinion;
        for (int i = 0; i < minions.getLength(); i++) {
            currentElement = (Element) minions.item(i);
            currentMinion = new Minion(currentElement.getAttribute("name"),
                    Integer.parseInt(currentElement.getAttribute("health")),
                    currentElement.getAttribute("description"),
                    Integer.parseInt(currentElement.getAttribute("manacost")),
                    Integer.parseInt(currentElement.getAttribute("attack")));
            cards.add(currentMinion);
            this.minions.add(currentMinion);
        }

        Element magicCardsElement = (Element) document.getElementsByTagName("MagicCards").item(0);
        NodeList magicCards = magicCardsElement.getElementsByTagName("MagicCard");

        MagicCard currentMagicCard;
        for (int i = 0; i < magicCards.getLength(); i++) {
            currentElement = (Element) magicCards.item(i);
            currentMagicCard = new MagicCard(currentElement.getAttribute("name"),
                    1,
                    currentElement.getAttribute("description"),
                    Integer.parseInt(currentElement.getAttribute("manacost")));
            cards.add(currentMagicCard);
            this.magicCards.add(currentMagicCard);
        }
    }
}
