package app.model;

import lombok.Getter;

@Getter
public enum Status {
    CREATED(1);

    private final int value;

    Status(int value) {
        this.value = value;
    }

}
