package org.easy.tool.beans;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * bean map key，提高性能
 *
 */
@EqualsAndHashCode
@AllArgsConstructor
public class BladeBeanMapKey {
	private final Class type;
	private final int require;
}
