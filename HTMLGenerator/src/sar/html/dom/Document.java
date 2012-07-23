package sar.html.dom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sar.html.exception.NoRootException;

public class Document {
	private org.dom4j.Document document;
	private Element rootElement;
	private boolean hasRoot = false;
	
	public Document(javax.swing.text.html.HTML.Tag rootTag){
		this.document = org.dom4j.DocumentHelper.createDocument();
		this.rootElement = createRootElement(rootTag);
		if(!((org.dom4j.Element)rootElement.getDom4jNode()).content().isEmpty()){
			assembleDocument(rootElement);
		}
	}
	
	public Document(org.dom4j.Document document){
		this.document = document;
		org.dom4j.Element rootElement = document.getRootElement();
		javax.swing.text.html.HTML.Tag rootTag = javax.swing.text.html.HTML.getTag(rootElement.getName());
		this.rootElement = createRootElement(rootTag);
		if(!((org.dom4j.Element)this.rootElement.getDom4jNode()).content().isEmpty()){
			assembleDocument(this.rootElement);
		}
	}
	
	private Element createRootElement(javax.swing.text.html.HTML.Tag tag){
		Element e = Element.initializeRootElement(tag, this);
		e.setDocument(this);
		this.hasRoot = true;
		return e;
	}
	
	private void assembleDocument(Element rootElement){
		org.dom4j.Element e = ((org.dom4j.Element)rootElement.getDom4jNode());
		List<org.dom4j.Element> childList = e.content();
		for(org.dom4j.Node child : childList){
			if(child instanceof org.dom4j.tree.DefaultElement){
				org.dom4j.Element child_e = (org.dom4j.Element)child;
				HashMap<javax.swing.text.html.HTML.Attribute, String> attributes = new HashMap<javax.swing.text.html.HTML.Attribute, String>();
				String content = new String(child_e.getText());
				
				List<org.dom4j.Attribute> aList = child_e.attributes();
				for(org.dom4j.Attribute a : aList){
					attributes.put(javax.swing.text.html.HTML.getAttributeKey(a.getName()), a.getValue());
				}
				
				Element childElement = rootElement.createElement(child_e);
				rootElement.children.add(childElement);
				childElement.addAttributes(attributes);
				if(!child_e.content().isEmpty()){
					assembleDocument(childElement);
				}
			}
			else if(child instanceof org.dom4j.tree.DefaultText){
				org.dom4j.Text child_t = (org.dom4j.Text)child;
				Text childText = rootElement.createText(child_t);
				rootElement.children.add(childText);
			}
		}
	}
	
	public boolean hasRoot(){
		return this.hasRoot;
	}
	
	public Element getRootElement(){
		return this.rootElement;
	}
	
	public org.dom4j.Document getDom4jDocument(){
		return document;
	}
	
	public void printString(){
		System.out.println(document.getRootElement().asXML());
	}
	
	public String getAsString(){
		return document.getRootElement().asXML();
	}
	
	public StringBuffer getAsStringBuffer(){
		return new StringBuffer(document.getRootElement().asXML());
	}
}
