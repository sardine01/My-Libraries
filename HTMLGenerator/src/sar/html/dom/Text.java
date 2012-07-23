package sar.html.dom;

import org.dom4j.DocumentHelper;

public class Text extends Content {

	private StringBuffer textBuffer;
	
	public Text(org.dom4j.Text text){
		setText(new StringBuffer(text.getText()));
		this.node = text;
	}
	
	public org.dom4j.Text getDom4jText(){
		return (org.dom4j.Text)node;
	}
	
	public void appendText(StringBuffer stringBuffer){
		setText(getText().append(stringBuffer));
	}
	
	public StringBuffer getText() {
		return textBuffer;
	}

	public void setText(StringBuffer stringBuffer) {
		this.textBuffer = stringBuffer;
	}

}
