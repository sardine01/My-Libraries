package sar.html;

import sar.html.exception.NoRootException;

public class Document {
	private org.dom4j.Document document;
	private Element rootElement;
	private boolean hasRoot = false;
	
	public Document(javax.swing.text.html.HTML.Tag rootTag){
		this.document = org.dom4j.DocumentHelper.createDocument();
		this.rootElement = createRootElement(rootTag);
	}
	
	private Element createRootElement(javax.swing.text.html.HTML.Tag tag){
		Element e = Element.initializeRootElement(tag, this);
		e.setDocument(this);
		this.hasRoot = true;
		return e;
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
	
	public String getDocumentAsString(){
		return document.getRootElement().asXML();
	}
}
