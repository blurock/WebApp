package info.esblurock.react.objects;

public class BaseREACTIONBaseObject {
	private String keyWord;

	public BaseREACTIONBaseObject() {
		this.keyWord = null;
	}
	
	public BaseREACTIONBaseObject(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	public String toString() {
		return keyWord;
	}
}
