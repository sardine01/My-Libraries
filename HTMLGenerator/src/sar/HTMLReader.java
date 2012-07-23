package sar;

import java.io.*;
import java.util.*;

import javax.swing.text.html.HTML.Tag;

import org.dom4j.io.*;

import sar.html.dom.*;

public class HTMLReader {

	public static void main(String[] args) {
		HTMLReader hr = new HTMLReader();
		try {
			org.dom4j.Document doc = hr.getDocumentFromXMLFile("C:\\WorkSpaces\\WorkSpace\\HTMLGenerator\\src\\sar\\test\\template.xml");
			org.dom4j.Element root = doc.getRootElement();
			Document document = new Document(doc);
			StringBuffer html = document.getAsStringBuffer();
			document.printString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private org.dom4j.Document getDocumentFromXMLFile(String filePath) throws Exception{
		File f = new File(filePath);
		SAXReader reader = new SAXReader();
		return reader.read(f);
	}
	
	private Document convertDocument(org.dom4j.Document document){
		return new Document(document);
	}
	
}
