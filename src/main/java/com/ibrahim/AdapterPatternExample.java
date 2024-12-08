package com.ibrahim;

import org.json.JSONObject;
import org.json.XML;

import java.nio.file.Files;
import java.nio.file.Paths;

// Step 1: Define the Target Interface (JSONProcessor)
interface JSONProcessor {
    JSONObject processJSON();
}

// Step 2: Define the Adaptee (XMLProcessor)
class XMLProcessor {
    private String xmlFilePath;

    public XMLProcessor(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
    }

    public String getXMLData() throws Exception {
        return new String(Files.readAllBytes(Paths.get(xmlFilePath)));
    }
}

// Step 3: Create the Adapter
class XMLToJSONAdapter implements JSONProcessor {
    private XMLProcessor xmlProcessor;

    public XMLToJSONAdapter(XMLProcessor xmlProcessor) {
        this.xmlProcessor = xmlProcessor;
    }

    @Override
    public JSONObject processJSON() {
        try {
            // Get the XML data
            String xmlData = xmlProcessor.getXMLData();
            // Convert XML to JSON
            return XML.toJSONObject(xmlData);
        } catch (Exception e) {
            throw new RuntimeException("Error processing XML to JSON", e);
        }
    }
}

// Step 4: Main Application
public class AdapterPatternExample {
    public static void main(String[] args) {
        try {
            // Step 4.1: Create an XML Processor (Adaptee)
            XMLProcessor xmlProcessor = new XMLProcessor("src/main/resources/config.xml");

            // Step 4.2: Create an Adapter
            JSONProcessor jsonProcessor = new XMLToJSONAdapter(xmlProcessor);

            // Step 4.3: Use JSON Processor
            JSONObject jsonData = jsonProcessor.processJSON();

            // Output the converted JSON data
            System.out.println("Converted JSON Data:");
            System.out.println(jsonData.toString(4)); // Pretty print JSON
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
