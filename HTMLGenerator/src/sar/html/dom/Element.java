package sar.html.dom;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.IllegalAddException;

import sar.html.exception.DuplicateRootException;
import sar.html.exception.NoDocumentException;
import sar.html.exception.NoTagException;
import sar.html.warning.NormalWarnings;
import sar.html.warning.WarnType;

public class Element extends Content{

	private javax.swing.text.html.HTML.Tag tag = null;
	private HashMap<javax.swing.text.html.HTML.Attribute, String> attributes;
	protected List<Content> children;
	
	private Element(){
		children = new LinkedList<Content>();
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
			org.dom4j.Element element = document.getDom4jDocument().getRootElement(); 
			if(document.getDom4jDocument().getRootElement() == null){
				element = document.getDom4jDocument().addElement(tag.toString()); 
			}
			Element e = new Element();
			e.node = element;
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
		org.dom4j.Element element = ((org.dom4j.Element)this.getDom4jNode()).addElement(tag.toString()); 
		Element e = new Element();
		e.node = element;
		e.document = this.document;
		e.parent = this;
		this.children.add(e);
		e.tag = tag;
		return e;
	}
	
	public Element createElement(org.dom4j.Element element){
		Element e = new Element();
		e.node = element;
		e.document = this.document;
		e.parent = this;
		this.children.add(e);
		e.tag = tag;
		return e;
	}
	
	public Text createText(StringBuffer stringBuffer){
		org.dom4j.Text text = DocumentHelper.createText(stringBuffer.toString()); 
		((org.dom4j.Element)this.getDom4jNode()).add(text);
		Text t = new Text(text);
		t.node = text;
		t.document = this.document;
		t.parent = this;
		this.children.add(t);
		return t;
	}
	
	public Text createText(org.dom4j.Text text){
		Text t = new Text(text);
		t.node = text;
		t.document = this.document;
		t.parent = this;
		this.children.add(t);
		return t;
	}
	
	public void attach(Content content){
		List<Content> elementList = this.children;
		List<org.dom4j.Node> eList = this.getDom4jElement().content();
		eList.add(content.getDom4jNode());
		elementList.add(content);
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
	
	public void clearContent(){
		this.getDom4jElement().clearContent();
		this.getDom4jElement().addText("");
		this.children.clear();
	}
	
	public void splitElement(){
		if(this.getDom4jElement().hasContent()){
			new NormalWarnings().warn(WarnType.IS_COUPLED);
			return;
		}
		this.createText(new StringBuffer(""));
	}
	
	public void fuseElement(){
		if(!this.getDom4jElement().hasContent()){
			new NormalWarnings().warn(WarnType.IS_SINGLED);
			return;
		}
		new NormalWarnings().warn(WarnType.WILL_BE_CLEARED);
		this.getDom4jElement().clearContent();
		this.children.clear();
	}
	
	public List<Content> getChildren(){
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
	
	public String getElementAsString(){
		return this.getDom4jNode().asXML();
	}
	
	public org.dom4j.Element getDom4jElement(){
		return (org.dom4j.Element)node;
	}
	
}
