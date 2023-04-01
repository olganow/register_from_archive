package main.java;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.*;

public class XMLParser {
    private  ArrayList<Person> owners = new ArrayList<>();
    private int appNumber;

    public void xmlParser(String path) {
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("room_record");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    appNumber = Integer.parseInt(eElement.getElementsByTagName("name_apartment").item(0).getTextContent());
                }


                // Here nodeList contains all the nodes with name individual
                NodeList nodeList = doc.getElementsByTagName("right_record");

                // Iterate through all the nodes in NodeList
                // using for loop.
                for (int i = 0; i < nodeList.getLength(); ++i) {
                    String shareInFlat;
                    String userSurname;
                    String userName;
                    String userNPatronymic;
                    Node node = nodeList.item(i);
                    System.out.println("\nNode Name :" + node.getNodeName());
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element tElement = (Element) node;
                        shareInFlat = tElement.getElementsByTagName("share_description").item(0).getTextContent();
                        userSurname = tElement.getElementsByTagName("surname").item(0).getTextContent();
                        userName = tElement.getElementsByTagName("name").item(0).getTextContent();
                        userNPatronymic = tElement.getElementsByTagName("patronymic").item(0).getTextContent();
                        owners.add(new Person(userSurname, userName, userNPatronymic, shareInFlat, appNumber));
                    }
                }

                // Вывод информации о каждом собственнике
                for (Person person : owners) {
                    System.out.println(person);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
