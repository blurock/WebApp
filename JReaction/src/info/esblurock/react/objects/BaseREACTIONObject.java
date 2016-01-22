package info.esblurock.react.objects;

public class BaseREACTIONObject extends BaseREACTIONBaseObject {
	
	
	BaseREACTIONObjectDescription description;

	public BaseREACTIONObject(String keyword) {
		super(keyword);
		this.description = description = null;
	}
	public BaseREACTIONObject(BaseREACTIONObjectDescription description) {
		super(description.getKeyWord());
		this.description = description;
	}
	public BaseREACTIONObjectDescription getDescription() {
		return description;
	}
	public void setDescription(BaseREACTIONObjectDescription description) {
		this.description = description;
	}
	
	
	
}
