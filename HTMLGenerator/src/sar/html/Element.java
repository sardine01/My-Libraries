package sar.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.IllegalAddException;

import sar.html.exception.DuplicateRootException;
import sar.html.exception.NoDocumentException;
import sar.html.exception.NoParentException;
import sar.html.exception.NoTagException;
import sar.html.exception.NullContentException;
import sar.html.warning.NormalWarnings;
import sar.html.warning.WarnType;

public class Element{

	private org.dom4j.Element element = null;
	private Document document = null;
	private javax.swing.text.html.HTML.Tag tag = null;
	private Element parent = null;
	private List<Element> children;
	private HashMap<javax.swing.text.html.HTML.Attribute, String> attributes;
	private StringBuffer content;

	private Element(){
		children = new LinkedList<Element>();
		attributes = new HashMap<javax.swing.text.html.HTML.Attribute, String>();
	}

	public static Element initializeRootElement(javax.swing.text.html.HTML.Tag tag, Document document){
		if(document == null){
			try {
				throw new NoDocumentException();
			} catch (NoDocumentException e1) {
				e1.printStackTrace();
			}
			return null;
		}
		if(tag == null){
			try {
				throw new NoTagException();
			} catch (NoTagException e1) {
				e1.printStackTrace();
			}
			return null;
		}
		try{
			org.dom4j.Element element = document.getDom4jDocument().addElement(tag.toString()); 
			Element e = new Element();
			e.element = element;
			e.tag = tag;
			return e;
		}
		catch(IllegalAddException e){
			try {
				throw new DuplicateRootException();
			} catch (DuplicateRootException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return null;
		}
		
	}
	
	public Element createElement(javax.swing.text.html.HTML.Tag tag){
		org.dom4j.Element element = this.getDom4jElement().addElement(tag.toString()); 
		Element e = new Element();
		e.element = element;
		e.document = this.document;
		e.parent = this;
		this.children.add(e);
		e.tag = tag;
		return e;
	}
	
	public Element getParent(){
		if(this.parent == null){
			try {
				throw new NoParentException();
			} catch (NoParentException e) {
				e.printStackTrace();
			}
			return null;
		}
		else{
			return this.parent;
		}
	}
	
	public List<Element> getChildren(){
		if(this.children.isEmpty()){
			new NormalWarnings().warn(WarnType.NO_CHILDREN);
		}
		return this.children;
	}
	
	public HashMap<javax.swing.text.html.HTML.Attribute, String> getAttributes(){
		if(this.attributes.isEmpty()){
			new NormalWarnings().warn(WarnType.NO_ATTRIBUTES);
		}
		return this.attributes;
	}
	
	public List<Element> getBros(){
		Element parent = this.parent;
		if(parent == null){
			List<Element> rootList = new LinkedList<Element>();
			rootList.add(this);
			new NormalWarnings().warn(WarnType.NO_BROTHERS_ROOT);
			return rootList;
		}
		else{
			if(parent.getChildren().size() <= 1){
				new NormalWarnings().warn(WarnType.NO_BROTHERS_NORMAL);
			}
			return parent.children;
		}
	}
	
	public void moveUp(){
		Element parent = this.parent;
		if(parent == null){
			new NormalWarnings().warn(WarnType.CANNOT_MOVE_ROOT);
		}
		else if(parent.getChildren().size() <= 1){
			new NormalWarnings().warn(WarnType.CANNOT_MOVE_ONLY);
		}
		else{
			org.dom4j.Element element = this.parent.getDom4jElement();
			List<org.dom4j.Element> contentList = element.content();
			List<Element> children = this.parent.children;
			for(int i = 0; i < contentList.size(); i++){
				org.dom4j.Element e = contentList.get(i);
				Element _e = children.get(i);
				if(e == this.getDom4jElement()){
					if(i == 0){
						new NormalWarnings().warn(WarnType.CANNOT_MOVE_TOP);
					}
					else{
						org.dom4j.Element temp = contentList.get(i - 1);
						contentList.remove(i);
						contentList.add(i, temp);
						contentList.remove(i - 1);
						contentList.add(i - 1, e);

						Element _temp = children.get(i - 1);
						children.remove(i);
						children.add(i, _temp);
						children.remove(i - 1);
						children.add(i - 1, _e);
						break;
					}
				}
			}
		}
		
	}
	
	public void moveDown(){
		Element parent = this.parent;
		if(parent == null){
			new NormalWarnings().warn(WarnType.CANNOT_MOVE_ROOT);
		}
		else if(parent.getChildren().size() <= 1){
			new NormalWarnings().warn(WarnType.CANNOT_MOVE_ONLY);
		}
		else{
			org.dom4j.Element element = this.parent.getDom4jElement();
			List<org.dom4j.Element> contentList = element.content();
			List<Element> children = this.parent.children;
			for(int i = 0; i < contentList.size(); i++){
				org.dom4j.Element e = contentList.get(i);
				Element _e = children.get(i);
				if(e == this.getDom4jElement()){
					if(i == contentList.size() - 1){
						new NormalWarnings().warn(WarnType.CANNOT_MOVE_BOTTOM);
					}
					else{
						org.dom4j.Element temp = contentList.get(i + 1);
						contentList.remove(i);
						contentList.add(i, temp);
						contentList.remove(i + 1);
						contentList.add(i + 1, e);

						Element _temp = children.get(i + 1);
						children.remove(i);
						children.add(i, _temp);
						children.remove(i+ 1);
						children.add(i + 1, _e);
						break;
					}
				}
			}
		}
		
	}
	
	public void remove(){
		List<Element> eList = this.parent.children;
		for(int i = 0; i < eList.size(); i++){
			Element e = eList.get(i);
			if(e == this){
				org.dom4j.Element parentElement = this.parent.getDom4jElement();
				List<org.dom4j.Element> contentList = parentElement.content();
				for(int j = 0; j < contentList.size(); j++){
					org.dom4j.Element element = contentList.get(j);
					if(element == this.getDom4jElement()){
						contentList.remove(j);
						break;
					}
				}
				eList.remove(i);
				break;
			}
		}
	}
	
	public void attach(Element element){
		List<Element> elementList = this.children;
		List<org.dom4j.Element> eList = this.getDom4jElement().content();
		eList.add(element.getDom4jElement());
		elementList.add(element);
	}
	
	public void addAttribute(javax.swing.text.html.HTML.Attribute attribute, String value){
		org.dom4j.Element element = this.getDom4jElement();
		element.addAttribute(attribute.toString(), value);
		this.attributes.put(attribute, value);
	}
	
	public void addAttributes(HashMap<javax.swing.text.html.HTML.Attribute, String> attributes){
		org.dom4j.Element element = this.getDom4jElement();
		Iterator iter = attributes.entrySet().iterator(); 
		while (iter.hasNext()) { 
		    Map.Entry entry = (Map.Entry) iter.next(); 
		    element.addAttribute(((javax.swing.text.html.HTML.Attribute)entry.getKey()).toString(), (String)entry.getValue());
		    this.attributes.put((javax.swing.text.html.HTML.Attribute)entry.getKey(), (String)entry.getValue());
		} 
	}
	
	public void updateAttribute(javax.swing.text.html.HTML.Attribute attribute, String value){
		org.dom4j.Element element = this.getDom4jElement();
		if(element.attribute(attribute.toString()) == null){
			new NormalWarnings().warn(WarnType.ATTRIBUTE_NOT_FOUND);
			addAttribute(attribute, value);
			return;
		}
		element.attribute(attribute.toString()).setValue(value);
		this.attributes.put(attribute, value);
	}
	
	public void removeAttribute(javax.swing.text.html.HTML.Attribute attribute){
		org.dom4j.Element element = this.getDom4jElement();
		if(!element.remove(element.attribute(attribute.toString()))){
			new NormalWarnings().warn(WarnType.ATTRIBUTE_NOT_FOUND);
		}
		this.attributes.remove(attribute);
	}
	
	public void setContent(String content){
		this.content = new StringBuffer();
		this.content.append(content);
		this.getDom4jElement().setText(this.content.toString());
	}
	
	public void appendContent(String content){
		if(this.content == null){
			this.content = new StringBuffer();
		}
		this.content.append(content);
		this.getDom4jElement().setText(this.content.toString());
	}
	
	public void clearContent(){
		this.getDom4jElement().clearContent();
		this.getDom4jElement().addText("");
		this.content = null;
	}
	
	public void splitElement(){
		if(this.getDom4jElement().hasContent()){
			new NormalWarnings().warn(WarnType.IS_COUPLED);
			return;
		}
		this.getDom4jElement().setText("");
		this.content = new StringBuffer();
	}
	
	public void fuseElement(){
		if(!this.getDom4jElement().hasContent()){
			new NormalWarnings().warn(WarnType.IS_SINGLED);
			return;
		}
		new NormalWarnings().warn(WarnType.WILL_BE_CLEARED);
		this.getDom4jElement().clearContent();
		this.content = null;
	}
	
	public org.dom4j.Element getDom4jElement(){
		return element;
	}
	
	public Document getDocument() {
		return document;
	}
	
	public Element getRootElement(){
		return this.document.getRootElement();
	}
	
	public String getElementAsString(){
		return this.getDom4jElement().asXML();
	}
	
	public StringBuffer getContent(){
		if(this.content == null){
			new NullContentException().printStackTrace();
		}
		return this.content;
	}
	
	public String getContentAsString(){
		try{
			return this.content.toString();
		}
		catch(NullPointerException e){
			new NullContentException().printStackTrace();
			e.printStackTrace();
			return null;
		}
	}

	public void setDocument(Document document) {
		this.document = document;
	}
}
