import lombok.Getter;

@Getter
public enum StatusCode {
    OK_200(200);
    private final Integer code;

    StatusCode(Integer code) {
        this.code = code;
    }
}
