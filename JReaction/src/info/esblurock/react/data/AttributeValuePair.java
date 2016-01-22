package info.esblurock.react.data;

import java.io.Serializable;

public class AttributeValuePair implements Serializable {
	private static final long serialVersionUID = 1L;
	private String propertyValue;
	private String attributeValue;
	
	public AttributeValuePair(String property, String attribute) {
		propertyValue = property;
		attributeValue = attribute;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
}
