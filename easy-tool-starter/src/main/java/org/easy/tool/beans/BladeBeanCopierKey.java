package org.easy.tool.beans;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * copy key
 *
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class BladeBeanCopierKey {
	private final Class<?> source;
	private final Class<?> target;
	private final boolean useConverter;
	private final boolean nonNull;
}
