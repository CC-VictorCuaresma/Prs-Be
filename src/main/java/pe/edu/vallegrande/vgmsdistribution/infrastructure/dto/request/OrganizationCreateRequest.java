package pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationCreateRequest {

    private String organizationCode;
    private String organizationName;
    private String legalRepresentative;
    private String address; 
    private Long phone;
    private String status; // "ACTIVE", "INACTIVE"

}
