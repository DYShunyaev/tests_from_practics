import lombok.Getter;

@Getter
public enum StatusCode {
    OK_200(200), BAD_REQ_400(400);
    private final Integer code;

    StatusCode(Integer code) {
        this.code = code;
    }
}
