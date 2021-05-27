package emina.eminaapp.api;

import java.util.List;

public class ModelApiResponse {
    private int value;
    private String message;
    private String email;
    private String password;
    private List<ModelResult> result;

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<ModelResult> getResult() {
        return result;
    }
}

