package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString

//{
//"token": "string"
//}
public class AuthResponseDTO {
    private String token;
}
