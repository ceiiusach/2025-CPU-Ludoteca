package seminfcpu.ludoteca.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LoanDto {
    private UUID userId;
    private UUID itemId;
    private int estimatedMinutes;
}