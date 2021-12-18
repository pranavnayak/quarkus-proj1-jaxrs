package org.popins.dev;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name="A Movie",description="Movie Details")
public class Movie {
	private Long id;
	@Schema(required=true)
	private String title;
	public Long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
