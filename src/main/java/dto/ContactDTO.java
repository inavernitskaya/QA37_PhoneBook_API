package dto;
//{
//    "id": "string",
    //    "name": "string",
      //  "lastName": "string",
        //"email": "string",
        //"phone": "51110298780",
      //  "address": "string",
        //"description": "string"
    //    }

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class ContactDTO {
    private String id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String description
            ;

}
