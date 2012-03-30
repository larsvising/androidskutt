package skutthyllan.data;

import java.util.ArrayList;

public class Skuttbok extends Bok {
	
	private Integer id;
	private String checkedoutBy;
	private String checkedoutDate;
	private String createdDate;
	private ArrayList<Integer> aReviewId;
	
	public ArrayList<Integer> getaReviewId() {
		return aReviewId;
	}
	public void setaReviewId(ArrayList<Integer> aReviewId) {
		this.aReviewId = aReviewId;
	}
	public String getCheckedoutBy() {
		return checkedoutBy;
	}
	public void setCheckedoutBy(String checkedoutBy) {
		this.checkedoutBy = checkedoutBy;
	}
	public String getCheckedoutDate() {
		return checkedoutDate;
	}
	public void setCheckedoutDate(String checkedoutDate) {
		this.checkedoutDate = checkedoutDate;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String inCreatedDate) {
		this.createdDate = inCreatedDate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	

}
