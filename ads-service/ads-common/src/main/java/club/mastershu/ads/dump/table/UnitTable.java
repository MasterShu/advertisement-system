package club.mastershu.ads.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitTable {
    private Long id;
    private Long planId;
    private Integer status;
    private Integer positionType;
}
