package dev.drugowick.algaworks.algafoodapi.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import lombok.Data;
import lombok.NonNull;

@JacksonXmlRootElement(localName = "cuisines")
@Data
public class CuisinesXmlWrapper {

	@JsonProperty("cuisine")
	@JacksonXmlElementWrapper(useWrapping = false)
	@NonNull
	private List<Cuisine> cuisines;
	
}
