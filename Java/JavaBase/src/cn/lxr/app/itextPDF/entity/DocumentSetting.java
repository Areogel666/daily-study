package cn.lxr.app.itextPDF.entity;


public class DocumentSetting {

	private String title;//标题
	private String author;//作者
	private String subject;//主题
	private String keywords;//关键字
	private boolean isCreationDate;
	private String creator;//应用程序
	
	public DocumentSetting() {
		this.isCreationDate = true;
	}
	
	public DocumentSetting(String author, String subject, String creator, boolean isCreationDate) {
		this.author = author;
		this.subject = subject;
		this.creator = creator;
		this.isCreationDate = isCreationDate;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public boolean isCreationDate() {
		return isCreationDate;
	}
	public void setCreationDate(boolean isCreationDate) {
		this.isCreationDate = isCreationDate;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	
}
