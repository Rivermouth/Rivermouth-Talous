package fi.rivermouth.talous.domain;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

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

		public ClassInfo(Class<?> cls) {
			fields = new ArrayList<BaseEntity.FieldInfo>();
			while (cls != null) {
				addFieldsFromClass(cls);
				cls = cls.getSuperclass();
			}
		}
		
		/**
		 * Add {@link FieldInfo} objects of given class. Includes 
		 * private, protected and public fields.
		 * @param cls
		 */
		private void addFieldsFromClass(Class<?> cls) {
			for (Field field : cls.getDeclaredFields()) {
				if (field.getName().equalsIgnoreCase("serialVersionUID")) {
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
				fields.add(new FieldInfo(field));
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
	private class FieldInfo {
		private String name;
		private boolean nullable = true;
		private int minLength = 0;
		private int maxLength = 0;
		private boolean hidden = false;

		public FieldInfo(Field field) {
			this.name = field.getName();
			setNullable(field.getAnnotation(NotNull.class));
			setSize(field.getAnnotation(Size.class));
			setHidden(field.getAnnotation(JsonIgnore.class));
		}

		public void setNullable(NotNull notNullAnnotation) {
			if (notNullAnnotation != null) {
				this.nullable = false;
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
			this.name = name;
		}

		public boolean isNullable() {
			return nullable;
		}

		public void setNullable(boolean nullable) {
			this.nullable = nullable;
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

	}

}
