package com.orange.goldgame.domain;

public class HelpQa extends BaseObject {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column help_qa.id
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	private Integer id;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column help_qa.question
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	private String question;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column help_qa.answer
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	private String answer;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column help_qa.help_type
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	private Integer helpType;

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column help_qa.id
	 * @return  the value of help_qa.id
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column help_qa.id
	 * @param id  the value for help_qa.id
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column help_qa.question
	 * @return  the value of help_qa.question
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column help_qa.question
	 * @param question  the value for help_qa.question
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column help_qa.answer
	 * @return  the value of help_qa.answer
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column help_qa.answer
	 * @param answer  the value for help_qa.answer
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column help_qa.help_type
	 * @return  the value of help_qa.help_type
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	public Integer getHelpType() {
		return helpType;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column help_qa.help_type
	 * @param helpType  the value for help_qa.help_type
	 * @ibatorgenerated  Wed Jul 24 10:22:52 CST 2013
	 */
	public void setHelpType(Integer helpType) {
		this.helpType = helpType;
	}
}