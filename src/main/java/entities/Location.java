package entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class Location {
    public int x;
    public int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
