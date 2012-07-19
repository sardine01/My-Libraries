package sar.html.warning;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NormalWarnings {

	private String WARNING = "HTML Generator Warning : ";
	
	public void warn(WarnType warnType){
		String currentDateTime = "[" + getCurrentDateTime() + "] ";
		WARNING = currentDateTime + WARNING;
		switch(warnType){
			case NO_CHILDREN : System.out.println(WARNING + "No child element exists. Empty list returned."); break;
			case NO_BROTHERS_ROOT : System.out.println(WARNING + "It's already root element. Root element returned as a list."); break;
			case NO_BROTHERS_NORMAL : System.out.println(WARNING + "It is the only child. Returning itself as a list."); break;
			case CANNOT_MOVE_ROOT : System.out.println(WARNING + "Root element cannot be moved."); break;
			case CANNOT_MOVE_ONLY : System.out.println(WARNING + "Only element cannot be moved."); break;
			case CANNOT_MOVE_TOP : System.out.println(WARNING + "This element is already at the top. It cannot be moved up again."); break;
			case CANNOT_MOVE_BOTTOM : System.out.println(WARNING + "This element is already at the bottom. It cannot be moved down again."); break;
			case NO_ATTRIBUTES : System.out.println(WARNING + "Not any attributes found in this element."); break;
			case ATTRIBUTE_NOT_FOUND : System.out.println(WARNING + "Attribute not found with such a name."); break;
			case IS_SINGLED : System.out.println(WARNING + "It supposed to be a single node. Can't be fused."); break;
			case IS_COUPLED : System.out.println(WARNING + "It supposed to be a coupled node. Can't be splited."); break;
			case WILL_BE_CLEARED : System.out.println(WARNING + "Fusing element, all the content in the element will be CLEARED! "); break;
			default : System.out.print(""); break;
		}
	}
	
	private String getCurrentDateTime() {
		Date utilDate = new java.util.Date();
		try {
			SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return myFmt.format(utilDate);
		} catch (Exception e) {
			return "";
		}
	}
}
