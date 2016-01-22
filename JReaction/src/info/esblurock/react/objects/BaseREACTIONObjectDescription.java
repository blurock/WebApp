package info.esblurock.react.objects;

public class BaseREACTIONObjectDescription extends BaseREACTIONBaseObject {
	
	//Long name (no limit, but basically one line, less than 80-130 characters), 
	private String FullName;
	
	// Short description (1-100 characters) used for a short summary explanation
	private String ShortDescription;
	
	// Full description
	private String FullDescription;

	public BaseREACTIONObjectDescription() {
		super();
		FullName =null;
		ShortDescription = null;
		FullDescription = null;
	}	
	public BaseREACTIONObjectDescription(String keyword) {
		super(keyword);
		FullName =null;
		ShortDescription = null;
		FullDescription = null;
	}

	public BaseREACTIONObjectDescription(String keyword, String fullName,
			String shortDescription, String fullDescription) {
		super(keyword);
		FullName = fullName;
		ShortDescription = shortDescription;
		FullDescription = fullDescription;
	}
	public String getFullName() {
		return FullName;
	}
	public void setFullName(String fullName) {
		FullName = fullName;
	}
	public String getShortDescription() {
		return ShortDescription;
	}
	public void setShortDescription(String shortDescription) {
		ShortDescription = shortDescription;
	}
	public String getFullDescription() {
		return FullDescription;
	}
	public void setFullDescription(String fullDescription) {
		FullDescription = fullDescription;
	}

	
}
