package pers.ksy.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleHtmlTag {
	private String tagName;
	private Map<String, String> attrs = new HashMap<String, String>();
	private List<SimpleHtmlTag> children = new ArrayList<SimpleHtmlTag>();
	private String text = "";

	public SimpleHtmlTag setText(String text) {
		this.text = text;
		return this;
	}

	public SimpleHtmlTag(String tagName) {
		super();
		this.tagName = tagName;
	}

	public SimpleHtmlTag addAttr(String attrName, String attrValue) {
		this.attrs.put(attrName, attrValue);
		return this;
	}
	
	public SimpleHtmlTag addAttrs(Map<String, String> attrs) {
		this.attrs.putAll(attrs);
		return this;
	}

	public SimpleHtmlTag addChild(SimpleHtmlTag child) {
		this.children.add(child);
		return this;
	}

	public String toHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<").append(tagName);
		for (String key : attrs.keySet()) {
			sb.append(" ").append(key).append("=\"");
			sb.append(attrs.get(key)).append("\"");
		}
		sb.append(">");
		for (SimpleHtmlTag child : children) {
			if (null != child)
				text += child.toHtml();
		}
		sb.append(text);
		sb.append("</").append(tagName).append(">");
		return sb.toString();
	}

	public String toString() {
		return toHtml();
	}

	public static void main(String[] args) {
		SimpleHtmlTag divTag = new SimpleHtmlTag("div");
		divTag.addAttr("class", "box_test");
		SimpleHtmlTag inputTag = new SimpleHtmlTag("input");
		inputTag.addAttr("id", "txt_test").addAttr("name", "test")
				.addAttr("value", "textValue");
		divTag.addChild(
				new SimpleHtmlTag("span").addChild(new SimpleHtmlTag("label")
						.setText("测试："))).addChild(inputTag);
		System.out.println(divTag);
	}
}
