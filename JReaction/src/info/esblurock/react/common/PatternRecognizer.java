package info.esblurock.react.common;

public class PatternRecognizer {

	String regExpressionInteger = "(?<![-.])\\b[0-9]+\\b(?!\\.[0-9])";
	String regExpressionDecimal = "/^\\d*\\.?\\d*$/";
	String regExpressionFloating = "(\\+|-)?([0-9]+\\.?[0-9]*|\\.[0-9]+)([eE](\\+|-)?[0-9]+)?";
	String regExpressionNumberChar = "^[0-9]*[a-zA-Z].*";
	
	String regExpressionLetterChar = "[a-zA-Z]";
	String regExpressionFirstNumberChar = "^[0-9].*";
	
	public PatternRecognizer() {
	}
	
	public boolean stringIsAnInteger(String text) {
		return text.matches(regExpressionInteger);
	}
	
	public boolean stringAsDecimal(String text) {
		return text.matches(regExpressionDecimal);
	}
	
	public boolean stringAsNumber(String text) {
		return text.matches(regExpressionFloating);
	}
	
	public boolean nonIntegerFloat(String text) {
		return text.matches(regExpressionFloating) && !text.matches(regExpressionInteger);
	}
	
	public boolean numberFollowedByCharacter(String text) {
		boolean ans = false;
		if(stringIsAnInteger(text)) 
			ans = false;
		else
			ans = text.matches(regExpressionFirstNumberChar);
		return ans;
	}
	public int positionOfFirstLetter(String text) {
		int pos = -1;
		if(text.matches(regExpressionFirstNumberChar)) {
			pos = 1;
			while(text.substring(pos).matches(regExpressionFirstNumberChar)) {
				pos++;
			}
		}
		return pos;
	}
	
}
