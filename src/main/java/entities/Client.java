package entities;

public class Client {

	private Long id;
	private String firstName;
	private String lastName;
	private Gender gender;
	private Long favoriteBook;
	
	public Client(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Client(String firstName, String lastName, Gender gender) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
	}
	
	public Client(String firstName, String lastName, Gender gender, Long favoriteBook) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.favoriteBook = favoriteBook;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Long getFavoriteBook() {
		return favoriteBook;
	}

	public void setFavoriteBook(Long favorite_book) {
		this.favoriteBook = favorite_book;
	}
	
	
}
