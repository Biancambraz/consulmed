package org.consulmed.dao;

public interface IDao<T> {
	void add(T object);
	T get(T object);
	void remove(T object);
	void set(T object);
}
