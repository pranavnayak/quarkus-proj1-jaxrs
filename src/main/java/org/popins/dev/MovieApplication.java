package org.popins.dev;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
			info = @Info(
					title = "Movie API's", 
					version = "1.0.0",
					description= "Movie Application",
					license=@License(
							name = "MIT",
							url="http://localhost:8080"),
					contact=@Contact(
							email="pranav.aem@yopmail.com",
							name="Pranav"
							)
					),
			tags= {
					@Tag(name="Movie",
							description="Movies Application"),
					@Tag(name="POC",
					description="Proof Of Concept")
			}
		)
public class MovieApplication {

}
