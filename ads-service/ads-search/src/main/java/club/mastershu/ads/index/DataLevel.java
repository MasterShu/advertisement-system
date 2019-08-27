package club.mastershu.ads.index;

import lombok.Getter;

@Getter
public enum DataLevel {

    LEVEL2(2, "Level 2"),
    LEVEL3(3, "Level 3"),
    LEVEL4(4, "Level 4");

    private Integer level;
    private String description;

    DataLevel(Integer level, String description) {
        this.level = level;
        this.description = description;
    }
}
