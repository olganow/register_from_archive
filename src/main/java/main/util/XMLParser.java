package main.util;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import main.java.Owner;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XMLParser {
    private List<Owner> owners = new ArrayList<>();
    private int appNumber;
    private double area;

    public List<Owner> getOwners() {
        return owners;
    }

    public void xmlParser(String path) {
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("room_record");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    appNumber = Integer.parseInt(eElement.getElementsByTagName("name_apartment").item(0).getTextContent());
                    area = Double.parseDouble(eElement.getElementsByTagName("area").item(0).getTextContent());
                }

                if (doc.getElementsByTagName("right_record").equals("right_record")) {
                    // Here nodeList contains all the nodes with name individual
                    NodeList nodeList = doc.getElementsByTagName("right_record");

                    // Iterate through all the nodes in NodeList
                    // using for loop.
                    for (int i = 0; i < nodeList.getLength(); ++i) {
                        double shareInFlat;
                        String userSurname;
                        String userName;
                        String userNPatronymic;
                        Node node = nodeList.item(i);
                        //     System.out.println("\nNode Name :" + node.getNodeName());
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element tElement = (Element) node;

                            shareInFlat = NumberUtils.fractionToDouble(tElement.getElementsByTagName("share_description").item(0).getTextContent());
                            userSurname = tElement.getElementsByTagName("surname").item(0).getTextContent();
                            userName = tElement.getElementsByTagName("name").item(0).getTextContent();
                            userNPatronymic = tElement.getElementsByTagName("patronymic").item(0).getTextContent();
                            Owner owner = new Owner(userSurname, userName, userNPatronymic, shareInFlat, appNumber, area);
                            if (owners.isEmpty()) {
                                owners.add(owner);
                            } else {
                                //Проверка того, что если в рееестре собственник повторяется - то его доли складываются
                                for (Owner ownerFromlist : owners) {
                                    if (ownerFromlist.getName().equals(userName) &&
                                            ownerFromlist.getSurname().equals(userSurname) &&
                                            ownerFromlist.getPatronymic().equals(userNPatronymic)) {
                                        double newShareInFlat = ownerFromlist.getShareInFlat() + shareInFlat;
                                        owners.remove(ownerFromlist);
                                        owners.add(new Owner(userSurname, userName, userNPatronymic, newShareInFlat, appNumber, area));
                                        break;
                                    } else {
                                        owners.add(owner);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    //если в документе нет информации о собственнике
                    owners.add(new Owner(null, null, null, 0, appNumber, area));
                }


                // Вывод информации о каждом собственнике
                for (Owner person : owners) {
                    System.out.println(person);
                }
            }
        } catch (SaveException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException | IOException | SAXException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
