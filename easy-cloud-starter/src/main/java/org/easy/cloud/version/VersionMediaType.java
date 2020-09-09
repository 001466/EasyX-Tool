/*
 *      Copyright (c) 2018-2028, DreamLu All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: DreamLu 卢春梦 (596392912@qq.com)
 */

package org.easy.cloud.version;

import lombok.Getter;

/**
 * blade Media Types，application/vnd.github.VERSION+json
 *
 * <p>
 * https://developer.github.com/v3/media/
 * </p>
 *
 * @author L.cm
 */
@Getter
public class VersionMediaType {
	private static final String MEDIA_TYPE_TEMP = "application/%s+json";

	private final String version;
	private final org.springframework.http.MediaType mediaType;

	public VersionMediaType(String version) {
		this.version = version;
		this.mediaType = org.springframework.http.MediaType.valueOf(String.format(MEDIA_TYPE_TEMP,  version));
	}

	@Override
	public String toString() {
		return mediaType.toString();
	}

	public  static  void main(String[] arg){
		System.err.print(new VersionMediaType("v1").toString());
	}
}
