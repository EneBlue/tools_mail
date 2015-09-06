package ene.quesle.tools.mail;

import java.util.ArrayList;
import java.util.List;

public class MailContent {
	
	private String from;
	private String to;
	private String content;
	private String title;
	
	private int index = 0;
	
	private List<AdditionalFile>  additionals;
	
	class AdditionalFile{
		// 上传文件的名称
		private String fileName;
		// 上传文件的路径
		private String filePath;
		
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getFilePath() {
			return filePath;
		}
		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}
		
	}
	
	// 添加一个附件
	public void addAdditionalFile(String fileName, String filePath){
		if(this.additionals == null){
			this.additionals = new ArrayList<AdditionalFile>();
		}
		
		AdditionalFile file = new AdditionalFile();
		file.setFileName(fileName);
		file.setFilePath(filePath);
		
		this.additionals.add(file);
	}
	
	// 是否有下一个附件
	public boolean hasNext(){
		if(this.additionals == null || this.additionals.isEmpty()){
			return false;
		}
		
		if(index >= this.additionals.size()){
			return false;
		}
		
		index ++ ;
		return true;
		
	}
	
	//获取文件名
	public String getFileName(){
		return this.additionals.get(index - 1).getFileName();
	}
	
	//获取文件路径
	public String getFilePath(){
		return this.additionals.get(index - 1).getFilePath();
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
