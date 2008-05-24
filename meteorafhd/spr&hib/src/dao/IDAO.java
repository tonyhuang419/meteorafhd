package dao;

import java.util.List;

import entity.Std;

public interface IDAO {
	public void save(Std std);
	public List<Std> findAll();
	public void delete(int id);
	public void update(Std std);
	public Std getStd(int id);
	public void modify();
}
