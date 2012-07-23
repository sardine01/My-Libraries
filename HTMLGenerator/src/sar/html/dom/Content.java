package sar.html.dom;

import java.util.LinkedList;
import java.util.List;

import sar.html.exception.NoParentException;
import sar.html.exception.NullContentException;
import sar.html.warning.NormalWarnings;
import sar.html.warning.WarnType;

public class Content {

	protected org.dom4j.Node node = null;
	protected Document document = null;
	protected Element parent = null;
	
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
	
	public List<Content> getBros(){
		Element parent = this.parent;
		if(parent == null){
			List<Content> rootList = new LinkedList<Content>();
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
			org.dom4j.Element element = (org.dom4j.Element)this.parent.getDom4jNode();
			List<org.dom4j.Node> contentList = element.content();
			List<Content> children = this.parent.children;
			for(int i = 0; i < contentList.size(); i++){
				org.dom4j.Node e = contentList.get(i);
				Content _e = children.get(i);
				if(e == this.getDom4jNode()){
					if(i == 0){
						new NormalWarnings().warn(WarnType.CANNOT_MOVE_TOP);
					}
					else{
						org.dom4j.Node temp = contentList.get(i - 1);
						contentList.remove(i);
						contentList.add(i, temp);
						contentList.remove(i - 1);
						contentList.add(i - 1, e);

						Content _temp = children.get(i - 1);
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
			org.dom4j.Element element = (org.dom4j.Element)this.parent.getDom4jNode();
			List<org.dom4j.Node> contentList = element.content();
			List<Content> children = this.parent.children;
			for(int i = 0; i < contentList.size(); i++){
				org.dom4j.Node e = contentList.get(i);
				Content _e = children.get(i);
				if(e == this.getDom4jNode()){
					if(i == contentList.size() - 1){
						new NormalWarnings().warn(WarnType.CANNOT_MOVE_BOTTOM);
					}
					else{
						org.dom4j.Node temp = contentList.get(i + 1);
						contentList.remove(i);
						contentList.add(i, temp);
						contentList.remove(i + 1);
						contentList.add(i + 1, e);

						Content _temp = children.get(i + 1);
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
		List<Content> eList = this.parent.children;
		for(int i = 0; i < eList.size(); i++){
			Content e = eList.get(i);
			if(e == this){
				org.dom4j.Element parentElement = (org.dom4j.Element)this.parent.getDom4jNode();
				List<org.dom4j.Node> contentList = parentElement.content();
				for(int j = 0; j < contentList.size(); j++){
					org.dom4j.Node node = contentList.get(j);
					if(node == this.getDom4jNode()){
						contentList.remove(j);
						break;
					}
				}
				eList.remove(i);
				break;
			}
		}
	}
	
	public String getContentAsString(){
		return this.getDom4jNode().asXML();
	}
	
	public Document getDocument() {
		return document;
	}
	
	public Element getRootElement(){
		return this.document.getRootElement();
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	
	public org.dom4j.Node getDom4jNode(){
		return node;
	}
}
