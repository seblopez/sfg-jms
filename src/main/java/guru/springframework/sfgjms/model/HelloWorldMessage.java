package guru.springframework.sfgjms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class HelloWorldMessage implements Serializable {

    static final long serialVersionUID = -4996651180128297417L;

    private UUID id;
    private String message;

}
