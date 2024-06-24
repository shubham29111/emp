package com.example.emp.model;

public class EpicRequest {

	  private String epicID;
	    private String captchaInput;
	    private String captchaId;

	    // Getters and Setters

	    public String getEpicID() {
	        return epicID;
	    }

	    public void setEpicID(String epicID) {
	        this.epicID = epicID;
	    }

	    public String getCaptchaInput() {
	        return captchaInput;
	    }

	    public void setCaptchaInput(String captchaInput) {
	        this.captchaInput = captchaInput;
	    }

	    public String getCaptchaId() {
	        return captchaId;
	    }

	    public void setCaptchaId(String captchaId) {
	        this.captchaId = captchaId;
	    }

		@Override
		public String toString() {
			return "EpicRequest [epicID=" + epicID + ", captchaInput=" + captchaInput + ", captchaId=" + captchaId
					+ "]";
		}
	    
	    
}
