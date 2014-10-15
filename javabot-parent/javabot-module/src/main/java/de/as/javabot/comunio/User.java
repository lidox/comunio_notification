package de.as.javabot.comunio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Representiert einen User-Account bei comunio
 */
@Entity
@NamedQueries({@NamedQuery(name = User.GET_BY_NAME, query = "SELECT u FROM user u where u.name = :name ")})
@Table(name = "user")
public class User implements Serializable{

	public static final String GET_BY_NAME = "User.getUserByName";
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;
    @Column(name = "name")
	private String login;
    @Column(name = "password")
	private String password;
	
	public User(String login, String password){
		this.login = login;
		this.password = password;
	}
	
	public User(){}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String toString(){
		StringBuilder ret = new StringBuilder();
		ret.append("User: "+getLogin()+" ");
		ret.append("Password: "+getPassword().substring(0,1)+"**********");
		return ret.toString();
	}
	
}
