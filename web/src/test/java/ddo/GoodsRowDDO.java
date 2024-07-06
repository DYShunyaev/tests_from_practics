package ddo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRowDDO {
    private int number;
    private String name;
    private String type;
    private boolean exotic;
}
