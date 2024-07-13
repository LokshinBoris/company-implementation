package bbl.io;

public interface JSONable<T>
{
	String getJSON();
	T setObject(String json);
}
