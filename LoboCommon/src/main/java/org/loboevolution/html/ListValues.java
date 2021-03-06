package org.loboevolution.html;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum ListValues {
	
	NONE(-1),
	
	TYPE_NONE(0),
	
	TYPE_DISC(1),
	
	TYPE_CIRCLE(2),
	
	TYPE_SQUARE(3),
	
	TYPE_DECIMAL(4),
	
	TYPE_LOWER_ALPHA(5),
	
	TYPE_UPPER_ALPHA(6),
	
	TYPE_LOWER_LATIN(7),
	
	TYPE_UPPER_LATIN(8),
	
	TYPE_LOWER_ROMAN(9),
	
	TYPE_UPPER_ROMAN(10),
	
	TYPE_DECIMAL_LEADING_ZERO(11),
	
	POSITION_INSIDE(253),
	
	POSITION_OUTSIDE(254),
	
	POSITION_UNSET(255),
	
	TYPE_UNSET(256);

	private int value;
	private static final Map<Integer, ListValues> ENUM_MAP;
	
	static {
		Map<Integer, ListValues> map = new HashMap<Integer, ListValues>();
		for (ListValues instance : ListValues.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	ListValues(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public boolean isEqual(Integer value) {
		return this.value == value;
	}

	public static ListValues get(Integer actionName) {
		ListValues value = ENUM_MAP.get(actionName);
		return value == null ? ListValues.NONE : value;
	}
}
