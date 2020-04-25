package pl.learning.spring.model.user;

public enum UserRoleType {

	ADMIN("ROLE_ADMIN"), USER("ROLE_USER");
	
	private String value;
	
	private UserRoleType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
