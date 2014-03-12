package org.consumed.jpa;

import java.sql.*;
import java.util.Calendar;

import javax.persistence.*;

public class JPABase {
	protected EntityManagerFactory factory;
	
	public JPABase() {
		factory = Persistence.createEntityManagerFactory("consulmed");
	}
}
