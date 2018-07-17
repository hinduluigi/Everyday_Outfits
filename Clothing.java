
public abstract class Clothing {
	// variables
	private String fileDirectory;
	private boolean washable;
	private String type;

	// constructor
	public Clothing(String file, boolean washable, String type) {
		this.washable = washable;
		fileDirectory = file;
		this.type = type;
	}

	// getters and setters
	public String getFileDirectory() {
		return fileDirectory;
	}

	public void setFileDirectory(String fileDirectory) {
		this.fileDirectory = fileDirectory;
	}

	public boolean isWashable() {
		return washable;
	}

	public void setWashable(boolean washable) {
		this.washable = washable;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
