package com.petstation.petstation.enums;
import java.util.stream.Stream;

public enum AnimalStatus {
	AVALIABLE(1, "AVALIABLE"),
    ADOPTED(2, "ADOPTED");
	
    private int id;
    private String description;

    private AnimalStatus(int id, String description) {
        this.id = id;
        this.description = description;
    }
    public int getId() {
		return id;
	}
	public String getDescription() {
		return description;
	}
	
	public static AnimalStatus of(int id) {
        return Stream.of(AnimalStatus.values())
          .filter(p -> p.getId() == id)
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}
