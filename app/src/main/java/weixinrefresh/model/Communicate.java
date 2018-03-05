package weixinrefresh.model;

import java.io.Serializable;

public class Communicate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String communicateUrl;
	private String communicateType;
	private String communicateName;
	private String type;
	private String communicateTime;
	private String content;
	private String smallImageUrlList;
	private String imageUrlList;
	private String commentList;
	private String good;
	private String goodNameList;
	private String communicateTag;

	public String getCommunicateUrl() {
		return communicateUrl;
	}

	public void setCommunicateUrl(String communicateUrl) {
		this.communicateUrl = communicateUrl;
	}

	public String getCommunicateType() {
		return communicateType;
	}

	public void setCommunicateType(String communicateType) {
		this.communicateType = communicateType;
	}

	public String getCommunicateName() {
		return communicateName;
	}

	public void setCommunicateName(String communicateName) {
		this.communicateName = communicateName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCommunicateTime() {
		return communicateTime;
	}

	public void setCommunicateTime(String communicateTime) {
		this.communicateTime = communicateTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSmallImageUrlList() {
		return smallImageUrlList;
	}

	public void setSmallImageUrlList(String smallImageUrlList) {
		this.smallImageUrlList = smallImageUrlList;
	}

	public String getImageUrlList() {
		return imageUrlList;
	}

	public void setImageUrlList(String imageUrlList) {
		this.imageUrlList = imageUrlList;
	}

	public String getCommentList() {
		return commentList;
	}

	public void setCommentList(String commentList) {
		this.commentList = commentList;
	}

	public String getGood() {
		return good;
	}

	public void setGood(String good) {
		this.good = good;
	}

	public String getGoodNameList() {
		return goodNameList;
	}

	public void setGoodNameList(String goodNameList) {
		this.goodNameList = goodNameList;
	}

	public String getCommunicateTag() {
		return communicateTag;
	}

	public void setCommunicateTag(String communicateTag) {
		this.communicateTag = communicateTag;
	}

}
