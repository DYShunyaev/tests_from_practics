package ddo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRowDDO {
    @JsonIgnoreProperties("number")
    private Integer id;
    private String name;
    private String type;
    private Boolean exotic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodsRowDDO that = (GoodsRowDDO) o;
        return Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(exotic, that.exotic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, exotic);
    }
}
