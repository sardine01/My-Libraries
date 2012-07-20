package sar.test;

import java.util.List;

import javax.swing.text.html.HTML.Attribute;
import javax.swing.text.html.HTML.Tag;

import sar.html.*;
import sar.html.dom.*;

public class Test1 {
	
	public static void main(String[] args){
		Document d = new Document(Tag.DIV);
		Element e = d.getRootElement();
		Element e0 = e.createElement(Tag.TABLE);
		Element e1 = e.createElement(Tag.TR);
		Text t1 = e1.createText(new StringBuffer("in front of td"));
		Element e2 = e1.createElement(Tag.TD);
		Text t2 = e1.createText(new StringBuffer("behind td"));
		t1.remove();
		t1.moveDown();
		e2.createText(new StringBuffer("in td"));
		
		Document _d = new Document(Tag.DIV);
		Element _e = _d.getRootElement();
		Element _e0 = _e.createElement(Tag.TABLE);
		Element _e1 = _e.createElement(Tag.TR);
		Element _e2 = _e1.createElement(Tag.TD);
		
		System.out.println(e.getElementAsString());
	}
}
