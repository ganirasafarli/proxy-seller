package proxy.seller.assigment.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private List<String> message;
}

