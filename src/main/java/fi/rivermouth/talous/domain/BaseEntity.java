package fi.rivermouth.talous.domain;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javassist.expr.Instanceof;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class BaseEntity extends fi.rivermouth.spring.entity.BaseEntity<Long> {

	/**
	 * Get {@link ClassInfo} object.
	 * @return ClassInfo
	 */
	public ClassInfo getClassInfo() {
		return new ClassInfo(getClass());
	}

	/**
	 * This class has meta info of given class. Including list of 
	 * {@link FieldInfo} objects of declared private, protected and public fields.
	 * 
	 * @author Karri Rasinmäki
	 *
	 */
	private class ClassInfo {
		private List<FieldInfo> fields;

		public ClassInfo(Class<?> cls, FieldInfo parent) {
			fields = new ArrayList<FieldInfo>();
			while (cls != null) {
				addFieldsFromClass(cls, parent);
				cls = cls.getSuperclass();
			}
			Collections.sort(fields);
		}

		public ClassInfo(Class<?> cls) {
			this(cls, null);
		}

		/**
		 * Add {@link FieldInfo} objects of given class. Includes 
		 * private, protected and public fields.
		 * @param cls
		 */
		private void addFieldsFromClass(Class<?> cls, FieldInfo parent) {
			for (Field field : cls.getDeclaredFields()) {
				if (field.getName().equalsIgnoreCase("serialVersionUID")) {
					continue;
				}
				if (Collection.class.isAssignableFrom(field.getType()) || 
					Map.class.isAssignableFrom(field.getType())) {
					continue;
				}
				switch (field.getModifiers()) {
				case Modifier.PRIVATE:
				case Modifier.PROTECTED:
				case Modifier.PUBLIC:
					break;
				default:
					continue;
				}
				fields.add(new FieldInfo(field, parent));
			}
		}

		public List<FieldInfo> getFields() {
			return fields;
		}

		public void setFields(List<FieldInfo> fields) {
			this.fields = fields;
		}
	}

	/**
	 * This class has some useful info about declared field.
	 * 
	 * @author Karri Rasinmäki
	 *
	 */
	private class FieldInfo implements Comparable<FieldInfo> {
		private String name;
		private String type;
		private FieldInfo parent = null;
		private ClassInfo child = null;
		private boolean required = false;
		private int minLength = 0;
		private int maxLength = -1;
		private boolean hidden = false;

		public FieldInfo(Field field, FieldInfo parent) {
			setParent(parent);
			if (!(
				field.getType().isAssignableFrom(String.class) || 
				field.getType().isAssignableFrom(Integer.class) || 
				field.getType().isAssignableFrom(Long.class)
				)) {
				setChild(new ClassInfo(field.getType(), this));
			}
			else {
				setRequired(field.getAnnotation(NotNull.class));
				setSize(field.getAnnotation(Size.class));
				setHidden(field.getAnnotation(JsonIgnore.class));
			}
			setName(field.getName());
		}
		
		public String getPrefix() {
			if (getParent() == null) {
				return "";
			}
			return getParent().getPrefix() + getParent().getName() + ".";
		}

		public void setRequired(NotNull notNullAnnotation) {
			if (notNullAnnotation != null) {
				this.required = true;
			}
		}

		public void setSize(Size sizeAnnotation) {
			if (sizeAnnotation == null) {
				return;
			}
			this.minLength = sizeAnnotation.min();
			this.maxLength = sizeAnnotation.max();
		}

		public void setHidden(JsonIgnore jsonIgnoreAnnotation) {
			if (jsonIgnoreAnnotation != null) {
				this.hidden = true;
			}
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			if (name.equals("password")) {
				setType("password");
			}
			if (name.equals("id")) {
				setHidden(true);
			}
			this.name = name;
		}

		public boolean isRequired() {
			return required;
		}

		public void setRequired(boolean required) {
			this.required = required;
		}

		public int getMinLength() {
			return minLength;
		}

		public void setMinLength(int minLength) {
			this.minLength = minLength;
		}

		public int getMaxLength() {
			return maxLength;
		}

		public void setMaxLength(int maxLength) {
			this.maxLength = maxLength;
		}

		public boolean isHidden() {
			return hidden;
		}

		public void setHidden(boolean hidden) {
			this.hidden = hidden;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public ClassInfo getChild() {
			return child;
		}

		public void setChild(ClassInfo child) {
			this.child = child;
		}

		public FieldInfo getParent() {
			return parent;
		}

		public void setParent(FieldInfo parent) {
			this.parent = parent;
		}

		@Override
		public int compareTo(FieldInfo o) {
			if (this.getChild() == null && o.getChild() != null) {
				return -1;
			}
			if (this.getChild() != null && o.getChild() == null) {
				return 1;
			}
			return this.name.compareTo(o.getName());
		}

	}

}
