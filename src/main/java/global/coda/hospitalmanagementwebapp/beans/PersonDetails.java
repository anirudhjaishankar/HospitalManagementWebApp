package global.coda.hospitalmanagementwebapp.beans;


public class PersonDetails {
	protected int id;
	protected String name;
	protected int age;
	protected String gender;
	protected String username;
	protected String password;
	protected Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "PersonDetails [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", username="
				+ username + ", password=" + password + ", address=" + address + "]";
	}


}
