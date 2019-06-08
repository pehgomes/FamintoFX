package view.crud;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Set;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewController<T> extends TableView<T> {
	
	private Class<T> persistentClass;
	
	public TableViewController(Class<T> persistentClass, String... colunas) {
		
		this.persistentClass = persistentClass;
		
		Set<Field> fields = new LinkedHashSet<Field>();
		for (Field f : this.persistentClass.getSuperclass().getDeclaredFields())
			fields.add(f);
		for (Field f : this.persistentClass.getDeclaredFields())
			fields.add(f);
		
		fields.forEach(f->{
			if(contem(colunas, f.getName())) {
				TableColumn<T, ?> tc = new TableColumn<>(f.getName().toUpperCase());
				tc.setMinWidth(f.getName().length()+200);
				tc.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
				getColumns().add(tc);
			}			
		});
	}
	private boolean contem(String[] colunas, String nome) {
		for (String s : colunas)
			if(s.equals(nome)) return true;
		return false;
	}
}
